package corr.ai.module.member.dal.mysql.address;

import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.module.member.dal.dataobject.address.MemberAddressDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberAddressMapper extends BaseMapperX<MemberAddressDO> {

    default MemberAddressDO selectByIdAndUserId(Long id, Long userId) {
        return selectOne(MemberAddressDO::getId, id, MemberAddressDO::getUserId, userId);
    }

    default List<MemberAddressDO> selectListByUserIdAndDefaulted(Long userId, Boolean defaulted) {
        return selectList(new LambdaQueryWrapperX<MemberAddressDO>().eq(MemberAddressDO::getUserId, userId)
                .eqIfPresent(MemberAddressDO::getDefaultStatus, defaulted));
    }

}
