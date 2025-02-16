package corr.ai.module.metadata.dto.strategy;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @author dongchengye
 */
@Data
public class StrategyConfigReqDTO {
    private Long sid;
    /**
     * 币id
     */
    private Long coinId;
    /**
     * 策略hash，判断是否存在
     */
    private String strategyHash;
    /**
     * 未加密的策略配置
     */
    private String rawStrategy;
    /**
     * 策略中包含的所有指标，仅做展示用
     */
    private String strategyFactors;
    /**
     * 时间粒度
     */
    private String intervals;

    private List<JSONObject> priceDataQueryParam;

    private List<JSONObject> nonPriceDataQueryParam;

    private List<StrategyBaseFactorAndIndicatorDTO> distinctFactorAndIndicators;
}
