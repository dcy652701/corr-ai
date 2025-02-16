package corr.ai.module.metadata.dto.strategy;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * 配置回测时，先查询好的数据
 * 一条，基本都是以list形式存在
 *
 * @author dongchengye
 */
@Data
public class StrategyBaseFactorAndIndicatorDTO {
    private Long coinId;
    private Long factorId;
    /**
     * 根据type来判断，目前已知number_input，logic_symbol，contect_logic，foctor，ind，non_price_factor,non_price_ind
     */
    private String type;
    private String symbol;
    private String interval;
    private String foctor;
    private String foctorInd;
    private List<String> foctorSourse;
    private String id;
    private String dataId;
    private JSONObject indPayload;
    private String subPayload;
    private String subPayloadValue;
    private String uniqueId;
    //逻辑符号部分
    /**
     * 数学符号 > < =等
     */
    private String mathSymbols;
    /**
     * 逻辑运算符 and or等
     */
    private String contectLogic;
    /**
     * 配置的常量条件
     */
    private Float value;
    private Long indId;
    private String from;
    private Integer useScene;
    //顺序
    protected Integer order;
}
