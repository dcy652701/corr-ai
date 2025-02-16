package corr.ai.module.member.dal.mysql.vip;

import corr.ai.framework.mybatis.core.mapper.BaseMapperX;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.module.member.dal.dataobject.vip.MemberVipInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

/**
 * @author dongchengye
 */
@Mapper
public interface MemberVipInfoMapper extends BaseMapperX<MemberVipInfoDO> {
    default MemberVipInfoDO findVIpByFee(BigDecimal fee){
        LambdaQueryWrapperX<MemberVipInfoDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(MemberVipInfoDO::getPayment,fee);
        return selectOne(wrapperX);
    }
}
