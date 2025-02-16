package corr.ai.module.metadata.controller.app.coin.req;

import lombok.Data;

/**
 * @author dongchengye
 */
@Data
public class CoinInfoReq {
    private Long metaId;
    private String symbol;
    private Integer intervals;
    private String intervalUnit;
    private String dwTableName;
}
