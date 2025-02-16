package corr.ai.module.metadata.converter.customizelayer;

import corr.ai.module.metadata.controller.app.customizelayer.vo.AppCustomizeLayerRespVO;
import corr.ai.module.metadata.controller.app.customizelayer.vo.AppCustomizeLayerSaveReqVO;
import corr.ai.module.metadata.dal.dataobject.customizelayer.CustomizeLayerDO;
import corr.ai.module.metadata.dto.customizelayer.CustomizeLayerSaveDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @Author: 董丞业
 * @CreateTime: 2025-01-15
 * @Description:
 * @Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface CustomizeLayerConverter {

    @Mappings({
            @Mapping(target = "layerConfig", expression = "java(source.getLayerConfigString())")
    })
    CustomizeLayerDO convert(AppCustomizeLayerSaveReqVO source);

    @Mappings({
            @Mapping(target = "layerConfig", expression = "java(source.getLayerConfigObject())")
    })
    AppCustomizeLayerRespVO convert(CustomizeLayerDO source);


    AppCustomizeLayerSaveReqVO convert(CustomizeLayerSaveDTO dto);
}
