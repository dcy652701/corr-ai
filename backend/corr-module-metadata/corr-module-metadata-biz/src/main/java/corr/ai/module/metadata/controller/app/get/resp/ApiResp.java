package corr.ai.module.metadata.controller.app.get.resp;

import lombok.Data;

/**
 * @Author: 董丞业
 * @CreateTime: 2024-09-11
 * @Description: API返回的数据
 * @Version: 1.0
 */
@Data
public class ApiResp {
    private Long t;
    private Long v;
    private String symbol;
    private String dataType;
    private String dataName;
    private String dataFrequency;
}
