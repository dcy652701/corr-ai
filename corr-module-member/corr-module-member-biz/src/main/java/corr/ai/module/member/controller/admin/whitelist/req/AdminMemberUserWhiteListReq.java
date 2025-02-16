package corr.ai.module.member.controller.admin.whitelist.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;

/**
 * @author dongchengye
 */
@Data
@Schema(description = "用户白名单")
public class AdminMemberUserWhiteListReq {
    @Schema(description = "要添加的email")
    @Email
    private String email;
}
