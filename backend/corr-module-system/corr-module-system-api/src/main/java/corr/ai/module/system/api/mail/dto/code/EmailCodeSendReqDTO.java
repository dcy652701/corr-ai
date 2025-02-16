package corr.ai.module.system.api.mail.dto.code;

import corr.ai.framework.common.validation.InEnum;
import corr.ai.framework.common.validation.Mobile;
import corr.ai.module.system.enums.sms.SmsSceneEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "RPC 服务 - 邮箱验证码的发送 Request DTO")
@Data
public class EmailCodeSendReqDTO {

    @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED, example = "15601691300@qq.com")
    @Email
    @NotEmpty(message = "邮箱不能为空")
    private String email;

    @Schema(description = "发送场景", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "发送场景不能为空")
    @InEnum(SmsSceneEnum.class)
    private Integer scene;
    @Schema(description = "发送 IP", requiredMode = Schema.RequiredMode.REQUIRED, example = "10.20.30.40")
    @NotEmpty(message = "发送 IP 不能为空")
    private String createIp;

}
