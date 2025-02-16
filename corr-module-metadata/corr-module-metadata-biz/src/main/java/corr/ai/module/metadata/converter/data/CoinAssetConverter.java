package corr.ai.module.metadata.converter.data;

import corr.ai.module.metadata.controller.app.get.resp.ApiResp;
import corr.ai.module.metadata.dal.dataobject.data.CoinAssetDO;
import corr.ai.module.metadata.mq.dto.CoinAssetDTO;
import org.mapstruct.Mapper;

/**
 * @Author: 董丞业
 * @CreateTime: 2024-09-11
 * @Description:
 * @Version: 1.0
 */
@Mapper(componentModel = "spring")
public interface CoinAssetConverter {
    CoinAssetDO convert(ApiResp resp);

    CoinAssetDTO convertDTO(ApiResp resp);
}
