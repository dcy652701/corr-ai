package corr.ai.module.metadata.mq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 币的tv结构的数据
 *
 * @author dongchengye
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinAssetDTO implements Serializable {
    private Long t;
    private Long v;
    private String symbol;
    private String dataType;
    private String dataName;
    private String dataFrequency;
    private Boolean deleted;
    private Long tenantId;
}
