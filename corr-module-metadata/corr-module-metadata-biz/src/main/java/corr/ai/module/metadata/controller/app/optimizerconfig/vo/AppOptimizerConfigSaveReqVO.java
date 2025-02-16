package corr.ai.module.metadata.controller.app.optimizerconfig.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "用户 APP - 优化器信息新增/修改 Request VO")
@Data
public class AppOptimizerConfigSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15275")
    private Long oid;

    @Schema(description = "要优化的策略id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3618")
    @NotNull(message = "要优化的策略id不能为空")
    private Long strategyId;

    @Schema(description = "coin id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3618")
    @ExcelProperty("coin id")
    private Long coinId;

    @Schema(description = "选择的优化器", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "选择的优化器不能为空")
    private String optimizer;

    @Schema(description = "优化器配置", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "优化器配置不能为空")
    private String optimizerConfig;
    /**
     * 当前的状态
     * 0已完成,1运行中,2运行中断
     */
    @Schema(description = "当前的状态，0已完成,1运行中,2运行中断")
    private Integer stats;
    /**
     * 最终的百分比
     */
    @Schema(description = "最终百分比")
    private Integer finalPercentage;

}
