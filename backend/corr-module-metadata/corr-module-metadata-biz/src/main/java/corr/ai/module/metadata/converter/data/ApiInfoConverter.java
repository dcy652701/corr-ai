package corr.ai.module.metadata.converter.data;

import corr.ai.module.metadata.controller.app.get.req.ApiSaveReq;
import corr.ai.module.metadata.dal.dataobject.data.ApiDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author dongchengye
 */
@Mapper(componentModel = "spring")
public interface ApiInfoConverter {
    @Mapping(target = "requestFrequency",expression = "java(req.getRequestFrequencyStr())")
    ApiDO convert(ApiSaveReq req);
}
