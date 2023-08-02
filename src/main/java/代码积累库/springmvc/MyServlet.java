package 代码积累库.springmvc;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/6/7 15:18 ]
 */

@WebServlet(urlPatterns = "/servlet")
//@ServletComponentScan(basePackageClasses = "com.**.mvc")//boot class need annotate
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("<html><head>");
        writer.flush();
        writer.close();
    }
}
