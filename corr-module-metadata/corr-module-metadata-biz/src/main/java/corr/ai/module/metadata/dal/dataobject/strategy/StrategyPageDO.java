package corr.ai.module.metadata.dal.dataobject.strategy;

import corr.ai.framework.mybatis.core.dataobject.BaseDO;
import corr.ai.module.metadata.consts.UrlConstants;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author dongchengye
 */
@Data
public class StrategyPageDO {
    private Long sid;
    private String strategyName;
    private Long coinId;
    private String symbol;
    private BigDecimal totalReturn;
    private BigDecimal sharpeRatio;
    private BigDecimal maxDrawdown;
    private String img;
    private BigDecimal profitFactor;
    private Long start;
    private Long end;
    private String strategyFactors;
    private String intervals;

    public String imgDownloadLink() {
        return UrlConstants.TENCENT_OSS_DOWNLOAD_URL + img;
    }
}
