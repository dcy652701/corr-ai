package corr.ai.module.metadata.dal.dataobject.coin.pricedata;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dongchengye
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("corr_available_indicators")
public class AvailableIndicatorsDO extends BaseDO {
    @TableId(value = "ind_id", type = IdType.ASSIGN_ID)
    private Long indId;
    private String indicatorClass;
    private String indicatorName;
    private String indicatorPayload;
    private String indicatorInputCol;
    private String chartArea;
    private String chartType;
    private String vReturn;
    private String indicatorType;
    private Integer useScene;

    /**
     * 解析一下IndicatorPayload
     *
     * @return
     */
    public JSONObject getIndicatorPayloadObject() {
        return JSONUtil.parseObj(indicatorPayload);
    }

    public List<String> getReturnList(){
        List<String> vReturnList=new ArrayList<>();
        if (vReturn.contains(",")){
            List<String> collect = Arrays.stream(vReturn.split(",")).collect(Collectors.toList());
            vReturnList.addAll(collect);
        }else {
            vReturnList.add(vReturn);
        }
        return vReturnList;
    }
}
