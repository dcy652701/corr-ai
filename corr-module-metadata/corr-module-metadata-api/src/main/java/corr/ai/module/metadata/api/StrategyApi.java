package corr.ai.module.metadata.api;

import cn.hutool.json.JSONObject;
import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.module.metadata.consts.ApiConstants;
import corr.ai.module.metadata.dto.strategy.StrategyConfigReqDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 策略 rpc接口
 * @author dongchengye
 */
@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC 服务 - 策略服务的rpc接口")
public interface StrategyApi {
    String PREFIX = ApiConstants.PREFIX + "/strategy";

    @PostMapping(PREFIX + "/getStrategyId")
    CommonResult<Long> generateStrategyId(@RequestBody StrategyConfigReqDTO strategyConfigReqDTO);

    @GetMapping(PREFIX+"/get")
    CommonResult<JSONObject> getStrategyConfigById(@RequestParam("sid") Long sid);

}
