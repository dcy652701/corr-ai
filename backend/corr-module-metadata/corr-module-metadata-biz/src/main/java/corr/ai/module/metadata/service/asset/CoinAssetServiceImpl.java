package corr.ai.module.metadata.service.asset;

import corr.ai.module.metadata.controller.app.assetname.resp.AssetNameVO;
import corr.ai.module.metadata.converter.data.AssetNameConverter;
import corr.ai.module.metadata.converter.data.CoinAssetConverter;
import corr.ai.module.metadata.dal.dataobject.data.AssetNameDO;
import corr.ai.module.metadata.dal.dataobject.data.CoinAssetDO;
import corr.ai.module.metadata.dal.mysql.data.CoinAssetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * @Author: 董丞业
 * @CreateTime: 2024-09-11
 * @Description:
 * @Version: 1.0
 */
@Service
@Slf4j
public class CoinAssetServiceImpl implements CoinAssetService{
    @Autowired
    private CoinAssetMapper coinAssetMapper;
    @Autowired
    private CoinAssetConverter coinAssetConverter;

    @Autowired
    private AssetNameConverter assetNameConverter;

//    @Transactional(rollbackFor = Throwable.class)
    @Override
    @Async("asyncThreadPoolExecutor")
    public void insertDatas(List<CoinAssetDO> collect, CountDownLatch countDownLatch) {
//        log.info("插入数据");
//        List<CoinAssetDO> collect = apiRespList.stream().map(x -> coinAssetConverter.convert(x)).collect(Collectors.toList());
        try {
            coinAssetMapper.insertBatch(collect);
        }finally {
            countDownLatch.countDown();
            log.info("插入完成，剩余任务数："+countDownLatch.getCount());
        }
    }

    @Override
    public List<AssetNameVO> listCols() {
        List<CoinAssetDO> coinAssets = coinAssetMapper.listCols();
        List<AssetNameDO> assetNameList = coinAssets.stream()
                .collect(Collectors.groupingBy(CoinAssetDO::getDataType))
                .entrySet()
                .stream()
                .map(entry -> {
                    AssetNameDO assetNameDO = new AssetNameDO();
                    assetNameDO.setDataType(entry.getKey());
                    assetNameDO.setDataName(entry.getValue().stream()
                            .map(CoinAssetDO::getDataName)
                            .collect(Collectors.toList()));
                    return assetNameDO;
                })
                .collect(Collectors.toList());
        return assetNameList.stream().map(x-> {
            AssetNameVO assetNameVO = assetNameConverter.convert(x);
            List<String> distinctDataNameList = assetNameVO.getDataName().stream().distinct().collect(Collectors.toList());
            assetNameVO.setDataName(distinctDataNameList);
            return assetNameVO;
        }).collect(Collectors.toList());
    }
}
