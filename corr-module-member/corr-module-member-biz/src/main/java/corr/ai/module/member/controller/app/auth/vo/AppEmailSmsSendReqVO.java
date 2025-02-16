package corr.ai.module.member.controller.app.auth.vo;

import corr.ai.framework.common.validation.InEnum;
import corr.ai.framework.common.validation.Mobile;
import corr.ai.module.system.enums.sms.SmsSceneEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Schema(description = "用户 APP - 发送邮箱验证码 Request VO")
@Data
@Accessors(chain = true)
public class AppEmailSmsSendReqVO {

    @Schema(description = "邮箱", example = "15601691234@gmail.com")
    @Email
    private String mobile;

    @Schema(description = "发送场景,对应 SmsSceneEnum 枚举", example = "1")
    @NotNull(message = "发送场景不能为空")
    @InEnum(SmsSceneEnum.class)
    private Integer scene;

}
