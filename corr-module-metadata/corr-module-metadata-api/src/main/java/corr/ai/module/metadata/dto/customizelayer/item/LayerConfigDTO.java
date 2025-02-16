package corr.ai.module.metadata.dto.customizelayer.item;

import cn.hutool.json.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author dongchengye
 */
@Schema(description = "每一层的配置")
@Data
public class LayerConfigDTO {
    private Integer order;
    private Long parentId;
    private String targetColName;
    private String indName;
    /**
     * 指标使用场景，0通用指标，1k线专属指标
     */
    private Integer useScene;
    private JSONObject payload;
}
