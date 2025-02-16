package corr.ai.module.member.dal.mysql.vip;

import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.framework.mybatis.core.query.MPJLambdaWrapperX;
import corr.ai.module.member.dal.dataobject.vip.MemberVipInfoDO;
import corr.ai.module.member.dal.dataobject.vip.VipOrderDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author dongchengye
 */
@Mapper
public interface VipOrderMapper extends BaseMapperX<VipOrderDO> {

    default MemberVipInfoDO findVipInfoByOrderId(Long orderId) {
        MPJLambdaWrapperX<VipOrderDO> wrapperX = new MPJLambdaWrapperX<>();
        wrapperX.select(MemberVipInfoDO::getVipId);
        wrapperX.select(MemberVipInfoDO::getLevel);
        wrapperX.select(MemberVipInfoDO::getPayment);
        wrapperX.select(MemberVipInfoDO::getDurationDay);
        wrapperX.leftJoin(VipOrderDO.class, VipOrderDO::getVipId, MemberVipInfoDO::getVipId);
        wrapperX.eq(VipOrderDO::getOrderId, orderId);
        return selectJoinOne(MemberVipInfoDO.class, wrapperX);
    }
}
