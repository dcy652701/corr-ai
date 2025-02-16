package corr.ai.module.metadata.controller.app.customizelayer.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import corr.ai.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static corr.ai.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "用户 APP - 用户自定义指标层分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppCustomizeLayerPageReqVO extends PageParam {

    @Schema(description = "最上层的factor", example = "20873")
    private Long parentFactorId;

    @Schema(description = "coin id", example = "27051")
    private Long coinId;

    @Schema(description = "interval")
    private String intervals;

    @Schema(description = "自定义指标层的完整配置")
    private String layerConfig;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
