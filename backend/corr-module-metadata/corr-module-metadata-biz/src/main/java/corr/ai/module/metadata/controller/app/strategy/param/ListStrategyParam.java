package corr.ai.module.metadata.controller.app.strategy.param;

import corr.ai.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dongchengye
 */
@Data
@Schema(description = "分页及模糊查询")
public class ListStrategyParam extends PageParam {

    @Schema(description = "策略名称")
    private String strategyName;

    @Schema(description = "交易对id")
    @NotNull(message = "trade pair can not be null")
    private Long coinId;

}
