package corr.ai.module.metadata.dal.mysql.data;

import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.module.metadata.dal.dataobject.data.CoinAssetDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: 董丞业
 * @CreateTime: 2024-09-11
 * @Description:
 * @Version: 1.0
 */
@Mapper
public interface CoinAssetMapper extends BaseMapperX<CoinAssetDO> {

    default List<CoinAssetDO> listCols(){
        LambdaQueryWrapperX<CoinAssetDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.select(CoinAssetDO::getDataType,CoinAssetDO::getDataName);
        return selectList(wrapperX);
    }
}
