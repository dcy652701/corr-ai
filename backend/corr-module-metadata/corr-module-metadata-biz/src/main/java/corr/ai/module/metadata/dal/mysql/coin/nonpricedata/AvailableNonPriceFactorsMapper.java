package corr.ai.module.metadata.dal.mysql.coin.nonpricedata;

import java.util.*;

import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.framework.mybatis.core.query.MPJLambdaWrapperX;
import corr.ai.module.metadata.controller.app.coin.vo.AppAvailableNonPriceFactorsPageReqVO;
import corr.ai.module.metadata.dal.dataobject.coin.CoinMetadataInfoDO;
import corr.ai.module.metadata.dal.dataobject.coin.nonpricedata.AvailableNonPriceFactorsDO;
import corr.ai.module.metadata.dal.dataobject.coin.nonpricedata.NonPriceFactorsDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 可查询的列 Mapper
 *
 * @author dongchengye
 */
@Mapper
public interface AvailableNonPriceFactorsMapper extends BaseMapperX<AvailableNonPriceFactorsDO> {

    default PageResult<AvailableNonPriceFactorsDO> selectPage(AppAvailableNonPriceFactorsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AvailableNonPriceFactorsDO>()
                .eqIfPresent(AvailableNonPriceFactorsDO::getBelongCoinId, reqVO.getBelongCoinId())
                .likeIfPresent(AvailableNonPriceFactorsDO::getFactorType, reqVO.getFactorType())
                .likeIfPresent(AvailableNonPriceFactorsDO::getFactorName, reqVO.getFactorName())
                .likeIfPresent(AvailableNonPriceFactorsDO::getFactorKeyName, reqVO.getFactorKeyName())
                .eqIfPresent(AvailableNonPriceFactorsDO::getMinInterval, reqVO.getMinInterval())
                .eqIfPresent(AvailableNonPriceFactorsDO::getHasIndicators, reqVO.getHasIndicators())
                .eqIfPresent(AvailableNonPriceFactorsDO::getNonPriceTable, reqVO.getNonPriceTable())
                .eqIfPresent(AvailableNonPriceFactorsDO::getComplexNonPriceFactor, reqVO.getComplexNonPriceFactor())
                .betweenIfPresent(AvailableNonPriceFactorsDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AvailableNonPriceFactorsDO::getFactorId));
    }

    default List<AvailableNonPriceFactorsDO> listNonPriceFactors(long coinId, String interval) {
        LambdaQueryWrapperX<AvailableNonPriceFactorsDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(AvailableNonPriceFactorsDO::getBelongCoinId, coinId);
        wrapperX.eq(AvailableNonPriceFactorsDO::getMinInterval, interval);
        return selectList(wrapperX);
    }

    default NonPriceFactorsDO selectNonPriceFactorById(long factorId) {
        MPJLambdaWrapperX<AvailableNonPriceFactorsDO> wrapperX = new MPJLambdaWrapperX<>();
        wrapperX.leftJoin(CoinMetadataInfoDO.class, CoinMetadataInfoDO::getMetaId, AvailableNonPriceFactorsDO::getBelongCoinId);
        wrapperX.select(AvailableNonPriceFactorsDO::getFactorId);
        wrapperX.select(AvailableNonPriceFactorsDO::getBelongCoinId);
        wrapperX.select(AvailableNonPriceFactorsDO::getFactorType);
        wrapperX.select(AvailableNonPriceFactorsDO::getFactorName);
        wrapperX.select(AvailableNonPriceFactorsDO::getFactorKeyName);
        wrapperX.select(AvailableNonPriceFactorsDO::getMinInterval);
        wrapperX.select(AvailableNonPriceFactorsDO::getHasIndicators);
        wrapperX.select(AvailableNonPriceFactorsDO::getNonPriceTable);
        wrapperX.select(AvailableNonPriceFactorsDO::getComplexNonPriceFactor);
        wrapperX.select(CoinMetadataInfoDO::getSymbol);
        wrapperX.eq(AvailableNonPriceFactorsDO::getFactorId, factorId);
        return selectJoinOne(NonPriceFactorsDO.class, wrapperX);
    }

}
