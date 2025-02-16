package corr.ai.module.metadata.dal.dataobject.optimizerconfig;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;

/**
 * @author dongchengye
 */
@Data
public class OptimizerAndStrategyConfigDO {
    private Long optimizerId;
    private Long strategyId;
    private String optimizerConfig;
    private String rawStrategy;

    public JSONObject getOptConfig(){
        return JSONUtil.parseObj(optimizerConfig);
    }

    public JSONObject getStrategyConfig(){
        return JSONUtil.parseObj(rawStrategy);
    }
}
