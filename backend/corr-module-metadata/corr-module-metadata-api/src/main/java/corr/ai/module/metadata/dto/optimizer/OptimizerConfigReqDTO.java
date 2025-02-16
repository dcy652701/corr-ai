package corr.ai.module.metadata.dto.optimizer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author dongchengye
 */
@Data
public class OptimizerConfigReqDTO {
    @Schema(description = "要优化的策略id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3618")
    private Long strategyId;

    @Schema(description = "coin id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3618")
    private Long coinId;

    @Schema(description = "选择的优化器", requiredMode = Schema.RequiredMode.REQUIRED)
    private String optimizer;

    @Schema(description = "优化器配置", requiredMode = Schema.RequiredMode.REQUIRED)
    private String optimizerConfig;
}
