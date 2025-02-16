package corr.ai.module.metadata.controller.app.coin.resp;

import lombok.Data;

/**
 * @author dongchengye
 */
@Data
public class CoinInfoRespVO {
    private Long metaId;
    private String symbol;
    private String coinFullName;
    private String coinLogo;
    private String tradePair;
}
