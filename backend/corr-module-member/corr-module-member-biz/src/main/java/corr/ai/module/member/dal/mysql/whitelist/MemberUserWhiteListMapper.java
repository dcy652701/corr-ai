package corr.ai.module.member.dal.mysql.whitelist;

import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.module.member.dal.dataobject.whitelist.MemberUserWhiteListDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author dongchengye
 */
@Mapper
public interface MemberUserWhiteListMapper extends BaseMapperX<MemberUserWhiteListDO> {
    default MemberUserWhiteListDO getWhiteUserByEmail(String email){
        LambdaQueryWrapperX<MemberUserWhiteListDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(MemberUserWhiteListDO::getEmail,email);
        return selectOne(wrapperX);
    }
}
