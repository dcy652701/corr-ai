package corr.ai.module.metadata.api;

import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.module.metadata.consts.ApiConstants;
import corr.ai.module.metadata.dto.optimizer.OptimizerConfigReqDTO;
import corr.ai.module.metadata.dto.optimizer.OptimizerAndStrategyConfigResultDTO;
import corr.ai.module.metadata.dto.optimizer.OptimizerStatusDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author dongchengye
 */
@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC 服务 - 优化器配置数据服务")
public interface OptimizerApi {
    String PREFIX = ApiConstants.PREFIX + "/optimizer";

    @PostMapping(PREFIX+"/createOptimizer")
    CommonResult<Long> createOptimizer(@RequestBody OptimizerConfigReqDTO reqDTO);

    @PostMapping(PREFIX+"/updateStatus")
    CommonResult<Boolean> updateStatusAndPercentage(@RequestBody OptimizerStatusDTO statsDto);

    @GetMapping(PREFIX+"/getOptimizerConfig")
    CommonResult<OptimizerAndStrategyConfigResultDTO> getOptimizerInfo(@RequestParam("oid") Long oid);
}
