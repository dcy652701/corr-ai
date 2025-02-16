package corr.ai.module.gpt.controller.app.chat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "用户 APP - 对话信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppGptChatSessionsRespVO {

    @Schema(description = "会话主键", example = "1")
    @ExcelProperty("会话主键")
    private Long id;

    @Schema(description = "用户编号，引用 member_user 表的主键", example = "290")
    @ExcelProperty("用户编号，引用 member_user 表的主键")
    private Long userId;

    @Schema(description = "会话标题")
    @ExcelProperty("会话标题")
    private String title;

    @Schema(description = "使用的模型名称")
    @ExcelProperty("使用的模型名称")
    private String model;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}