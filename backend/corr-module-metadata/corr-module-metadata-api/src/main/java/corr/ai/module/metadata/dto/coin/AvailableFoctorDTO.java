package corr.ai.module.metadata.dto.coin;

import lombok.Data;

import java.util.List;

/**
 * @author dongchengye
 */
@Data
public class AvailableFoctorDTO {
    private Long factorId;
    private Long belongCoinId;
    private String symbol;
    private String factorType;
    private String factorName;
    private String factorKeyName;
    private String minInterval;
    private List<Long> hasIndicatorList;
    private String nonPriceTable;
    private Boolean complexNonPriceFactor;
}
