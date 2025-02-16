package corr.ai.module.metadata.consts;

/**
 * @author dongchengye
 */
public interface CacheKey {
    String STRATEGY_RESULT = "strategy_result";
    String STRATEGY_CONFIG = "strategy_config";
    String RESULT_STRING = "strategy_result_string_cache";
    String PRICE_DATA_CACHE = "price_data_cache";
    String KLINE_DATA_CACHE = "kline_data_cache";
    String NON_PRICE_DATA_CACHE = "non_price_data_cache";
    String INDICATOR_DATA_CACHE = "indicator_data_cache";
    String KLINE_INDICATOR_DATA_CACHE = "kline_indicator_data_cache";
    String NONE_PRICE_DATA_CACHE = "none_price_data_cache";
    String USER_CHAT_SESSION_CACHE = "user_chat_session_cache";
    String USER_CHAT_DETAILS_CACHE = "user_chat_details_cache";

    String FREQTRADE_BACKTESTING_RESULT = "freqtrade_backtesting_result";
    String FREQTRADE_RESULT_STRING = "freqtrade_backtesting_result_string_cache";

}
