package 代码积累库.响应式编程.mvc;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class Control {
    public static void main(String[] args) {
        WebClient webClient = WebClient.create("http://127.0.0.1:50066");

        String id = "1";
        UserHandler.User u = webClient.get()
                .uri("/users/{id}",id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserHandler.User.class)
                .block();
        assert u != null;
        System.out.println(u.getName());//lucy

        //查询所有
        webClient.get()
                .uri("/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(UserHandler.User.class)
                .map(UserHandler.User::getName)
                .buffer()
                .doOnNext(System.out::println)
                .blockFirst();
        //[lucy, mary, jack]

    }
}
