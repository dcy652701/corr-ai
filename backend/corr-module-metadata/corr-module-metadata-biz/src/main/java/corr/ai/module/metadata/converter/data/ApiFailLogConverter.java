package corr.ai.module.metadata.converter.data;

import corr.ai.module.metadata.controller.app.get.req.ApiFailLogReq;
import corr.ai.module.metadata.dal.dataobject.data.ApiFailLogDO;
import org.mapstruct.Mapper;

/**
 * @Author: 董丞业
 * @CreateTime: 2024-09-14
 * @Description:
 * @Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface ApiFailLogConverter {
    ApiFailLogDO convert(ApiFailLogReq req);
}
