package corr.ai.module.gpt.controller.app.chat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "用户 APP - 对话信息新增/修改 Request VO")
@Data
public class AppGptChatSessionsSaveReqVO {

    @Schema(description = "会话主键", example = "1")
    private Long id;

    @Schema(description = "用户编号，引用 member_user 表的主键", example = "290")
    private Long userId;

    @Schema(description = "会话标题")
    private String title;

    @Schema(description = "使用的模型名称")
    private String model;

}