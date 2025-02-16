package corr.ai.module.infra.dal.mysql.db;

import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.module.infra.dal.dataobject.db.DataSourceConfigDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源配置 Mapper
 *
 * @author CorrAi
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperX<DataSourceConfigDO> {
}
