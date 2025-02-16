package corr.ai.module.metadata.service.strategy.config;

import cn.hutool.json.JSONObject;
import corr.ai.framework.common.pojo.PageParam;
import corr.ai.framework.common.pojo.PageResult;
import corr.ai.module.metadata.controller.app.strategy.param.ListStrategyParam;
import corr.ai.module.metadata.controller.app.strategy.resp.StrategyPageRecordVO;
import corr.ai.module.metadata.controller.app.strategy.resp.StrategyPageResultVO;
import corr.ai.module.metadata.dal.dataobject.strategy.StrategyConfigDO;
import corr.ai.module.metadata.dal.dataobject.strategy.StrategyHistoryDO;
import corr.ai.module.metadata.dal.dataobject.strategy.StrategyPageDO;
import corr.ai.module.metadata.dal.dataobject.strategy.TradePairDO;

import java.util.List;
import java.util.Map;

/**
 * 策略配置service
 *
 * @author dongchengye
 */
public interface StrategyConfigService {

    /**
     * 列出历史记录，只限最近20条
     * @return
     */
    List<StrategyHistoryDO> listHistory();

    /**
     * 分页查询策略
     *
     * @param param
     * @return
     */
    PageResult<StrategyPageDO> pageStrategy(ListStrategyParam param);

    /**
     * 分页所有交易对
     *
     * @param param
     * @return
     */
    PageResult<TradePairDO> listTradePair(PageParam param);

    /**
     * 保存配置
     *
     * @param strategyConfigDO
     */
    @Deprecated
    Long save(StrategyConfigDO strategyConfigDO);

    /**
     * 完成策略存储
     *
     * @param rawStrategy
     * @param rawStrategyCrypto
     * @return
     */
    Long finishStrategySave(Long strategyId, String strategyName, String imageName, int inputMode, JSONObject strategyResultJson, List<JSONObject> priceDataQueryParam, List<JSONObject> nonPriceDataQueryParam);

    /**
     * 根据策略名判断是否存在
     *
     * @param strategyName
     * @return
     */
    boolean exitsByName(String strategyName);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    StrategyConfigDO queryById(Long id);

    /**
     * 通过策略配置编码的hash来判断是否存在
     *
     * @param hash
     * @return
     */
    boolean exitsByHash(String hash);

    /**
     * 根据id来判断策略是否存在
     *
     * @param strategyId
     * @return
     */
    boolean exitsById(Long strategyId);

    /**
     * 通过策略hash获取id
     */
    Long getIdByHash(String hash);

    /**
     * 插入一个空的策略，先生成id，把策略配置的hash也保存上
     *
     * @return
     */
    Long insertAnEmptyStrategy(String hash, Long coinId, String rawStrategy, String strategyFactor, String intervals, String priceDataQueryParam);
}
