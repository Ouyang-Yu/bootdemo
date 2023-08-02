package 代码积累库.springmvc;


import com.alibaba.fastjson.JSON;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();//支持通配符

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        ArrayList<String> noNeedToLogin = new ArrayList<String>() {{
            add("/login");
        }};

        for (String s : noNeedToLogin) {
            if (PATH_MATCHER.match(s, request.getRequestURI())) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        if (request.getSession().getAttribute("loginUser") != null) {
            filterChain.doFilter(request, response);
        }//already login

        response.getWriter().write(JSON.toJSONString(ResponseEntity.ok("you hava to login")));

        response.sendRedirect(request.getContextPath()+"login");
        /*
        now
         */
        //@Configuration
        //public class LoginConfig implements WebMvcConfigurer {
        //
        //    @Override
        //    public void addInterceptors(InterceptorRegistry registry) {
        //        //注册TestInterceptor拦截器
        //        InterceptorRegistration registration = registry.addInterceptor(new UserLoginInterceptor());
        //        registration.addPathPatterns("/**"); //所有路径都被拦截
        //        registration.excludePathPatterns(    //添加不拦截路径
        //                "/login",                    //登录路径
        //                "/**/*.html",                //html静态资源
        //                "/**/*.js",                  //js静态资源
        //                "/**/*.css"                  //css静态资源
        //        );
        //    }

//        implements HandlerInterceptor
    }
}
