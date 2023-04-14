package 代码积累库.响应式编程.mvc;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    //根据id查用户。mono返回0-1
    Mono<UserHandler.User> getUserById(int id);

    //查询所有用户，flux返回多个
    Flux<UserHandler.User> getAllUser();

    //添加用户。mono返回0-1
    Mono<Void> saveUserInfo(Mono<UserHandler.User> user);
}
