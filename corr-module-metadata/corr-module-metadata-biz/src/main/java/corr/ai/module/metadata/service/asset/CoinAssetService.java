package corr.ai.module.metadata.service.asset;

import corr.ai.module.metadata.controller.app.assetname.resp.AssetNameVO;
import corr.ai.module.metadata.dal.dataobject.data.CoinAssetDO;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: 董丞业
 * @CreateTime: 2024-09-11
 * @Description: 币数据抓取服务
 * @Version: 1.0
 */
public interface CoinAssetService {
    void insertDatas(List<CoinAssetDO> collect, CountDownLatch countDownLatch);

    List<AssetNameVO> listCols();
}
