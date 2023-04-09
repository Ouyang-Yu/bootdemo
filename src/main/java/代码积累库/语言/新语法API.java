package 代码积累库.语言;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class 新语法API {
    void onMessage(int a) {
        switch (a) {
            case 0 -> System.out.println("zero");
            case 1 -> System.out.println("one");
            case 2, 3, 4 -> System.out.println("few");
            default -> throw new IllegalArgumentException();
        }
    }

    @Test
    public void strEncode() throws UnsupportedEncodingException {
//        0xEF 0xBF 0xBD
        byte[] gbks = "大厦".getBytes("GBK");
        String unicodeStr = new String(gbks, UTF_8);
        System.out.println(unicodeStr);//����
        //用GBK编码的字符串,用unicode解码时候会无法识别,显示为��
        String gbk = new String(unicodeStr.getBytes(UTF_8), "GBK");
        System.out.println(gbk);//锟斤拷锟斤拷
        //��用unicode编码再用GBK解码会显示为锟斤拷


    }


    @Component//config a WebMvcConfigurer into spring
    class SpringMVCConfig implements WebMvcConfigurer {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new HandlerInterceptor() {
                @Override
                public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                    System.out.println("intercept a request");
                    return true;
                }
            }).addPathPatterns("/**");

        }
    }


    @Bean//config a WebMvcConfigurer into spring
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new HandlerInterceptor() {
                    @Override
                    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                        System.out.println("intercept a request");
                        return true;// let it go
                    }

                }).addPathPatterns("/**");
            }
        };
    }
    //interceptor是Spring MVC框架中的组件，主要在controller之前intercept
    // 重写preHandle postHandle 例如记录日志、验证用户权限等。
    //
    // filter是Servlet规范中的一部分，主要在servlet之前之后拦截
    // 重写doFilter可以对请求进行处理、转发、重定向等操作。

    @Bean
    public Filter filter() {
        return new Filter() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

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
                return;
            }
        };



    }

    private boolean validateToken(String token) {
        return true;
    }
}
