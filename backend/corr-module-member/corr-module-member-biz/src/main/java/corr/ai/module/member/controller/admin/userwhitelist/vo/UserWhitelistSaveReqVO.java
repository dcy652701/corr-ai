package corr.ai.module.member.controller.admin.userwhitelist.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 可注册的白名单新增/修改 Request VO")
@Data
public class UserWhitelistSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21846")
    private Long wid;

    @Schema(description = "用户邮箱")
    private String email;

}