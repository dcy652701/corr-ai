package corr.ai.module.member.controller.admin.userwhitelist.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 可注册的白名单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class UserWhitelistRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21846")
    @ExcelProperty("编号")
    private Long wid;

    @Schema(description = "用户邮箱")
    @ExcelProperty("用户邮箱")
    private String email;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}