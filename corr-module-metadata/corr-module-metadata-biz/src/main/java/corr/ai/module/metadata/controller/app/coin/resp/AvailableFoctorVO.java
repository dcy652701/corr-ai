package corr.ai.module.metadata.controller.app.coin.resp;

import lombok.Data;

import java.util.List;

/**
 * @author dongchengye
 */
@Data
public class AvailableFoctorVO {
    private Long foctorId;
    private Long belongCoinId;
    private String foctorType;
    private String foctorName;
    private String minInterval;
    private List<Long> hasIndicatorList;
    private String nonPriceTable;
    private boolean indFlag;
    /**
     * 是否是复杂结构的非价格数据
     */
    private Boolean complexNonPriceFactor;
    private String source;
}
