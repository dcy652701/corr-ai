package corr.ai.module.metadata.dal.mysql.strategy;

import corr.ai.framework.common.pojo.PageParam;
import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.framework.mybatis.core.query.MPJLambdaWrapperX;
import corr.ai.framework.security.core.util.SecurityFrameworkUtils;
import corr.ai.module.metadata.controller.app.strategy.param.ListStrategyParam;
import corr.ai.module.metadata.dal.dataobject.coin.CoinMetadataInfoDO;
import corr.ai.module.metadata.dal.dataobject.strategy.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dongchengye
 */
@Mapper
public interface StrategyConfigMapper extends BaseMapperX<StrategyConfigDO> {

    default PageResult<StrategyPageDO> pageStrategy(ListStrategyParam param) {
        MPJLambdaWrapperX<StrategyConfigDO> wrapperX = new MPJLambdaWrapperX<>();
        wrapperX.leftJoin(CoinMetadataInfoDO.class, CoinMetadataInfoDO::getMetaId, StrategyConfigDO::getCoinId);
        wrapperX.leftJoin(StrategyResultDO.class, StrategyResultDO::getStrategyId, StrategyConfigDO::getSid);
        wrapperX.select(StrategyConfigDO::getSid);
        wrapperX.select(StrategyConfigDO::getCoinId);
        wrapperX.select(CoinMetadataInfoDO::getSymbol);
        wrapperX.select(StrategyConfigDO::getImg);
        wrapperX.select(StrategyConfigDO::getStrategyName);
        wrapperX.select(StrategyResultDO::getTotalReturn);
        wrapperX.select(StrategyResultDO::getSharpeRatio);
        wrapperX.select(StrategyResultDO::getProfitFactor);
        wrapperX.select(StrategyResultDO::getMaxDrawdown);
        wrapperX.select(StrategyResultDO::getStart);
        wrapperX.select(StrategyResultDO::getEnd);
        wrapperX.select(StrategyConfigDO::getStrategyFactors);
        wrapperX.select(StrategyConfigDO::getIntervals);
        wrapperX.likeIfPresent(StrategyConfigDO::getStrategyName, param.getStrategyName());
        wrapperX.isNotNull(StrategyConfigDO::getStrategyName);
        wrapperX.eq(StrategyConfigDO::getCoinId,param.getCoinId());
        wrapperX.eq(StrategyConfigDO::getCreator, SecurityFrameworkUtils.getLoginUserId());
        wrapperX.orderByDesc(StrategyConfigDO::getCreateTime);
        return selectJoinPage(param, StrategyPageDO.class, wrapperX);
    }

    default PageResult<TradePairDO> pageTradePair(PageParam param) {
        MPJLambdaWrapperX<StrategyConfigDO> wrapperX = new MPJLambdaWrapperX<>();
        wrapperX.leftJoin(CoinMetadataInfoDO.class, CoinMetadataInfoDO::getMetaId, StrategyConfigDO::getCoinId);
        wrapperX.select(StrategyConfigDO::getCoinId);
        wrapperX.select(CoinMetadataInfoDO::getTradePair);
        wrapperX.select(CoinMetadataInfoDO::getCoinLogo);
        wrapperX.distinct();
        wrapperX.eq(StrategyConfigDO::getCreator,SecurityFrameworkUtils.getLoginUserId());
        return selectJoinPage(param, TradePairDO.class, wrapperX);
    }

}
