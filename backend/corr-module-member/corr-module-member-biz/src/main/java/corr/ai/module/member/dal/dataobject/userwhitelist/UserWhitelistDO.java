package corr.ai.module.member.dal.dataobject.userwhitelist;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 可注册的白名单 DO
 *
 * @author 芋道源码
 */
@TableName("member_user_whitelist")
@KeySequence("member_user_whitelist_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWhitelistDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long wid;
    /**
     * 用户邮箱
     */
    private String email;

}