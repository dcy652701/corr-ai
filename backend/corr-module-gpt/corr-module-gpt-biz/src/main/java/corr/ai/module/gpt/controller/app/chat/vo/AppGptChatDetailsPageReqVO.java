package corr.ai.module.gpt.controller.app.chat.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import corr.ai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static corr.ai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户 APP - 对话详情分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppGptChatDetailsPageReqVO extends PageParam {

    @Schema(description = "对话详情主键", example = "1")
    private Long id;

    @Schema(description = "会话编号", example = "1")
    private Long sessionId;

    @Schema(description = "消息角色", example = "assistant")
    private String role;

    @Schema(description = "消息内容")
    private String content;

    @Schema(description = "消息时间")
    private LocalDateTime chatTimestamp;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}