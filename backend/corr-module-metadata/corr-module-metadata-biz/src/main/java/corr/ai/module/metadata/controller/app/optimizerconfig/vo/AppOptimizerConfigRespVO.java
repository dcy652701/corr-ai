package corr.ai.module.metadata.controller.app.optimizerconfig.vo;

import cn.hutool.json.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "用户 APP - 优化器信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppOptimizerConfigRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15275")
    @ExcelProperty("编号")
    private Long oid;

    @Schema(description = "要优化的策略id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3618")
    @ExcelProperty("要优化的策略id")
    private Long strategyId;

    /**
     * coinId
     */
    @Schema(description = "coin id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3618")
    @ExcelProperty("coin id")
    private Long coinId;

    @Schema(description = "选择的优化器", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("选择的优化器")
    private String optimizer;

    @Schema(description = "优化器配置", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("优化器配置")
    private String optimizerConfig;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;
    /**
     * 当前的状态
     * 0已完成,1运行中,2运行中断
     */
    @Schema(description = "0已完成,1运行中,2运行中断")
    @ExcelProperty("0已完成,1运行中,2运行中断")
    private Integer stats;
    /**
     * 最终的百分比
     */
    @Schema(description = "最终的百分比")
    @ExcelProperty("最终的百分比")
    private Integer finalPercentage;

    @Schema(description = "策略配置")
    private JSONObject strategyConfig;

    @Schema(description = "Strategy factors")
    private String strategyFactors;
}
