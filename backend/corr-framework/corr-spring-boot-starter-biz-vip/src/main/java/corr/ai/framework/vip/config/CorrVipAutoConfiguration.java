package corr.ai.framework.vip.config;

import corr.ai.framework.vip.core.service.VipFrameworkService;
import corr.ai.framework.vip.core.service.VipFrameworkServiceImpl;
import corr.ai.module.member.api.vip.VipApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author dongchengye
 */
@AutoConfiguration
@ConditionalOnProperty(prefix = "corr.vip",value = "enable",matchIfMissing = true)
@EnableConfigurationProperties(VipProperties.class)
public class CorrVipAutoConfiguration {

    @Bean
    public VipFrameworkService vipFrameworkService(VipApi vipApi){
        return new VipFrameworkServiceImpl(vipApi);
    }

}
