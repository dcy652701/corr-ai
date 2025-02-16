package corr.ai.module.metadata.dal.dataobject.strategy;

import lombok.Data;

/**
 * @author dongchengye
 */
@Data
public class TradePairDO {
    private Long coinId;
    private String tradePair;
    private String coinLogo;
}
