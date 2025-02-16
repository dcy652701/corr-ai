package corr.ai.module.metadata.dto.optimizer;

import cn.hutool.json.JSONObject;
import lombok.Data;

/**
 * 策略与优化器配置的dto
 * @author dongchengye
 */
@Data
public class OptimizerAndStrategyConfigResultDTO {
    private Long optimizerId;
    private Long strategyId;
    private JSONObject optimizerConfig;
    private JSONObject strategyConfig;
}
