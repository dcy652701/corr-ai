package corr.ai.framework.env.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 环境配置
 *
 * @author CorrAi
 */
@ConfigurationProperties(prefix = "corr.env")
@Data
public class EnvProperties {

    public static final String TAG_KEY = "corr.env.tag";

    /**
     * 环境标签
     */
    private String tag;

}
