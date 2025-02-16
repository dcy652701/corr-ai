import requests
import json
API_KEY = '2kbirg0hddGIDYRhY9NpXxIb1vo'
param = {'a': 'BTC',
         's': 1736784000,
         'u': 1736870399,
         'i': '24h',
         'f': 'JSON',
         'timestamp_format': 'unix',
         'api_key': '2kbirg0hddGIDYRhY9NpXxIb1vo'}

# make API request
res1 = requests.get('https://api.glassnode.com/v1/metrics/addresses/accumulation_balance',
                    params=param)
print(res1)
