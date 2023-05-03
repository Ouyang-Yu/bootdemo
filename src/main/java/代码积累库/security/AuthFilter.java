package 代码积累库.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import 代码积累库.JWTUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AuthFilter implements GlobalFilter {

    public static final String USER_KEY = "user_key";
    public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    @Resource
    private RedisTemplate redisTemplate;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ArrayList<String> whiteList = new ArrayList<>() {{
            add("/*");
        }};
        ServerHttpRequest request = exchange.getRequest();
        List<String> collect = whiteList.stream().filter(patten -> new AntPathMatcher().match(patten, request.getURI().getPath())).toList();
        if (collect.size() > 0) {// 匹配到白名单,放行
            return chain.filter(exchange);
        }

        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String userKey = JWTUtils.parseToken(token, USER_KEY);
        if (Boolean.TRUE.equals(redisTemplate.hasKey("user_key" + userKey))) {
            return Mono.just(HttpStatus.UNAUTHORIZED).then();
        }
        String userId = JWTUtils.parseToken(token, USER_ID);
        String userName = JWTUtils.parseToken(token, USERNAME);

        if (StringUtils.hasText(token)
                && Boolean.TRUE.equals(redisTemplate.hasKey("user_key" + userKey))
                && StringUtils.hasText(userId)
                && StringUtils.hasText(userName)
        ) {
            request.mutate().header(USER_KEY, userKey);
            request.mutate().header(USER_ID, userId);
            request.mutate().header(USERNAME, userName);

            return chain.filter(exchange.mutate()
                    .request(request.mutate().build())
                    .build()
            );
        } else {
            ServerHttpResponse response = exchange.getResponse();{
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            }
            log.error("[鉴权异常处理]请求路径:{}", exchange.getRequest().getPath());

            return response.writeWith(Mono.just(
                    response.bufferFactory().wrap(
                            new ObjectMapper().writeValueAsBytes(
                                    ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            )
                    )
            ));
        }

    }

    @Test
    public void sd() {
    }
}
