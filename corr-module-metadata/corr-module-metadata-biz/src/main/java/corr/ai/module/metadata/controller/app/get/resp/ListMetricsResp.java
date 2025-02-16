package corr.ai.module.metadata.controller.app.get.resp;

import corr.ai.module.metadata.controller.app.get.resp.sub.Asset;
import lombok.Data;

import java.util.List;

/**
 * list metrics接口的响应
 *
 * @author dongchengye
 */
@Data
public class ListMetricsResp {
    /**
     * 资产信息
     */
    private List<Asset> assets;

    /**
     * 货币信息
     */
    private List<String> currencies;

    /**
     * 格式信息
     */
    private List<String> formats;

    /**
     * 没用
     */
    private String paramsDomain;

    /**
     * 接口url
     */
    private String path;

    /**
     * 数据的时间间隔
     */
    private List<String> resolutions;

    /**
     * 不知道是啥
     */
    private Integer tier;
}
