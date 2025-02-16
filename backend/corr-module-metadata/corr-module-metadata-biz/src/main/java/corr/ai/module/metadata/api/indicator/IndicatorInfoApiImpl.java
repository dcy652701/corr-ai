package corr.ai.module.metadata.api.indicator;

import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.module.metadata.api.IndicatorInfoApi;
import corr.ai.module.metadata.service.coin.pricedata.AvailableIndicatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dongchengye
 */
@RestController
@Validated
@Slf4j
public class IndicatorInfoApiImpl implements IndicatorInfoApi {

    @Autowired
    private AvailableIndicatorService availableIndicatorService;

    @Override
    public CommonResult<List<Long>> getAllIndicatorId() {
        List<Long> indIds = availableIndicatorService.listIndicatorIds();
        return CommonResult.success(indIds);
    }
}
