package corr.ai.module.metadata.dal.dataobject.optimizerconfig;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;

import java.util.List;

/**
 * @author dongchengye
 */
@Data
public class OptimizerResultDO {
    /**
     * 编号
     */
    private Long oid;
    /**
     * 要优化的策略id
     */
    private Long strategyId;
    /**
     * coinId
     */
    private Long coinId;
    /**
     * 选择的优化器
     */
    private String optimizer;
    /**
     * 优化器配置
     */
    private String optimizerConfig;
    /**
     * 当前的状态
     * 0已完成,1运行中,2运行中断
     */
    private Integer stats;
    /**
     * 最终的百分比
     */
    private Integer finalPercentage;

    private String rawStrategy;

    private String strategyFactors;

    public JSONObject getStrategyConfig(){
        return JSONUtil.parseObj(rawStrategy);
    }
}
