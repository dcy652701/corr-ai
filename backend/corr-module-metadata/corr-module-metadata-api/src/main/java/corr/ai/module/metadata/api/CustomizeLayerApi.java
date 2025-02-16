package corr.ai.module.metadata.api;

import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.module.metadata.consts.ApiConstants;
import corr.ai.module.metadata.dto.customizelayer.CustomizeLayerSaveDTO;
import corr.ai.module.metadata.dto.customizelayer.IndicatorLayerReqDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: 董丞业
 * @CreateTime: 2025-01-15
 * @Description: 自定义指标rpc接口
 * @Version: 1.0
 */
@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC 服务 - 自定义指标层rpc服务")
public interface CustomizeLayerApi {
    String PREFIX = ApiConstants.PREFIX + "/customizeLayer";

    /**
     * 创建一个自定义layer，拿到它的id
     *
     * @param saveDTO
     * @return
     */
    @PostMapping(PREFIX + "/createLayer")
    CommonResult<Long> createLayerRecord(@RequestBody CustomizeLayerSaveDTO saveDTO);

    @GetMapping(PREFIX + "/getLayer")
    CommonResult<IndicatorLayerReqDTO> getLayerConfigById(@RequestParam("layerId") Long layerId);
}
