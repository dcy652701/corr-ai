package corr.ai.module.metadata.dal.mysql.data;

import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.module.metadata.dal.dataobject.data.ApiDO;
import corr.ai.module.metadata.dal.dataobject.data.ApiNameIdDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dongchengye
 */
@Mapper
public interface ApiInfoMapper extends BaseMapperX<ApiDO> {
    List<ApiNameIdDO> getApiIdList(@Param("sym") String sym);
}
