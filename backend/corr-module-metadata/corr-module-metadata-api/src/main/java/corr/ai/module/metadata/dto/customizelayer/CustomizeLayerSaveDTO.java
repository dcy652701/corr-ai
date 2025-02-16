package corr.ai.module.metadata.dto.customizelayer;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "用户 APP - 用户自定义指标层新增/修改 Request VO")
@Data
public class CustomizeLayerSaveDTO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "32068")
    private Long layerId;

    @Schema(description = "最上层的factor", requiredMode = Schema.RequiredMode.REQUIRED, example = "20873")
//    @NotNull(message = "最上层的factor不能为空")
    private Long parentFactorId;

    @Schema(description = "coin id", requiredMode = Schema.RequiredMode.REQUIRED, example = "27051")
    @NotNull(message = "coin id不能为空")
    private Long coinId;

    @Schema(description = "interval", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "interval不能为空")
    private String intervals;

    @Schema(description = "自定义指标层的完整配置", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "自定义指标层的完整配置不能为空")
    private JSONObject layerConfig;

    public String getLayerConfigString(){
        return JSONUtil.toJsonStr(layerConfig);
    }

}
