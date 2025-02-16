package corr.ai.module.metadata.converter.data;

import corr.ai.module.metadata.controller.app.assetname.resp.AssetNameVO;
import corr.ai.module.metadata.dal.dataobject.data.AssetNameDO;
import org.mapstruct.Mapper;

/**
 * @Author: 董丞业
 * @CreateTime: 2024-09-12
 * @Description:
 * @Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface AssetNameConverter {
    AssetNameVO convert(AssetNameDO source);
}
