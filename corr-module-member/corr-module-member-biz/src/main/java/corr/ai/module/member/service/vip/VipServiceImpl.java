package corr.ai.module.member.service.vip;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.framework.security.core.util.SecurityFrameworkUtils;
import corr.ai.module.member.controller.app.vip.req.VipOrderCreateReq;
import corr.ai.module.member.controller.app.vip.resp.VipOrderRespVO;
import corr.ai.module.member.convert.vip.VipConverter;
import corr.ai.module.member.dal.dataobject.vip.MemberVipInfoDO;
import corr.ai.module.member.dal.dataobject.vip.MemberVipPrivilegeDO;
import corr.ai.module.member.dal.dataobject.vip.VipOrderDO;
import corr.ai.module.member.dal.mysql.vip.MemberVipInfoMapper;
import corr.ai.module.member.dal.mysql.vip.MemberVipPrivilegeInfoRelationMapper;
import corr.ai.module.member.dal.mysql.vip.MemberVipPrivilegeMapper;
import corr.ai.module.member.dal.mysql.vip.VipOrderMapper;
import corr.ai.module.member.mq.producer.vip.UserApplyVipProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.el.LambdaExpression;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * @author dongchengye
 */
@Service
public class VipServiceImpl implements VipService {

    @Autowired
    private MemberVipInfoMapper memberVipInfoMapper;

    @Autowired
    private MemberVipPrivilegeMapper privilegeMapper;

    @Autowired
    private MemberVipPrivilegeInfoRelationMapper relationMapper;

    @Autowired
    private VipOrderMapper orderMapper;

    @Autowired
    private VipConverter vipConverter;

    @Autowired
    private UserApplyVipProducer userApplyVipProducer;

    @Override
    public List<MemberVipInfoDO> listAllVip() {
        return memberVipInfoMapper.selectList();
    }

    @Override
    public List<MemberVipPrivilegeDO> getVipById(Long vipId) {
        List<MemberVipPrivilegeDO> privilegeList = privilegeMapper.selectPrivilegeListByVipId(vipId);
        return privilegeList;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public VipOrderRespVO createVipOrder(VipOrderCreateReq req) {
        VipOrderDO vipOrderDO = vipConverter.convert(req);
        // 现在的系统时间
        LocalDateTime localDateTime = LocalDateTime.now();
        // 获取UTC的ZoneOffset
        ZoneOffset utcOffset = ZoneOffset.UTC;
        //用户支付后的开通时间
        LocalDateTime paidTime = localDateTime.atOffset(utcOffset).toLocalDateTime();
        //就给15分钟支付时间
        LocalDateTime payExpireTime = paidTime.plusMinutes(15L);
        vipOrderDO.setOrderExpireTime(payExpireTime);
        orderMapper.insert(vipOrderDO);
        return vipConverter.convert(vipOrderDO);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void applyPayment(Long orderId, BigDecimal userPay) {
        MemberVipInfoDO vipInfoByOrder = orderMapper.findVipInfoByOrderId(orderId);
        // 现在的系统时间
        LocalDateTime localDateTime = LocalDateTime.now();
        // 获取UTC的ZoneOffset
        ZoneOffset utcOffset = ZoneOffset.UTC;
        //用户支付后的开通时间
        LocalDateTime paidTime = localDateTime.atOffset(utcOffset).toLocalDateTime();
        LocalDateTime vipExpireTime = paidTime.plusDays(vipInfoByOrder.getDurationDay());
        UpdateWrapper<VipOrderDO> wrapper = new UpdateWrapper<>();
        wrapper.set("order_id", orderId);
        wrapper.set("user_pay", userPay);
        wrapper.set("paid", true);
        wrapper.set("vip_expire_time", vipExpireTime);
        orderMapper.update(wrapper);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                userApplyVipProducer.notifyToApplyVipForUser(SecurityFrameworkUtils.getLoginUserId(), vipInfoByOrder.getVipId());
            }
        });
    }
}
