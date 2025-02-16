package corr.ai.module.metadata.dal.mysql.optimizerconfig;

import java.util.*;

import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.framework.mybatis.core.query.MPJLambdaWrapperX;
import corr.ai.module.metadata.dal.dataobject.optimizerconfig.OptimizerAndStrategyConfigDO;
import corr.ai.module.metadata.dal.dataobject.optimizerconfig.OptimizerConfigDO;
import corr.ai.module.metadata.dal.dataobject.optimizerconfig.OptimizerResultDO;
import corr.ai.module.metadata.dal.dataobject.strategy.StrategyConfigDO;
import org.apache.ibatis.annotations.Mapper;
import corr.ai.module.metadata.controller.app.optimizerconfig.vo.*;

/**
 * 优化器信息 Mapper
 *
 * @author CorrAi
 */
@Mapper
public interface OptimizerConfigMapper extends BaseMapperX<OptimizerConfigDO> {

    default PageResult<OptimizerConfigDO> selectPage(AppOptimizerConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OptimizerConfigDO>()
                .eqIfPresent(OptimizerConfigDO::getStrategyId, reqVO.getStrategyId())
                .eqIfPresent(OptimizerConfigDO::getOptimizer, reqVO.getOptimizer())
                .eqIfPresent(OptimizerConfigDO::getOptimizerConfig, reqVO.getOptimizerConfig())
                .eqIfPresent(OptimizerConfigDO::getCoinId, reqVO.getCoinId())
                .betweenIfPresent(OptimizerConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OptimizerConfigDO::getOid));
    }

    default PageResult<OptimizerResultDO> selectOptimizerPage(AppOptimizerConfigPageReqVO reqVO) {
        MPJLambdaWrapperX<OptimizerConfigDO> wrapperX = new MPJLambdaWrapperX<>();
        wrapperX.leftJoin(StrategyConfigDO.class, StrategyConfigDO::getSid, OptimizerConfigDO::getStrategyId);
        wrapperX.select(OptimizerConfigDO::getOid);
        wrapperX.select(OptimizerConfigDO::getStrategyId);
        wrapperX.select(OptimizerConfigDO::getCoinId);
        wrapperX.select(OptimizerConfigDO::getOptimizer);
        wrapperX.select(OptimizerConfigDO::getOptimizerConfig);
        wrapperX.select(OptimizerConfigDO::getStats);
        wrapperX.select(OptimizerConfigDO::getFinalPercentage);
        wrapperX.select(StrategyConfigDO::getRawStrategy);
        wrapperX.select(StrategyConfigDO::getStrategyFactors);
        wrapperX.eq(OptimizerConfigDO::getCoinId, reqVO.getCoinId());
        return selectJoinPage(reqVO, OptimizerResultDO.class, wrapperX);
    }

    default OptimizerAndStrategyConfigDO getOptimizerInfo(Long oid) {
        MPJLambdaWrapperX<OptimizerConfigDO> wrapperX = new MPJLambdaWrapperX<>();
        wrapperX.leftJoin(StrategyConfigDO.class, StrategyConfigDO::getSid, OptimizerConfigDO::getStrategyId);
        wrapperX.select(OptimizerConfigDO::getOid);
        wrapperX.select(OptimizerConfigDO::getStrategyId);
        wrapperX.select(OptimizerConfigDO::getOptimizerConfig);
        wrapperX.select(StrategyConfigDO::getRawStrategy);
        wrapperX.eq(OptimizerConfigDO::getOid, oid);
        return selectJoinOne(OptimizerAndStrategyConfigDO.class, wrapperX);
    }

}
