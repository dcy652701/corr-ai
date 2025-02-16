package corr.ai.module.metadata.dal.mysql.coin.pricedata;

import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.module.metadata.dal.dataobject.coin.pricedata.AvailableIndicatorsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dongchengye
 */
@Mapper
public interface AvailableIndicatorMapper extends BaseMapperX<AvailableIndicatorsDO> {
    default List<AvailableIndicatorsDO> listAvailableInds(List<Long> ids){
        LambdaQueryWrapperX<AvailableIndicatorsDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.in(AvailableIndicatorsDO::getIndId,ids);
        return selectList(wrapperX);
    }

    default List<AvailableIndicatorsDO> listKlineIndicators(){
        LambdaQueryWrapperX<AvailableIndicatorsDO> wrapperX = new LambdaQueryWrapperX<>();
        // useScene 1表示是k线专用
        //wrapperX.eq(AvailableIndicatorsDO::getUseScene,1);
        return selectList(wrapperX);
    }

    default List<Long> listAllIndicatorIds(){
        LambdaQueryWrapperX<AvailableIndicatorsDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(AvailableIndicatorsDO::getUseScene,0);
        List<AvailableIndicatorsDO> availableIndicatorsDOS = selectList(wrapperX);
        return availableIndicatorsDOS.stream().map(AvailableIndicatorsDO::getIndId).collect(Collectors.toList());
    }
}
