package corr.ai.module.metadata.controller.app.strategy.resp;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 分页查询后的记录
 *
 * @author dongchengye
 */
@Data
public class StrategyPageRecordVO {
    private Long sid;
    private String strategyName;
    private Long coinId;
    private String coinName;
    private BigDecimal totalReturn;
    private BigDecimal sharpeRatio;
    private BigDecimal maxDrawdown;
    private String img;
    private BigDecimal profitFactor;
    private Long start;
    private Long end;
    private String strategyFactors;
    private String intervals;
}
