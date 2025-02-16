package corr.ai.module.metadata.controller.app.strategy.resp;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * 策略查询
 *
 * @author dongchengye
 */
@Data
public class StrategyConfigRespVO {
    private Long sid;
    private String strategyName;
    private JSONObject strategyConfig;
    private Integer inputMode;
    private List<JSONObject> priceDataQueryParam;
    private List<JSONObject> nonPriceDataQueryParam;
}
