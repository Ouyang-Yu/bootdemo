package 代码积累库;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author ouyangyu369@gmail.com
 * @date 2022-06-29  11:10
 */
public class NET {


    @SneakyThrows
    @Test
    public void httpClient用法() {
        // replace HttpURLConnection
        HttpClient.newHttpClient()
                .send(
                        HttpRequest.newBuilder(URI.create("")).build(),
                        HttpResponse.BodyHandlers.ofString()
                )
                .body();
        HttpClient.newHttpClient()
                .sendAsync(
                        HttpRequest.newBuilder(URI.create("")).build(),
                        HttpResponse.BodyHandlers.ofString()
                )
                .thenApply(HttpResponse::body) //response -> response.body()
                .thenAccept(System.out::println);

    }
}
