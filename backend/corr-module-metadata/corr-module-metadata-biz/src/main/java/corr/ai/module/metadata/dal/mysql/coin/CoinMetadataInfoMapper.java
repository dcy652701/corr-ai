package corr.ai.module.metadata.dal.mysql.coin;

import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.module.metadata.controller.app.coin.param.CoinInfoParam;
import corr.ai.module.metadata.dal.dataobject.coin.CoinMetadataInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dongchengye
 */
@Mapper
public interface CoinMetadataInfoMapper extends BaseMapperX<CoinMetadataInfoDO> {
    default PageResult<CoinMetadataInfoDO> page(CoinInfoParam param){
        LambdaQueryWrapperX<CoinMetadataInfoDO> wrapperX = new LambdaQueryWrapperX<>();
        return selectPage(param,wrapperX);
    }

    default List<CoinMetadataInfoDO> selectCoinsByIdList(List<Long> ids){
        LambdaQueryWrapperX<CoinMetadataInfoDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.in(CoinMetadataInfoDO::getMetaId,ids);
        return selectList(wrapperX);
    }
}
