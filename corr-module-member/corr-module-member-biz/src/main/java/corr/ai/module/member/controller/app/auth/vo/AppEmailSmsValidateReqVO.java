package corr.ai.module.member.controller.app.auth.vo;

import corr.ai.framework.common.validation.InEnum;
import corr.ai.framework.common.validation.Mobile;
import corr.ai.module.system.enums.sms.SmsSceneEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Schema(description = "用户 APP - 校验邮箱验证码 Request VO")
@Data
@Accessors(chain = true)
public class AppEmailSmsValidateReqVO {

    @Schema(description = "邮箱", example = "15601691234@gmail.com")
    @Email
    private String email;

    @Schema(description = "发送场景,对应 SmsSceneEnum 枚举", example = "1")
    @NotNull(message = "发送场景不能为空")
    @InEnum(SmsSceneEnum.class)
    private Integer scene;

    @Schema(description = "邮箱验证码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotEmpty(message = "邮箱验证码不能为空")
    @Length(min = 4, max = 6, message = "邮箱验证码长度为 4-6 位")
    @Pattern(regexp = "^[0-9]+$", message = "邮箱验证码必须都是数字")
    private String code;

}
