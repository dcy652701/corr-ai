package corr.ai.module.metadata.dal.mysql.coin.pricedata;

import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.module.metadata.dal.dataobject.coin.pricedata.AvailableFoctorDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dongchengye
 */
@Mapper
public interface AvailableFoctorMapper extends BaseMapperX<AvailableFoctorDO> {
    default List<AvailableFoctorDO> listFoctors(long coinId, String interval) {
        LambdaQueryWrapperX<AvailableFoctorDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(AvailableFoctorDO::getBelongCoinId, coinId);
        wrapperX.eq(AvailableFoctorDO::getMinInterval, interval);
        return selectList(wrapperX);
    }

    List<String> listAvailableIntervals();
}
