package corr.ai.module.metadata.controller.app.coin.param;

import corr.ai.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author dongchengye
 */
@Data
@Schema(description = "指标查询参数")
public class IndicatorQueryParam extends PageParam {
    @Schema(description = "指标id")
    @NotNull(message = "indicator id must not be null")
    private Long foctorId;
    @Schema(description = "是否是价格数据")
    @NotNull(message = "factor type must not be null")
    private Boolean isPrice;
//    @Schema(description = "终止符，true就终止查询子指标")
//    @NotNull(message = "finish flag must not be null")
//    private boolean finish;
}
