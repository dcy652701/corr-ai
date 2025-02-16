package corr.ai.module.metadata.converter.coin;

import corr.ai.module.metadata.controller.app.coin.resp.AvailableIndicatorVO;
import corr.ai.module.metadata.dal.dataobject.coin.pricedata.AvailableIndicatorsDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author dongchengye
 */
@Mapper(componentModel = "spring")
public interface AvailableIndicatorConverter {

    @Mappings({
            @Mapping(target = "indicatorPayloadList", expression = "java(availableIndicatorsDO.getIndicatorPayloadObject())"),
            @Mapping(target = "vreturn", expression = "java(availableIndicatorsDO.getReturnList())"),
            @Mapping(target = "indFlag", expression = "java(false)")
    })
    AvailableIndicatorVO convert(AvailableIndicatorsDO availableIndicatorsDO);

}
