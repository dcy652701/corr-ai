import json

import pymysql
import requests
import time
from requests.adapters import HTTPAdapter
from urllib3.util.retry import Retry

API_KEY = '2kbirg0hddGIDYRhY9NpXxIb1vo'

coin_id_mapping = {
    "BTC": 101,
    "ETH": 102,
    "SOL": 103
}


def connect_to_db():
    while True:
        try:
            return pymysql.connect(
                host='194.233.65.183',
                user='root',
                password='xa5AW8PES3GGs3',
                database='corr_db',
                port=13306,
                autocommit=True,
                connect_timeout=60,
                cursorclass=pymysql.cursors.DictCursor
            )
        except pymysql.MySQLError as e:
            print(f"Error connecting to database: {e}")
            time.sleep(2)  # 等待5秒后重试


# MySQL 插入函数
def insert_into_mysql(sql, data):
    connection = connect_to_db()
    try:
        with connection.cursor() as cursor:
            cursor.execute(sql, data)
        connection.commit()
    finally:
        connection.close()


# 数据表 1: corr_api_info 的插入逻辑
def insert_into_corr_api_info(data):
    path = data["path"]
    parameters = data["parameters"]

    api_name = path.split("/")[-1]
    api_type = path.split("/")[1]

    # 动态拼接 symbol，只保留在 coin_id_mapping 中有对应币种的值
    valid_symbols = [symbol for symbol in parameters.get("a", []) if symbol in coin_id_mapping]
    symbol = ",".join(valid_symbols) if valid_symbols else None

    url = path
    # symbol = ",".join(parameters["a"])
    # request_frequency = ",".join(parameters["i"])

    # 替换 request_frequency 中的 "24h" 为 "1d"
    request_frequencies = [
        "1d" if frequency == "24h" else frequency
        for frequency in parameters.get("i", [])
    ]
    request_frequency = ",".join(request_frequencies)

    provider = "glassnode"  # 假设固定值
    tenant_id = 1

    sql = """
    INSERT INTO corr_api_info_test (api_name, api_type, symbol, url, request_frequency, provider, tenant_id)
    VALUES (%s, %s, %s, %s, %s, %s, %s)
    """
    values = (api_name, api_type, symbol, url, request_frequency, provider, tenant_id)
    # print(values)
    insert_into_mysql(sql, values)


# 数据表 2: corr_available_non_price_factors 的插入逻辑
def insert_into_corr_available_non_price_factors(data):
    path = data["path"]
    parameters = data["parameters"]
    min_intervals = parameters["i"]
    coins = parameters["a"]

    api_type = path.split("/")[1].capitalize()
    factor_name = path.split("/")[-1].replace("_", " ").title()
    provider = "Glassnode"  # 假设固定值
    tenant_id = 1

    for coin in coins:
        belong_coin_id = coin_id_mapping.get(coin)
        if not belong_coin_id:
            continue  # 跳过未定义的币种

        for interval in min_intervals:
            if interval == "24h":
                interval = "1d"
            sql = """
            INSERT INTO corr_available_non_price_factors_test (belong_coin_id, factor_type, factor_name, factor_key_name, min_interval, url, provider, tenant_id)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
            """
            values = (belong_coin_id, api_type, factor_name, 'v', interval, path, provider, tenant_id)
            # print(values)
            insert_into_mysql(sql, values)


if __name__ == '__main__':

    # make API request
    res1 = requests.get('https://api.glassnode.com/v1/metadata/metrics',
                        params={'api_key': API_KEY})
    url_list = json.loads(res1.content.decode('utf-8').strip())

    session = requests.Session()
    retries = Retry(total=10, backoff_factor=0.5, status_forcelist=[500, 502, 503, 504])
    session.mount('https://', HTTPAdapter(max_retries=retries))

    # TODO 对于url_list写for循环 调用查询每一个metric的细节
    for index, url in enumerate(url_list):
        if index <= 699:
            continue
        print(f"Processing path: {url}")
        param = {'path': url, 'api_key': API_KEY}
        res2 = session.get('https://api.glassnode.com/v1/metadata/metric',
                            params=param)
        metric_detail = json.loads(res2.content.decode('utf-8').strip())
        # 执行插入操作
        insert_into_corr_api_info(metric_detail)
        insert_into_corr_available_non_price_factors(metric_detail)

    # res2 = requests.get('https://api.glassnode.com/v1/metadata/metric',
    #                     params={'path': url_list[35], 'api_key': API_KEY})
    # metric_detail = json.loads(res2.content.decode('utf-8').strip())
    # # 执行插入操作
    # insert_into_corr_api_info(metric_detail)
    # insert_into_corr_available_non_price_factors(metric_detail)