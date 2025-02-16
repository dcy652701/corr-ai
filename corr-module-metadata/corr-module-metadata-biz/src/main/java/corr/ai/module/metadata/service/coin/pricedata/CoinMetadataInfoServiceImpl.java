package corr.ai.module.metadata.service.coin.pricedata;

import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.module.metadata.controller.app.coin.param.CoinInfoParam;
import corr.ai.module.metadata.controller.app.coin.req.CoinInfoReq;
import corr.ai.module.metadata.converter.coin.CoinInfoConverter;
import corr.ai.module.metadata.dal.dataobject.coin.CoinMetadataInfoDO;
import corr.ai.module.metadata.dal.mysql.coin.CoinMetadataInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dongchengye
 */
@Service
public class CoinMetadataInfoServiceImpl implements CoinMetadataInfoService {

    @Autowired
    private CoinMetadataInfoMapper coinMetadataInfoMapper;

    @Autowired
    private CoinInfoConverter coinInfoConverter;

    @Override
    public PageResult<CoinMetadataInfoDO> pageCoins(CoinInfoParam param) {
        return coinMetadataInfoMapper.page(param);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void insertCoin(CoinInfoReq req) {
        CoinMetadataInfoDO coinMetadataInfoDO = coinInfoConverter.convert(req);
        coinMetadataInfoMapper.insert(coinMetadataInfoDO);
    }

    @Override
    public List<CoinMetadataInfoDO> listCoinByIds(List<Long> ids) {
        return coinMetadataInfoMapper.selectCoinsByIdList(ids);
    }

    @Override
    public CoinMetadataInfoDO queryCoinById(Long id) {
        LambdaQueryWrapperX<CoinMetadataInfoDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(CoinMetadataInfoDO::getMetaId, id);
        return coinMetadataInfoMapper.selectOne(wrapperX);
    }
}
