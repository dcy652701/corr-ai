package corr.ai.module.gpt.controller.app.chat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Schema(description = "用户 APP - 对话相关新增 Request VO")
@Data
public class QueryCreateChatParam {

    @Schema(description = "消息内容")
    @NotBlank(message = "消息内容不能为空")
    private String content;

    @Schema(description = "使用的模型名称")
    @NotBlank(message = "使用的模型名称不能为空")
    private String model;
}
