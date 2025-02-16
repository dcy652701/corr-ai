package corr.ai.module.metadata.dal.mysql.coin.pricedata;

import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.module.metadata.dal.dataobject.coin.pricedata.SubIndicatorsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dongchengye
 */
@Mapper
public interface SubIndicatorsMapper extends BaseMapperX<SubIndicatorsDO> {
    default List<SubIndicatorsDO> listSubIndByMainIndId(List<Long> ids){
        LambdaQueryWrapperX<SubIndicatorsDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.in(SubIndicatorsDO::getMainIndId,ids);
        return selectList(wrapperX);
    }
}
