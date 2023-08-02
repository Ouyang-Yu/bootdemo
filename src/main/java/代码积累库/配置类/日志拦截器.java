package 代码积累库.配置类;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/7/25 14:21
 */
@Slf4j
public class 日志拦截器 implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor traceId = new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String traceId = String.format("%s - %s", request.getRequestURI(), UUID.fastUUID().toString(true));
                MDC.put("traceId", traceId);// 为全局请求添加 TraceId

                // //因为是 ThreadLocal ，异步任务必然是获取不到 traceId 的，需要再线程池配置中手动添加一个TaskDecorator。
                // executor.setTaskDecorator(task -> {
                //     Map<String, String> contextMap = MDC.getCopyOfContextMap();
                //     if (contextMap == null) {
                //         MDC.clear();
                //     } else {
                //         MDC.setContextMap(contextMap);
                //     }
                //
                //     if (MDC.get("traceId") == null) {
                //         MDC.put("traceId", UUID.fastUUID().toString(true));
                //     }
                //
                //     try {
                //         task.run();
                //     } finally {
                //         MDC.clear();
                //     }
                //
                //
                //     return task;
                // });


                log.debug(" interface:{},Parameter:{} ",
                        request.getRequestURI(),
                        request.getParameterMap()
                                .entrySet()
                                .stream()
                                .map(entry -> "\n" + entry.getKey() + ":" + String.join(",", entry.getValue()))
                                .collect(Collectors.joining())
                        //, StreamUtils.copyToString(request.getInputStream(),StandardCharsets.UTF_8)
                );


                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                // ResponseWrapper responseWrapper = new ResponseWrapper(response);
                // responseWrapper.getOutputStream().println();
                // log.debug("response:{}", new String(responseWrapper.getDataStream(), StandardCharsets.UTF_8));
                // 全局统一处理中,才截取到返回的object
                //        //https://www.cnblogs.com/keeya/p/13634015.html
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                // afterCompletion是请求返回前端之后回调的事件
                HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
            }
        };
        registry.addInterceptor(traceId);
    }
}
