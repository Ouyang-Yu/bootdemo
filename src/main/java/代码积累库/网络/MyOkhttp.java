package 代码积累库.网络;

import lombok.val;
import okhttp3.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

public class MyOkhttp {


    public String post(String url, String json) throws IOException {
        val okHttpClient = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 1080)))
                .build();
        var request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.get("application/json; charset=utf-8"), json))
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
        try (var response = okHttpClient.newCall(request).execute()) {
            //execute方法，是一个同步请求方法，发起网络请求时会阻塞线程
            return response.body().string();
        }

    }
}
