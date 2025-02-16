package corr.ai.module.gpt.dal.dataobject.chat;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 对话信息 DO
 *
 * @author CorrAi
 */
@TableName("corr_gpt_chat_sessions")
@KeySequence("corr_gpt_chat_sessions_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GptChatSessionsDO extends BaseDO {

    /**
     * 会话主键
     */
    @TableId
    private Long id;
    /**
     * 用户编号，引用 member_user 表的主键
     */
    private Long userId;
    /**
     * 会话标题
     */
    private String title;
    /**
     * 使用的模型名称
     */
    private String model;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}