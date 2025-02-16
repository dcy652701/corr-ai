package corr.ai.module.member.controller.app.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 邮箱注册body
 *
 * @author dongchengye
 */
@Data
@Schema(description = "用户邮箱注册的body")
public class AppAuthEmailRegisterReqVO {
    @Schema(description = "邮箱")
    @NotNull(message = "邮箱不能为空")
    private String email;
    @Schema(description = "密码")
    @NotNull(message = "密码不能为空")
    private String password;
    @Schema(description = "确认密码")
    @NotNull(message = "确认密码不能为空")
    private String confirmPassword;
    @Schema(description = "验证码")
    @NotNull(message = "验证码不能为空")
    private String verifyCode;
}
