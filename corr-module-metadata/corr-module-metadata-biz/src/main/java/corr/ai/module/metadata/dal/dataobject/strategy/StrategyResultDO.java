package corr.ai.module.metadata.dal.dataobject.strategy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author dongchengye
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("corr_strategy_result")
public class StrategyResultDO extends BaseDO {
    @TableId(value = "res_id", type = IdType.ASSIGN_ID)
    private Long resId;
    private Long strategyId;
    private Long start;
    private Long end;
    private String periods;
    private BigDecimal startValue;
    private BigDecimal calmarRatio;
    private BigDecimal avgLosingTradeDuration;
    private BigDecimal avgLosingTrade;
    private BigDecimal avgWinningTradeDuration;
    private BigDecimal avgWinningTrade;
    private BigDecimal benchmarkReturn;
    private BigDecimal bestTrade;
    private BigDecimal endValue;
    private BigDecimal expectancy;
    private BigDecimal maxDrawdown;
    private BigDecimal maxGrossExposure;
    private BigDecimal omegaRatio;
    private BigDecimal openTradePnl;
    private BigDecimal profitFactor;
    private BigDecimal sharpeRatio;
    private BigDecimal sortinoRatio;
    private Integer totalClosedTrades;
    private BigDecimal totalFeesPaid;
    private Integer totalOpenTrades;
    private BigDecimal totalReturn;
    private Integer totalTrades;
    private BigDecimal winRate;
    private BigDecimal worstTrade;
}
