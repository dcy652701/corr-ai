package corr.ai.module.metadata.controller.app.optimizerconfig.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import corr.ai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static corr.ai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户 APP - 优化器信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppOptimizerConfigPageReqVO extends PageParam {

    @Schema(description = "要优化的策略id", example = "3618")
    private Long strategyId;

    @Schema(description = "coin id")
    private Long coinId;

    @Schema(description = "选择的优化器")
    private String optimizer;

    @Schema(description = "优化器配置")
    private String optimizerConfig;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
