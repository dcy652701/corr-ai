package corr.ai.module.gpt.controller.app.chat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "用户 APP - 对话详情新增/修改 Request VO")
@Data
public class AppGptChatDetailsSaveReqVO {

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

    @Schema(description = "使用的模型名称")
    private String model;

}