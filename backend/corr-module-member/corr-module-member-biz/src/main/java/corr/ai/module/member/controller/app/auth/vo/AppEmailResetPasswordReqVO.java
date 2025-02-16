package corr.ai.module.member.controller.app.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * 重置密码的req
 * @author dongchengye
 */
@Schema(description = "用户 APP - 重置密码 Request VO")
@Data
@Accessors(chain = true)
public class AppEmailResetPasswordReqVO {
    @Email
    @NotNull(message = "邮箱不能为空")
    private String email;
    @NotNull(message = "验证码不能为空")
    private String code;
}
