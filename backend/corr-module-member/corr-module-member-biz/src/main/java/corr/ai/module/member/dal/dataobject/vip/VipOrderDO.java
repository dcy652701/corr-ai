package corr.ai.module.member.dal.dataobject.vip;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author dongchengye
 */
@TableName(value = "corr_vip_order", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VipOrderDO extends BaseDO {
    @TableId(value = "order_id",type = IdType.ASSIGN_ID)
    private Long orderId;
    private Long vipId;
    /**
     * 开通的用户id
     */
    private Long applyUserId;
    /**
     * 待支付金额
     */
    private BigDecimal needToPay;
    /**
     * 用户实际支付
     */
    private BigDecimal userPay;
    /**
     * 订单过期时间
     */
    private LocalDateTime orderExpireTime;
    /**
     * 已支付/未支付
     */
    private Boolean paid;
    /**
     * 会员过期时间
     */
    private LocalDateTime vipExpireTime;
    /**
     * 订单过期取消或者用户主动取消
     */
    private Boolean cancel;
}
