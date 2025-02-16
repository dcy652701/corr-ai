package corr.ai.module.metadata.dto.coin;

import lombok.Data;

/**
 * 币的元数据信息，包括币的符号，k线数据粒度与对应的数仓里的表
 *
 * @author dongchengye
 */
@Data
public class CoinMetadataDTO {
    private Long metaId;
    private String symbol;
    private String coinFullName;
    private String coinLogo;
    private Integer intervals;
    private String intervalUnit;
    private String dwTableName;
}
