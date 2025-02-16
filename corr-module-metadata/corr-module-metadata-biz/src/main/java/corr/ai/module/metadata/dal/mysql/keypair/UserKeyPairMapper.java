package corr.ai.module.metadata.dal.mysql.keypair;

import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.module.metadata.dal.dataobject.keypair.UserKeyPairDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author dongchengye
 */
@Mapper
public interface UserKeyPairMapper extends BaseMapperX<UserKeyPairDO> {
}
