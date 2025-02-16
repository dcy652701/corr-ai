package corr.ai.module.member.controller.app.auth.vo;

import cn.hutool.core.util.StrUtil;
import corr.ai.framework.common.validation.InEnum;
import corr.ai.module.system.enums.social.SocialTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author dongchengye
 */
@Schema(description = "用户 APP - 邮箱 + 密码登录 Request VO,如果登录并绑定社交用户，需要传递 social 开头的参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppWalletAuthLoginReqVO {
    @Schema(description = "钱包地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "0x1db583314B49bA339Fc13cD49D80459EAC17351e")
    @NotEmpty(message = "钱包不能为空")
    private String walletAddress;
    @Schema(description = "钱包返回的签名sign")
    @NotNull(message = "签名不能为空")
    private String signedHash;
    @Schema(description = "原始的message")
    @NotNull(message = "原始的message不能为空")
    private String originalMessageHashInHex;
    // ========== 绑定社交登录时，需要传递如下参数 ==========

    @Schema(description = "社交平台的类型，参见 SocialTypeEnum 枚举值", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @InEnum(SocialTypeEnum.class)
    private Integer socialType;

    @Schema(description = "授权码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String socialCode;

    @Schema(description = "state", requiredMode = Schema.RequiredMode.REQUIRED, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62")
    private String socialState;

    @AssertTrue(message = "授权码不能为空")
    public boolean isSocialCodeValid() {
        return socialType == null || StrUtil.isNotEmpty(socialCode);
    }

    @AssertTrue(message = "授权 state 不能为空")
    public boolean isSocialState() {
        return socialType == null || StrUtil.isNotEmpty(socialState);
    }
}
