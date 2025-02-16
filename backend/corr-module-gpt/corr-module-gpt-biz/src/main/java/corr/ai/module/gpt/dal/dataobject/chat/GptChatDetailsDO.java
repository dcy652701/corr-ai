package corr.ai.module.gpt.dal.dataobject.chat;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 对话详情 DO
 *
 * @author CorrAi
 */
@TableName("corr_gpt_chat_details")
@KeySequence("corr_gpt_chat_details_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GptChatDetailsDO extends BaseDO {

    /**
     * 对话详情主键
     */
    @TableId
    private Long id;
    /**
     * 会话编号
     */
    private Long sessionId;
    /**
     * 消息角色
     */
    private String role;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息时间
     */
    private LocalDateTime chatTimestamp;

}