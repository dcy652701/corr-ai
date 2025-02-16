package corr.ai.module.metadata.controller.app.strategy.resp;

import cn.hutool.json.JSONObject;
import lombok.Data;

/**
 * @author dongchengye
 */
@Data
public class StrategyHistoryVO {
    private Long sid;
    private String rawStrategy;
    private String priceDataQueryParam;
}
