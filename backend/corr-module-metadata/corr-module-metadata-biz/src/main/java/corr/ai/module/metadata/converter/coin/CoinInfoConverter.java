package corr.ai.module.metadata.converter.coin;

import corr.ai.module.metadata.controller.app.coin.req.CoinInfoReq;
import corr.ai.module.metadata.controller.app.coin.resp.CoinInfoRespVO;
import corr.ai.module.metadata.dal.dataobject.coin.CoinMetadataInfoDO;
import corr.ai.module.metadata.dto.coin.CoinMetadataDTO;
import org.mapstruct.Mapper;

/**
 * @author dongchengye
 */
@Mapper(componentModel = "spring")
public interface CoinInfoConverter {
    CoinMetadataInfoDO convert(CoinInfoReq req);
    CoinInfoRespVO convert(CoinMetadataInfoDO coinMetadataInfoDO);
    CoinMetadataDTO convertDTO(CoinMetadataInfoDO coinMetadataInfoDO);
}
