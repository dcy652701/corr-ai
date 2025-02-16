package corr.ai.module.member.service.vip;

import corr.ai.module.member.controller.app.vip.req.VipOrderCreateReq;
import corr.ai.module.member.controller.app.vip.resp.VipOrderRespVO;
import corr.ai.module.member.dal.dataobject.vip.MemberVipInfoDO;
import corr.ai.module.member.dal.dataobject.vip.MemberVipPrivilegeDO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author dongchengye
 */
public interface VipService {

    /**
     * 列出所有的会员套餐
     *
     * @return
     */
    List<MemberVipInfoDO> listAllVip();

    /**
     * 根据ip查询套餐
     *
     * @param vipId
     * @return
     */
    List<MemberVipPrivilegeDO> getVipById(Long vipId);

    /**
     * 创建待支付订单
     *
     * @param req
     */
    VipOrderRespVO createVipOrder(VipOrderCreateReq req);

    /**
     * 用户支付后的操作
     *
     * @param orderId
     */
    void applyPayment(Long orderId, BigDecimal userPay);

}
