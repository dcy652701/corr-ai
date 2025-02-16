package corr.ai.module.metadata.controller.app.coin.resp;

import corr.ai.module.metadata.controller.app.coin.vo.AppAvailableNonPriceFactorsRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author dongchengye
 */
@Data
@Schema(description = "总的factor，涵盖价格数据与非价格数据")
@AllArgsConstructor
public class TotalFactorVO {
    @Schema(description = "价格列的集合")
    private List<AvailableFoctorVO> priceFactor;
    @Schema(description = "非价格列的集合")
    private Map<String, Map<String, List<AppAvailableNonPriceFactorsRespVO>>> nonPriceFactor;
}
