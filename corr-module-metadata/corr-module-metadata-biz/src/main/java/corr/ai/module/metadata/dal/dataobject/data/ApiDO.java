package corr.ai.module.metadata.dal.dataobject.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dongchengye
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("corr_api_info")
public class ApiDO extends BaseDO {
    @TableId(value = "api_id", type = IdType.ASSIGN_ID)
    private Long apiId;
    private String apiType;
    private String apiName;
    private String symbol;
    private String url;
    private String provider;
    private String requestFrequency;

    /**
     * 获取可用的数据粒度参数
     *
     * @return
     */
    public List<String> getValidDataInterval() {
        if (requestFrequency.contains(",")) {
            return Arrays.asList(requestFrequency.split(","));
        } else {
            ArrayList<String> interval = new ArrayList<>();
            interval.add(requestFrequency);
            return interval;
        }
    }

    /**
     * 获取最小数据粒度
     *
     * @return
     */
    public String getMinInterval() {
        if (requestFrequency.contains(",")) {
            //有多个值就取第一个
            return Arrays.asList(requestFrequency.split(",")).get(0);
        } else {
            return requestFrequency;
        }
    }
}
