package corr.ai.module.metadata.controller.app.strategy.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author dongchengye
 */
@Data
@Schema(description = "策略结果req")
public class StrategyResultReq {
    @Schema(description = "主键")
    private Long resId;
    @Schema(description = "策略主键")
    @NotNull(message = "策略主键不能为空")
    private Long strategyId;
    @NotNull(message = "策略结果的参数为空")
    private LocalDateTime start;
    @NotNull(message = "策略结果的参数为空")
    private LocalDateTime end;
    @NotNull(message = "策略结果的参数为空")
    private Integer period;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal startValue;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal calmarRatio;
    @NotNull(message = "策略结果的参数为空")
    private Integer avgLosingTradeDuration;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal avgLosingTrade;
    @NotNull(message = "策略结果的参数为空")
    private Integer avgWinningTradeDuration;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal avgWinningTrade;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal benchmarkReturn;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal bestTrade;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal endValue;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal expectancy;
    @NotNull(message = "策略结果的参数为空")
    private Integer maxDrawdownDuration;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal maxDrawdown;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal maxGrossExposure;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal omegaRatio;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal openTradePnl;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal profitFactor;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal sharpeRatio;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal sortinoRatio;
    @NotNull(message = "策略结果的参数为空")
    private Integer totalClosedTrades;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal totalFeesPaid;
    @NotNull(message = "策略结果的参数为空")
    private Integer totalOpenTrades;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal totalReturn;
    @NotNull(message = "策略结果的参数为空")
    private Integer totalTrades;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal winRate;
    @NotNull(message = "策略结果的参数为空")
    private BigDecimal worstTrade;
}
