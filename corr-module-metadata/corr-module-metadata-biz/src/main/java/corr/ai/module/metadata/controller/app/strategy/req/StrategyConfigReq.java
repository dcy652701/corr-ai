package corr.ai.module.metadata.controller.app.strategy.req;

import cn.hutool.json.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author dongchengye
 */
@Data
@Schema(description = "策略配置请求")
public class StrategyConfigReq {
    @Schema(description = "策略id")
    private Long strategyId;
    @Schema(description = "策略的缩略图名称")
    @NotNull(message = "缩略图名称不能为空")
    private String img;
    @Schema(description = "策略名称")
    private String strategyName;
    @Schema(description = "输入模式，0键盘、1拖拽")
    private Integer inputMode;
    @Schema(description = "价格数据查询参数")
    private List<JSONObject> priceDataQueryParam;
    @Schema(description = "非价格数据查询参数")
    private List<JSONObject> nonPriceDataQueryParam;
}
