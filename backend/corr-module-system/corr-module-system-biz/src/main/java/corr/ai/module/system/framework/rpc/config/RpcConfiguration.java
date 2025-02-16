package corr.ai.module.system.framework.rpc.config;

import corr.ai.module.infra.api.config.ConfigApi;
import corr.ai.module.infra.api.file.FileApi;
import corr.ai.module.infra.api.websocket.WebSocketSenderApi;
import corr.ai.module.member.api.user.MemberUserApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {FileApi.class, WebSocketSenderApi.class, ConfigApi.class, MemberUserApi.class})
public class RpcConfiguration {
}
