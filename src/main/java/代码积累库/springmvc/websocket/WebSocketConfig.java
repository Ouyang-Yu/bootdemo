package 代码积累库.springmvc.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/7/11 10:23
 */
@Component
@EnableWebSocket//!!
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyWebSocketHandler(), "/websocket")
                .setAllowedOrigins("*");
    }
}
