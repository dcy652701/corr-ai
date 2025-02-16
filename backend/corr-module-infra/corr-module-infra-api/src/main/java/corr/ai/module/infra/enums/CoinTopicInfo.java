package corr.ai.module.infra.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dongchengye
 */
public class CoinTopicInfo {
    private static Map<String, String> topic = new HashMap<>();

    static {
        topic.put("BTCUSDT", "btc_kline_1m_topic");
        topic.put("ETHUSDT", "eth_kline_1m_topic");
        topic.put("SOLUSDT", "sol_kline_1m_topic");
    }

    public static String getTopicByCoin(String symbol){
        return topic.get(symbol);
    }
}
