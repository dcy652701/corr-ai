package corr.ai.module.infra.framework.file.config;

import corr.ai.module.infra.framework.file.core.client.FileClientFactory;
import corr.ai.module.infra.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author CorrAi
 */
@Configuration(proxyBeanMethods = false)
public class CorrFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
