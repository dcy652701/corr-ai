package corr.ai.module.metadata.service.strategy.result;

import cn.hutool.json.JSONObject;
import corr.ai.framework.common.util.date.LocalDateTimeUtils;
import corr.ai.module.metadata.dal.dataobject.strategy.StrategyResultDO;
import corr.ai.module.metadata.dal.mysql.strategy.StrategyResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author dongchengye
 */
@Service
public class StrategyRecordServiceImpl implements StrategyRecordService{

    @Autowired
    private StrategyResultMapper strategyResultMapper;

    //这里不用加事务注解，上层调用的时候一起注解了
    @Override
    public void saveResult(JSONObject strategyResultJson, Long strategyId) {
        StrategyResultDO strategyResultDO = new StrategyResultDO();
        strategyResultDO.setStrategyId(strategyId);

        // 创建DateTimeFormatter对象，指定要解析的字符串格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 使用parse方法将字符串转换为LocalDateTime对象
//        String startStr = strategyResultJson.getStr("start");
//        LocalDateTime start = LocalDateTime.parse(startStr, formatter);
//        String endStr = strategyResultJson.getStr("end");
//        LocalDateTime end = LocalDateTime.parse(endStr, formatter);
        Long startTimestamp = strategyResultJson.getLong("start");
        Long endTimestamp = strategyResultJson.getLong("end");
//        LocalDateTime start = convertSecondsToLocalDateTime(startTimestamp, "UTC");
//        LocalDateTime end = convertSecondsToLocalDateTime(endTimestamp, "UTC");
        String period = strategyResultJson.getStr("period");
        BigDecimal startValue = strategyResultJson.getBigDecimal("start_value");
        BigDecimal calmarRatio = strategyResultJson.getBigDecimal("calmar_ratio");
        BigDecimal avgLosingTradeDuration = strategyResultJson.getBigDecimal("avg_losing_trade_duration");
        BigDecimal avgLosingTrade = strategyResultJson.getBigDecimal("avg_losing_trade");
        BigDecimal avgWinningTradeDuration = strategyResultJson.getBigDecimal("avg_winning_trade_duration");
        BigDecimal avgWinningTrade = strategyResultJson.getBigDecimal("avg_winning_trade");
        BigDecimal benchmarkReturn = strategyResultJson.getBigDecimal("benchmark_return");
        BigDecimal bestTrade = strategyResultJson.getBigDecimal("best_trade");
        BigDecimal endValue = strategyResultJson.getBigDecimal("end_value");
        BigDecimal expectancy = strategyResultJson.getBigDecimal("expectancy");
        BigDecimal maxDrawdown = strategyResultJson.getBigDecimal("max_drawdown");
        BigDecimal maxGrossExposure = strategyResultJson.getBigDecimal("max_gross_exposure");
        BigDecimal omegaRatio = strategyResultJson.getBigDecimal("omega_ratio");
        BigDecimal openTradePnl = strategyResultJson.getBigDecimal("open_trade_pnl");
        BigDecimal profitFactor = strategyResultJson.getBigDecimal("profit_factor");
        BigDecimal sharpeRatio = strategyResultJson.getBigDecimal("sharpe_ratio");
        BigDecimal sortinoRatio = strategyResultJson.getBigDecimal("sortino_ratio");
        int totalClosedTrades = strategyResultJson.getInt("total_closed_trades");
        BigDecimal totalFeesPaid = strategyResultJson.getBigDecimal("total_fees_paid");
        int totalOpenTrades = strategyResultJson.getInt("total_open_trades");
        BigDecimal totalReturn = strategyResultJson.getBigDecimal("total_return");
        int totalTrades = strategyResultJson.getInt("total_trades");
        BigDecimal winRate = strategyResultJson.getBigDecimal("win_rate");
        BigDecimal worstTrade = strategyResultJson.getBigDecimal("worst_trade");

        strategyResultDO.setStart(startTimestamp);
        strategyResultDO.setEnd(endTimestamp);
        strategyResultDO.setPeriods(period);
        strategyResultDO.setStartValue(startValue);
        strategyResultDO.setCalmarRatio(calmarRatio);
        strategyResultDO.setAvgLosingTradeDuration(avgLosingTradeDuration);
        strategyResultDO.setAvgLosingTrade(avgLosingTrade);
        strategyResultDO.setAvgWinningTradeDuration(avgWinningTradeDuration);
        strategyResultDO.setAvgWinningTrade(avgWinningTrade);
        strategyResultDO.setBenchmarkReturn(benchmarkReturn);
        strategyResultDO.setBestTrade(bestTrade);
        strategyResultDO.setEndValue(endValue);
        strategyResultDO.setExpectancy(expectancy);
        strategyResultDO.setMaxDrawdown(maxDrawdown);
        strategyResultDO.setMaxGrossExposure(maxGrossExposure);
        strategyResultDO.setOmegaRatio(omegaRatio);
        strategyResultDO.setOpenTradePnl(openTradePnl);
        strategyResultDO.setProfitFactor(profitFactor);
        strategyResultDO.setSharpeRatio(sharpeRatio);
        strategyResultDO.setSortinoRatio(sortinoRatio);
        strategyResultDO.setTotalClosedTrades(totalClosedTrades);
        strategyResultDO.setTotalFeesPaid(totalFeesPaid);
        strategyResultDO.setTotalOpenTrades(totalOpenTrades);
        strategyResultDO.setTotalReturn(totalReturn);
        strategyResultDO.setTotalTrades(totalTrades);
        strategyResultDO.setWinRate(winRate);
        strategyResultDO.setWorstTrade(worstTrade);

        strategyResultMapper.insert(strategyResultDO);
    }

    /**
     * Converts a second-level timestamp to LocalDateTime.
     *
     * @param timestampSeconds The second-level timestamp.
     * @param zoneId           The ZoneId for the conversion (e.g., "UTC", "Asia/Shanghai").
     * @return The corresponding LocalDateTime.
     */
    private LocalDateTime convertSecondsToLocalDateTime(long timestampSeconds, String zoneId) {
//        LocalDateTime localDateTime = Timestamp.from(Instant.ofEpochSecond(timestampSeconds)).toLocalDateTime();
//        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestampSeconds), ZoneId.of(zoneId));
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(timestampSeconds, 0, ZoneOffset.UTC);
        return localDateTime;
    }
}
