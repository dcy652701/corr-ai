package corr.ai.framework.websocket.core.sender.local;

import corr.ai.framework.websocket.core.sender.AbstractWebSocketMessageSender;
import corr.ai.framework.websocket.core.sender.WebSocketMessageSender;
import corr.ai.framework.websocket.core.session.WebSocketSessionManager;

/**
 * 本地的 {@link WebSocketMessageSender} 实现类
 *
 * 注意：仅仅适合单机场景！！！
 *
 * @author CorrAi
 */
public class LocalWebSocketMessageSender extends AbstractWebSocketMessageSender {

    public LocalWebSocketMessageSender(WebSocketSessionManager sessionManager) {
        super(sessionManager);
    }

}
