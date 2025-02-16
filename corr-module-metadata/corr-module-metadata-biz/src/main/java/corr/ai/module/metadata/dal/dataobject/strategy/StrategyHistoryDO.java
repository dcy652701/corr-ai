package corr.ai.module.metadata.dal.dataobject.strategy;

import cn.hutool.json.JSONObject;
import lombok.Data;

/**
 * @author dongchengye
 */
@Data
public class StrategyHistoryDO {
    private Long sid;
    private String rawStrategy;
    private String priceDataQueryParam;
}
