package corr.ai.module.metadata.dto.optimizer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dongchengye
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptimizerStatusDTO {
    private Long optimizerId;
    private Integer stats;
    private Integer finalPercentage;
}
