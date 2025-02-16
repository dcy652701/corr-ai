package corr.ai.module.member.framework.rpc.config;

import corr.ai.module.coin.api.BlockchainApi;
import corr.ai.module.system.api.logger.LoginLogApi;
import corr.ai.module.system.api.sms.SmsCodeApi;
import corr.ai.module.system.api.social.SocialClientApi;
import corr.ai.module.system.api.social.SocialUserApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {SmsCodeApi.class, LoginLogApi.class, SocialUserApi.class, SocialClientApi.class, BlockchainApi.class})
public class RpcConfiguration {
}
