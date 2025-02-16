package corr.ai.module.infra.binance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

/**
 * @author dongchengye
 */
@Configuration
public class WebsocketClientConfig {
    @Bean
    public WebSocketClient webSocketClient(){
        return new StandardWebSocketClient();
    }
}
