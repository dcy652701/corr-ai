package corr.ai.module.infra.binance;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.TypeUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import corr.ai.framework.common.exception.ErrorCode;
import corr.ai.framework.common.exception.ServiceException;
import corr.ai.framework.common.util.json.JsonUtils;
import corr.ai.framework.tenant.core.util.TenantUtils;
import corr.ai.framework.websocket.core.listener.WebSocketMessageListener;
import corr.ai.framework.websocket.core.message.JsonWebSocketMessage;
import corr.ai.framework.websocket.core.util.WebSocketFrameworkUtils;
import corr.ai.module.infra.enums.CoinTopicInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * 这是用spring内置的WebsocketConnectionManager实现
 *
 * @author dongchengye
 */
@Slf4j
@Component
public class BinanceWebsocketClient extends TextWebSocketHandler {

    @Value("${corr.binance}")
    private String binanceUrl;

    @Autowired
    private WebSocketClient webSocketClient;

    private WebSocketSession session;

    @Autowired
    private KafkaTemplate<String, JSONObject> kafkaTemplate;

    @PostConstruct
    public void afterConstructor() {
        connect(binanceUrl);
    }

    public void connect(String url) {
        WebSocketConnectionManager manager = new WebSocketConnectionManager(webSocketClient, this, url);
        manager.setAutoStartup(true);
        manager.start();
    }

    public void disconnect() {
        try {
            if (session != null && session.isOpen()) {
                session.close();
            }
        } catch (Exception e) {
            throw new ServiceException(new ErrorCode(10101, "close websocket session error"));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("closed by binance");
        connect(binanceUrl);
        log.info("重新连接");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 1.1 空消息，跳过
        if (message.getPayloadLength() == 0) {
            return;
        }
        // 1.2 ping 心跳消息，直接返回 pong 消息。
        if (message.getPayloadLength() == 4 && Objects.equals(message.getPayload(), "ping")) {
            session.sendMessage(new TextMessage("pong"));
            return;
        }
        try {
            JSONObject messageJson = JsonUtils.parseObject(message.getPayload(), JSONObject.class);
            assert messageJson != null;
            String coinSymbol = messageJson.getJSONObject("data").getStr("s");
            JSONObject klineJson = messageJson.getJSONObject("data").getJSONObject("k");
            JSONObject dwKline = JSONUtil.createObj();
            dwKline.set("open_time", klineJson.getLong("t"));
            dwKline.set("close_time", klineJson.getLong("T"));
            dwKline.set("open", klineJson.getFloat("o"));
            dwKline.set("high", klineJson.getFloat("h"));
            dwKline.set("low", klineJson.getFloat("l"));
            dwKline.set("close", klineJson.getFloat("c"));
            dwKline.set("volume", klineJson.getInt("v"));
            dwKline.set("base_asset_volume", 0);
            dwKline.set("number_of_trades", 0);
            dwKline.set("taker_buy_volume", 0);
            dwKline.set("taker_buy_base_asset_volume", 0);
            dwKline.set("symbol", coinSymbol);
            dwKline.set("symbol_id", 22032119420L);
            dwKline.set("intervals", klineJson.getStr("i"));
            dwKline.set("tenant_id", 1);
            dwKline.set("creator", 1);
            dwKline.set("updater", 1);
            dwKline.set("create_time", 1725149759999L);
            dwKline.set("update_time", 1725149759999L);
            dwKline.set("deleted", false);
            String topic = CoinTopicInfo.getTopicByCoin(coinSymbol);
            kafkaTemplate.send(topic, dwKline);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[handleTextMessage][session({}) message({}) 处理异常]", session.getId(), message.getPayload());
        }
    }
}
