package corr.ai.module.metadata.controller.app.get.req;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 用来保存可以请求的接口都有哪些的
 *
 * @author dongchengye
 */
@Data
@Schema(description = "保存接口的req")
public class ApiSaveReq {
    private Long apiId;
    private String apiType;
    private String apiName;
    private String symbol;
    private String url;
    private String provider;
    private List<String> requestFrequency;

    public ApiSaveReq(String apiName, String apiType, String symbol, String url, String provider, List<String> requestFrequency) {
        this.apiName = apiName;
        this.symbol = symbol;
        this.apiType = apiType;
        this.url = url;
        this.provider = provider;
        this.requestFrequency = requestFrequency;
    }

    public String getRequestFrequencyStr() {
        return StrUtil.join(",", requestFrequency);
    }
}
