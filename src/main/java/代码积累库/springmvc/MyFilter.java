package 代码积累库.springmvc;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/6/8 15:03
 */
@WebFilter(urlPatterns = "/*")
//@ServletComponentScan(basePackageClasses = "com.**.mvc")//boot class need annotate

@Slf4j
public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();

        log.info("filter{}", requestURI);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
