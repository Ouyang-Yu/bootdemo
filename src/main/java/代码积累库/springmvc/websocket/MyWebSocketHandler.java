package 代码积累库.springmvc.websocket;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/7/11 10:18
 */
public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) {
        System.out.println("websocket connection established");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("received message: " + payload);

        session.sendMessage(new TextMessage("this is response"));
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        System.out.println("websocket connection closed");
    }
}
