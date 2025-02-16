package corr.ai.module.metadata.controller.app.assetname.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author: 董丞业
 * @CreateTime: 2024-09-12
 * @Description: 所有可查询列的列表
 * @Version: 1.0
 */
@Data
@Schema(description = "所有列名")
public class AssetNameVO {
    @Schema(description = "接口类型")
    private String dataType;
    @Schema(description = "接口名称")
    private List<String> dataName;
}
