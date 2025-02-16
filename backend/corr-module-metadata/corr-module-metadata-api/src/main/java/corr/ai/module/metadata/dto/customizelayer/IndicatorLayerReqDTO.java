package corr.ai.module.metadata.dto.customizelayer;

import corr.ai.module.metadata.dto.customizelayer.item.LayerConfigDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author dongchengye
 */
@Data
@Schema(description = "自定义指标的请求body")
public class IndicatorLayerReqDTO {
    @Schema(description = "startTime")
    private String startTime;
    @Schema(description = "endTime")
    private String endTime;
    @Schema(description = "interval")
    private String interval;
    @Schema(description = "coinId")
    private Long coinId;
    @Schema(description = "非价格数据的factor id")
    private Long factorId;
    @Schema(description = "数据类型，1价格数据，2非价格数据，3k线数据")
    private Integer priceType;
    @Schema(description = "factor列名")
    private String colName;
    @Schema(description = "缓存key")
    private String cacheKey;
    @Schema(description = "后续嵌套层的配置", implementation = LayerConfigDTO.class)
    private List<LayerConfigDTO> layerConfig;
}
