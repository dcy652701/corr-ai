package corr.ai.module.metadata.service.strategy.config;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import corr.ai.framework.common.pojo.PageParam;
import corr.ai.framework.common.pojo.PageResult;
import corr.ai.framework.mybatis.core.query.LambdaQueryWrapperX;
import corr.ai.framework.security.core.util.SecurityFrameworkUtils;
import corr.ai.module.metadata.controller.app.strategy.param.ListStrategyParam;
import corr.ai.module.metadata.controller.app.strategy.resp.StrategyPageRecordVO;
import corr.ai.module.metadata.controller.app.strategy.resp.StrategyPageResultVO;
import corr.ai.module.metadata.converter.strategy.StrategyConfigConverter;
import corr.ai.module.metadata.dal.dataobject.strategy.StrategyConfigDO;
import corr.ai.module.metadata.dal.dataobject.strategy.StrategyHistoryDO;
import corr.ai.module.metadata.dal.dataobject.strategy.StrategyPageDO;
import corr.ai.module.metadata.dal.dataobject.strategy.TradePairDO;
import corr.ai.module.metadata.dal.mysql.strategy.StrategyConfigMapper;
import corr.ai.module.metadata.dal.mysql.strategy.StrategyResultMapper;
import corr.ai.module.metadata.service.strategy.result.StrategyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dongchengye
 */
@Service
public class StrategyConfigServiceImpl implements StrategyConfigService {

    @Autowired
    private StrategyConfigMapper strategyConfigMapper;

    @Autowired
    private StrategyResultMapper resultMapper;

    @Autowired
    private StrategyRecordService strategyRecordService;

    @Autowired
    private StrategyConfigConverter strategyConfigConverter;

    @Override
    public List<StrategyHistoryDO> listHistory() {
        LambdaQueryWrapperX<StrategyConfigDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(StrategyConfigDO::getCreator,SecurityFrameworkUtils.getLoginUserId());
        wrapperX.orderByDesc(StrategyConfigDO::getCreateTime);
        wrapperX.last("limit 20");
        List<StrategyConfigDO> strategyConfig = strategyConfigMapper.selectList(wrapperX);
        List<StrategyHistoryDO> histories = strategyConfig.stream().map(x -> strategyConfigConverter.convertHis(x)).collect(Collectors.toList());
        return histories;
    }

    @Override
    public PageResult<StrategyPageDO> pageStrategy(ListStrategyParam param) {
        return strategyConfigMapper.pageStrategy(param);
    }

    @Override
    public PageResult<TradePairDO> listTradePair(PageParam param) {
        return strategyConfigMapper.pageTradePair(param);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Long save(StrategyConfigDO strategyConfigDO) {
        strategyConfigMapper.insert(strategyConfigDO);
        return strategyConfigDO.getSid();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long finishStrategySave(Long strategyId, String strategyName, String imageName, int inputMode, JSONObject strategyResultJson, List<JSONObject> priceDataQueryParam, List<JSONObject> nonPriceDataQueryParam) {
        // TODO 这里加密的配置后续实现，目前先只保存原始数据
        UpdateWrapper<StrategyConfigDO> wrapper = new UpdateWrapper<>();
        wrapper.set("strategy_name", strategyName);
        wrapper.set("img", imageName);
        wrapper.set("saved", true);
        wrapper.set("input_mode", inputMode);
//        wrapper.set("price_data_query_param", JSONUtil.toJsonStr(priceDataQueryParam));
//        wrapper.set("non_price_data_query_param", JSONUtil.toJsonStr(nonPriceDataQueryParam));
        wrapper.eq("sid", strategyId);
        strategyConfigMapper.update(wrapper);
        strategyRecordService.saveResult(strategyResultJson, strategyId);
        return strategyId;
    }

    @Override
    public boolean exitsByName(String strategyName) {
        LambdaQueryWrapperX<StrategyConfigDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(StrategyConfigDO::getStrategyName, strategyName);
        wrapperX.eq(StrategyConfigDO::getCreator, SecurityFrameworkUtils.getLoginUserId());
        return strategyConfigMapper.exists(wrapperX);
    }

    @Override
    public StrategyConfigDO queryById(Long id) {
        return strategyConfigMapper.selectById(id);
    }

    @Override
    public boolean exitsByHash(String hash) {
        LambdaQueryWrapperX<StrategyConfigDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(StrategyConfigDO::getStrategyHash, hash);
        return strategyConfigMapper.exists(wrapperX);
    }

    @Override
    public boolean exitsById(Long strategyId) {
        LambdaQueryWrapperX<StrategyConfigDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(StrategyConfigDO::getSid, strategyId);
        StrategyConfigDO strategyConfigDO = strategyConfigMapper.selectOne(wrapperX);
        return true;
    }

    @Override
    public Long getIdByHash(String hash) {
        LambdaQueryWrapperX<StrategyConfigDO> wrapperX = new LambdaQueryWrapperX<>();
        wrapperX.eq(StrategyConfigDO::getStrategyHash, hash);
        StrategyConfigDO strategyConfigDO = strategyConfigMapper.selectOne(wrapperX);
        return strategyConfigDO.getSid();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long insertAnEmptyStrategy(String hash, Long coinId, String rawStrategy, String strategyFactor, String intervals, String priceDataQueryParam) {
        StrategyConfigDO strategyConfigDO = new StrategyConfigDO();
        strategyConfigDO.setStrategyHash(hash);
        strategyConfigDO.setCreator(SecurityFrameworkUtils.getLoginUserId().toString());
        strategyConfigDO.setCoinId(coinId);
        strategyConfigDO.setRawStrategy(rawStrategy);
        strategyConfigDO.setStrategyFactors(strategyFactor);
        strategyConfigDO.setIntervals(intervals);
        strategyConfigDO.setPriceDataQueryParam(priceDataQueryParam);
        strategyConfigMapper.insert(strategyConfigDO);
        return strategyConfigDO.getSid();
    }
}
