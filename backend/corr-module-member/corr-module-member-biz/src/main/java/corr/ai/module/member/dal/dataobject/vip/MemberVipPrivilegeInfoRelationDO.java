package corr.ai.module.member.dal.dataobject.vip;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * @author dongchengye
 */
@TableName(value = "corr_member_vip_privilege_info_relation", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberVipPrivilegeInfoRelationDO extends BaseDO {
    /**
     * 主键
     */
    @TableId(value = "privilege_id",type = IdType.ASSIGN_ID)
    private Long rid;
    /**
     * vip的主键
     */
    private Long vipId;
    /**
     * 权益主键
     */
    private Long privilegeId;
}
