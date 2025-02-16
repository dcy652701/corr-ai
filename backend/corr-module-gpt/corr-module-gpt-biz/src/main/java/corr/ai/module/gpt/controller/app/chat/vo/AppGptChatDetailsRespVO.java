package corr.ai.module.gpt.controller.app.chat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "用户 APP - 对话详情 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppGptChatDetailsRespVO {

    @Schema(description = "对话详情主键", example = "1")
    @ExcelProperty("对话详情主键")
    private Long id;

    @Schema(description = "会话编号", example = "1")
    @ExcelProperty("会话编号")
    private Long sessionId;

    @Schema(description = "消息角色", example = "assistant")
    @ExcelProperty("消息角色")
    private String role;

    @Schema(description = "消息内容")
    @ExcelProperty("消息内容")
    private String content;

    @Schema(description = "消息时间")
    @ExcelProperty("消息时间")
    private LocalDateTime chatTimestamp;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}