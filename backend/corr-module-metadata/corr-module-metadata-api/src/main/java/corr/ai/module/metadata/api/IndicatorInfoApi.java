package corr.ai.module.metadata.api;

import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.module.metadata.consts.ApiConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author dongchengye
 */
@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC 服务 - 指标信息rpc服务")
public interface IndicatorInfoApi {
    String PREFIX = ApiConstants.PREFIX + "/indicator";

    @PostMapping(PREFIX + "/getIndIds")
    CommonResult<List<Long>> getAllIndicatorId();
}
