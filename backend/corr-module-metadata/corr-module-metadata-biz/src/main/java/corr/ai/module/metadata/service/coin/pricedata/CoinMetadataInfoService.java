package corr.ai.module.metadata.service.coin.pricedata;

import corr.ai.framework.common.pojo.PageResult;
import corr.ai.module.metadata.controller.app.coin.param.CoinInfoParam;
import corr.ai.module.metadata.controller.app.coin.req.CoinInfoReq;
import corr.ai.module.metadata.dal.dataobject.coin.CoinMetadataInfoDO;

import java.util.List;

/**
 * @author dongchengye
 */
public interface CoinMetadataInfoService {
    PageResult<CoinMetadataInfoDO> pageCoins(CoinInfoParam param);

    void insertCoin(CoinInfoReq req);

    List<CoinMetadataInfoDO> listCoinByIds(List<Long> ids);

    CoinMetadataInfoDO queryCoinById(Long id);
}
