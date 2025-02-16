package corr.ai.module.member.dal.dataobject.vip;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * @author dongchengye
 */
@TableName(value = "corr_member_vip_privilege", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberVipPrivilegeDO extends BaseDO {
    /**
     * id
     */
    @TableId(value = "privilege_id",type = IdType.ASSIGN_ID)
    private Long privilegeId;
    /**
     * 权益描述
     */
    private String description;
    /**
     * 权益阈值
     */
    private Integer threshold;
}
