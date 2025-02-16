package corr.ai.module.metadata.converter.coin;

import corr.ai.module.metadata.controller.app.coin.resp.AvailableFoctorVO;
import corr.ai.module.metadata.controller.app.coin.vo.AppAvailableNonPriceFactorsRespVO;
import corr.ai.module.metadata.dal.dataobject.coin.nonpricedata.AvailableNonPriceFactorsDO;
import corr.ai.module.metadata.dal.dataobject.coin.nonpricedata.NonPriceFactorsDO;
import corr.ai.module.metadata.dal.dataobject.coin.pricedata.AvailableFoctorDO;
import corr.ai.module.metadata.dto.coin.AvailableFoctorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author dongchengye
 */
@Mapper(componentModel = "spring")
public interface FoctorConverter {
    @Mappings({
            @Mapping(target = "hasIndicatorList", expression = "java(availableFoctorDO.getIndicators())"),
            @Mapping(target = "indFlag", expression = "java(availableFoctorDO.getIndFlag())"),
            @Mapping(target = "source", expression = "java(\"priceData\")")
    })
    AvailableFoctorVO convert(AvailableFoctorDO availableFoctorDO);

//    AvailableFoctorDTO convertDTO(AvailableFoctorDO availableFoctorDO);

    @Mappings({
            @Mapping(target = "hasIndicatorList", expression = "java(nonPriceFactorsDO.getIndicators())")
    })
    AvailableFoctorDTO convertDTO(NonPriceFactorsDO nonPriceFactorsDO);

    @Mappings({
            @Mapping(target = "hasIndicatorsList", expression = "java(nonPriceFactorsDO.getIndicators())"),
            @Mapping(target = "indFlag", expression = "java(nonPriceFactorsDO.getIndFlag())"),
            @Mapping(target = "foctorId", source = "factorId"),
            @Mapping(target = "foctorType", source = "factorType"),
            @Mapping(target = "foctorName", source = "factorName"),
            @Mapping(target = "foctorKeyName", source = "factorKeyName"),
            @Mapping(target = "source", expression = "java(\"noPriceData\")")
    })
    AppAvailableNonPriceFactorsRespVO convert(AvailableNonPriceFactorsDO nonPriceFactorsDO);
}
