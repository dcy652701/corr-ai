package corr.ai.module.metadata.controller.app.get.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 董丞业
 * @CreateTime: 2024-09-14
 * @Description: 失败日志req
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
public class ApiFailLogReq {
    private Long logId;
    private Long apiId;
    private String response;
    private String exceptionMsg;
    private String symbol;
    private String url;

    public ApiFailLogReq(Long apiId, String response, String exceptionMsg, String symbol, String url) {
        this.apiId = apiId;
        this.response = response;
        this.exceptionMsg = exceptionMsg;
        this.symbol = symbol;
        this.url = url;
    }
}
