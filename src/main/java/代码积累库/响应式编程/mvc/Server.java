// package 代码积累库.响应式编程.mvc;
//
// import org.apache.http.impl.bootstrap.HttpServer;
// import org.springframework.http.MediaType;
// import org.springframework.http.server.reactive.HttpHandler;
// import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
// import org.springframework.web.reactive.function.server.RouterFunction;
// import org.springframework.web.reactive.function.server.ServerResponse;
// import org.springframework.web.servlet.function.RequestPredicates;
// import org.springframework.web.servlet.function.RouterFunctions;
//
// import static org.springframework.web.servlet.function.RequestPredicates.GET;
//
// public class Server {
//     //1 创建 Router 路由
//     public RouterFunction<ServerResponse> routingFunction(){
//         //创建 handler 对象
//         UserService userService = new UserServiceImpl();
//         UserHandler handler = new UserHandler(userService);
//         //设置路由
//         return RouterFunctions.route(
//                 GET("/users/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
//                 handler::getUserById
//                 // GET("/users/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
//                 // handler::getUserById
//                 )
//                 .andRoute(
//                         GET("/users").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
//                         handler::getAllUsers
//                 );
//     }
//
//
//     //2 创建服务器完成适配
//     public void createReactorServer() {
//         //路由和 handler 适配
//         RouterFunction<ServerResponse> route = routingFunction();
//         HttpHandler httpHandler = toHttpHandler(route);
//         ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
//         //创建服务器
//         HttpServer httpServer = HttpServer.create();
//         httpServer.handle(adapter).bindNow();
//     }
// }
