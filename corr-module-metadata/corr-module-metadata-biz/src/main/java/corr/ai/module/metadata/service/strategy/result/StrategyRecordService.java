package corr.ai.module.metadata.service.strategy.result;

import cn.hutool.json.JSONObject;

/**
 * @author dongchengye
 */
public interface StrategyRecordService {

    /**
     * 保存策略结果
     *
     * @param strategyResultJson
     * @param strategyId
     */
    void saveResult(JSONObject strategyResultJson, Long strategyId);
}
