package corr.ai.module.metadata.controller.app.customizelayer.vo;

import cn.hutool.json.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "用户 APP - 用户自定义指标层 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppCustomizeLayerRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32068")
    @ExcelProperty("编号")
    private Long layerId;

    @Schema(description = "最上层的factor", requiredMode = Schema.RequiredMode.REQUIRED, example = "20873")
    @ExcelProperty("最上层的factor")
    private Long parentFactorId;

    @Schema(description = "coin id", requiredMode = Schema.RequiredMode.REQUIRED, example = "27051")
    @ExcelProperty("coin id")
    private Long coinId;

    @Schema(description = "interval", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("interval")
    private String intervals;

    @Schema(description = "自定义指标层的完整配置", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("自定义指标层的完整配置")
    private JSONObject layerConfig;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
