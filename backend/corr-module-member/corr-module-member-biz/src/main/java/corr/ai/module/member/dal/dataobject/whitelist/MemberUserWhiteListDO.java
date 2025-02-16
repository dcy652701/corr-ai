package corr.ai.module.member.dal.dataobject.whitelist;

import com.baomidou.mybatisplus.annotation.TableName;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 注册白名单
 * @author dongchengye
 */
@TableName(value = "member_user_whitelist", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUserWhiteListDO extends BaseDO {
    private Long wid;
    private String email;
}
