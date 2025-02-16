package corr.ai.module.metadata.api.optimizer;

import corr.ai.framework.common.pojo.CommonResult;
import corr.ai.module.metadata.api.OptimizerApi;
import corr.ai.module.metadata.controller.app.optimizerconfig.vo.AppOptimizerConfigSaveReqVO;
import corr.ai.module.metadata.converter.optimizer.OptimizerConverter;
import corr.ai.module.metadata.dal.dataobject.optimizerconfig.OptimizerAndStrategyConfigDO;
import corr.ai.module.metadata.dto.optimizer.OptimizerConfigReqDTO;
import corr.ai.module.metadata.dto.optimizer.OptimizerAndStrategyConfigResultDTO;
import corr.ai.module.metadata.dto.optimizer.OptimizerStatusDTO;
import corr.ai.module.metadata.service.optimizerconfig.OptimizerConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dongchengye
 */
@RestController
@Validated
@Slf4j
public class OptimizerApiImpl implements OptimizerApi {
    @Resource
    private OptimizerConfigService optimizerConfigService;

    @Autowired
    private OptimizerConverter optimizerConverter;

    @Override
    public CommonResult<Long> createOptimizer(OptimizerConfigReqDTO reqDTO) {
        AppOptimizerConfigSaveReqVO createReqVO = optimizerConverter.convert(reqDTO);
        Long oId = optimizerConfigService.createOptimizerConfig(createReqVO);
        return CommonResult.success(oId);
    }

    @Override
    public CommonResult<Boolean> updateStatusAndPercentage(OptimizerStatusDTO statusDTO) {
        boolean b = optimizerConfigService.updateOptimizerStatus(statusDTO);
        return CommonResult.success(b);
    }

    @Override
    public CommonResult<OptimizerAndStrategyConfigResultDTO> getOptimizerInfo(Long oid) {
        OptimizerAndStrategyConfigDO optimizerInfoRpc = optimizerConfigService.getOptimizerInfoRpc(oid);
        OptimizerAndStrategyConfigResultDTO dto = optimizerConverter.convert(optimizerInfoRpc);
        return CommonResult.success(dto);
    }
}
