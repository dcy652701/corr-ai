package corr.ai.module.metadata.dal.dataobject.customizelayer;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户自定义指标层 DO
 *
 * @author CorrAi
 */
@TableName("corr_metadata_customize_layer")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomizeLayerDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long layerId;
    /**
     * 最上层的factor
     */
    private Long parentFactorId;
    /**
     * coin id
     */
    private Long coinId;
    /**
     * interval
     */
    private String intervals;
    /**
     * 自定义指标层的完整配置
     */
    private String layerConfig;

    public JSONObject getLayerConfigObject(){
        return JSONUtil.parseObj(layerConfig);
    }

}
