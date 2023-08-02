package 代码积累库.springmvc;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class 拦截器与过滤器配置 {


    @Bean//config a WebMvcConfigurer into spring
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(@NotNull InterceptorRegistry registry) {
                var interceptARequest = new HandlerInterceptor() {
                    @Override
                    public boolean preHandle(@NotNull HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                        System.out.println("intercept a request");
                        return true;// let it go
                    }
                };
                registry.addInterceptor(interceptARequest).addPathPatterns("/**");
            }
        };
    }
    // filter是Servlet规范中的一部分，主要在DispatchServlet之前过滤
    // 重写doFilter可以对请求链接进行处理、转发、重定向等操作。

    // interceptor是Spring MVC框架中的组件，主要在controller之前拦截
    // 重写preHandle postHandle 例如记录日志、验证用户权限等。
    //

    @Bean
    public Filter filter() {
        return new Filter() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                System.out.println("filter a request");
                HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

                // Get the Authorization header from the request
                String authHeader = httpRequest.getHeader("Authorization");

                // Validate the Authorization header
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    String token = authHeader.substring(7);
                    // Validate the token
                    if (validateToken(token)) {
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                }

                // Unauthorized access
                ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        };



    }

    private boolean validateToken(String token) {
        return true;
    }
}
