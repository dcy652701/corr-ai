package corr.ai.module.member.dal.dataobject.vip;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author dongchengye
 */
@TableName(value = "corr_member_vip_info", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberVipInfoDO extends BaseDO {
    /**
     * id
     */
    @TableId(value = "vip_id", type = IdType.ASSIGN_ID)
    private Long vipId;
    /**
     * vip等级，如月度会员，季度会员，年费会员，svip
     */
    private String level;
    /**
     * 需要支付的费用
     */
    private BigDecimal payment;
    /**
     * 会员持续时间
     */
    private Integer durationDay;
}
