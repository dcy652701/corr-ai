package corr.ai.module.metadata.api.customizelayer;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.module.metadata.api.CustomizeLayerApi;
import corr.ai.module.metadata.controller.app.customizelayer.vo.AppCustomizeLayerSaveReqVO;
import corr.ai.module.metadata.converter.customizelayer.CustomizeLayerConverter;
import corr.ai.module.metadata.dal.dataobject.customizelayer.CustomizeLayerDO;
import corr.ai.module.metadata.dto.customizelayer.CustomizeLayerSaveDTO;
import corr.ai.module.metadata.dto.customizelayer.IndicatorLayerReqDTO;
import corr.ai.module.metadata.service.customizelayer.CustomizeLayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 董丞业
 * @CreateTime: 2025-01-15
 * @Description:
 * @Version: 1.0
 */
@RestController
@Validated
@Slf4j
public class CustomizeLayerApiImpl implements CustomizeLayerApi {

    @Autowired
    private CustomizeLayerConverter customizeLayerConverter;

    @Autowired
    private CustomizeLayerService customizeLayerService;

    @Override
    public CommonResult<Long> createLayerRecord(CustomizeLayerSaveDTO saveDTO) {
        AppCustomizeLayerSaveReqVO saveReqVO = customizeLayerConverter.convert(saveDTO);
        Long layerId = customizeLayerService.createCustomizeLayer(saveReqVO);
        return CommonResult.success(layerId);
    }

    @Override
    public CommonResult<IndicatorLayerReqDTO> getLayerConfigById(Long layerId) {
        CustomizeLayerDO customizeLayer = customizeLayerService.getCustomizeLayer(layerId);
        JSONObject layerConfig = customizeLayer.getLayerConfigObject();
        IndicatorLayerReqDTO indicatorLayerReqDTO = JSONUtil.toBean(layerConfig, IndicatorLayerReqDTO.class);
        return CommonResult.success(indicatorLayerReqDTO);
    }
}
