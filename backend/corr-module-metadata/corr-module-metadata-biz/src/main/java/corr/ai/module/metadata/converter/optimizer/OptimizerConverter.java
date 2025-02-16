package corr.ai.module.metadata.converter.optimizer;

import corr.ai.module.metadata.controller.app.optimizerconfig.vo.AppOptimizerConfigRespVO;
import corr.ai.module.metadata.controller.app.optimizerconfig.vo.AppOptimizerConfigSaveReqVO;
import corr.ai.module.metadata.dal.dataobject.optimizerconfig.OptimizerAndStrategyConfigDO;
import corr.ai.module.metadata.dal.dataobject.optimizerconfig.OptimizerResultDO;
import corr.ai.module.metadata.dto.optimizer.OptimizerAndStrategyConfigResultDTO;
import corr.ai.module.metadata.dto.optimizer.OptimizerConfigReqDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author dongchengye
 */
@Mapper(componentModel = "spring")
public interface OptimizerConverter {
    AppOptimizerConfigSaveReqVO convert(OptimizerConfigReqDTO reqDTO);
    @Mappings({
            @Mapping(target = "strategyConfig",expression = "java(source.getStrategyConfig())")
    })
    AppOptimizerConfigRespVO convert(OptimizerResultDO source);

    @Mappings({
            @Mapping(target = "optimizerConfig",expression = "java(source.getOptConfig())"),
            @Mapping(target = "strategyConfig",expression = "java(source.getStrategyConfig())")
    })
    OptimizerAndStrategyConfigResultDTO convert(OptimizerAndStrategyConfigDO source);
}
