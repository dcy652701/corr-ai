package corr.ai.module.metadata.dal.dataobject.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import corr.ai.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口请求失败日志
 *
 * @author dongchengye
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("corr_api_fail_log")
public class ApiFailLogDO extends BaseDO {
    @TableId(value = "log_id", type = IdType.ASSIGN_ID)
    private Long logId;
    private Long apiId;
    private String response;
    private String exceptionMsg;
    private String symbol;
    private String url;
}
