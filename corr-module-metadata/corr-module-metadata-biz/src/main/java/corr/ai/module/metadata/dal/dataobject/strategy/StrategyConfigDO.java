package corr.ai.module.metadata.dal.dataobject.strategy;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;
import corr.ai.module.metadata.consts.UrlConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 策略配置类DO
 *
 * @author dongchengye
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "corr_strategy_config")
public class StrategyConfigDO extends BaseDO {
    @TableId(value = "sid", type = IdType.ASSIGN_ID)
    private Long sid;
    /**
     * 策略名称
     */
    private String strategyName;
    /**
     * 币id
     */
    private Long coinId;
    /**
     * 策略hash，判断是否存在
     */
    private String strategyHash;
    /**
     * 加密后的策略配置
     */
    private String rawStrategyCrypto;
    /**
     * 未加密的策略配置
     */
    private String rawStrategy;
    /**
     * 策略的缩略图
     */
    private String img;
    /**
     * 已保存/未保存
     */
    private Boolean saved;

    private Integer inputMode;

    private String strategyFactors;

    private String intervals;

    private String priceDataQueryParam;

    private String nonPriceDataQueryParam;

    public JSONObject recoverConfig() {
        return JSONUtil.parseObj(rawStrategy);
    }

    public String imgDownloadLink() {
        return UrlConstants.TENCENT_OSS_DOWNLOAD_URL + img;
    }

    public List<JSONObject> getPriceDataQueryParamList(){
        return JSONUtil.parseArray(priceDataQueryParam).toList(JSONObject.class);
    }

    public List<JSONObject> getNonPriceDataQueryParamList(){
        return JSONUtil.parseArray(nonPriceDataQueryParam).toList(JSONObject.class);
    }
}
