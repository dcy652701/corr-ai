package corr.ai.module.metadata.dal.dataobject.data;

import lombok.Data;

import java.util.List;

/**
 * @Author: 董丞业
 * @CreateTime: 2024-09-12
 * @Description: 列出数据列信息
 * @Version: 1.0
 */
@Data
public class AssetNameDO {
    private String dataType;
    private List<String> dataName;
}
