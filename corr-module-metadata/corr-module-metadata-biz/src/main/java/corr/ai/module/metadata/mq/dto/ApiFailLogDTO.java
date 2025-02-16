package corr.ai.module.metadata.mq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 失败接口日志
 * deleted默认false不变
 * tenantId默认1不变
 *
 * @author dongchengye
 */
@Data
@AllArgsConstructor
public class ApiFailLogDTO implements Serializable {
    private Long apiId;
    private String response;
    private String exceptionMsg;
    private String symbol;
    private String url;
    private Boolean deleted;
    private Long tenantId;
}
