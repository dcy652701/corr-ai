package corr.ai.framework.vip.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dongchengye
 */
@ConfigurationProperties(prefix = "corr.vip")
@Data
public class VipProperties {
    /**
     * 默认vip校验开启
     */
    private static final Boolean ENABLE_DEFAULT = true;

    /**
     * 是否开启
     */
    private Boolean enable = ENABLE_DEFAULT;
}
