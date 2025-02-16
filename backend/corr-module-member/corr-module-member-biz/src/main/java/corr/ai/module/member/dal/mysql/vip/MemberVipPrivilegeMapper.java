package corr.ai.module.member.dal.mysql.vip;

import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.framework.mybatis.core.query.MPJLambdaWrapperX;
import corr.ai.module.member.dal.dataobject.vip.MemberVipPrivilegeDO;
import corr.ai.module.member.dal.dataobject.vip.MemberVipPrivilegeInfoRelationDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author dongchengye
 */
@Mapper
public interface MemberVipPrivilegeMapper extends BaseMapperX<MemberVipPrivilegeDO> {
    default List<MemberVipPrivilegeDO> selectPrivilegeListByVipId(Long vipId){
        MPJLambdaWrapperX<MemberVipPrivilegeDO> wrapperX = new MPJLambdaWrapperX<>();
        wrapperX.selectAll(MemberVipPrivilegeDO.class);
        wrapperX.leftJoin(MemberVipPrivilegeInfoRelationDO.class,MemberVipPrivilegeInfoRelationDO::getPrivilegeId,MemberVipPrivilegeDO::getPrivilegeId);
        wrapperX.eq(MemberVipPrivilegeInfoRelationDO::getVipId,vipId);
        return selectJoinList(MemberVipPrivilegeDO.class, wrapperX);
    }
}
