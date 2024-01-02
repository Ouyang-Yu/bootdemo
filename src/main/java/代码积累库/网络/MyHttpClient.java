package 代码积累库.网络;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author ouyangyu369@gmail.com
 * @date 2022-06-29  11:10
 */
public class MyHttpClient {
    @Test
    public void dd() {

    }

    public static void main(String[] args) {
        // LocalDateTime parse = LocalDateTime.parse("2023-08-18", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate localDate = LocalDate.parse("2023-08-18", DateTimeFormatter.ofPattern("yyyy-MM-dd"));


        System.out.println(ChronoUnit.DAYS.between(localDate, LocalDateTime.now()));
        System.out.println(localDate);
        System.out.println(Duration.between(LocalDateTime.now(), LocalDate.now().plusDays(1)));
        System.out.println(Duration.between(LocalDateTime.now(), LocalDate.now().plusDays(1)).toDays());
        // System.out.println(Duration.between(localDate,LocalDate.now()).toDays());
    }

    @SneakyThrows
    @Test
    public void httpClient用法() {
        // j
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
                .thenApply(HttpResponse::body) // response -> response.body()
                .thenAccept(System.out::println);

    }

}


