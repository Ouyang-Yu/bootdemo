package 代码积累库.网络;

import org.apache.http.util.TextUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

public class MyHttpURLConnection {

    @Test
    public void post() throws IOException {
        postRequestByHttpURLConnection();
    }

    private void postRequestByHttpURLConnection() throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://postman-echo.com/post").openConnection(); {
            httpURLConnection.setRequestMethod("POST");
            /*4. 设置允许输入输出*/
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
        }
        OutputStream outputStream = httpURLConnection.getOutputStream(); {
            String params = MessageFormat.format(
                    "username={0}&password={1}",
                    URLEncoder.encode("wildma", StandardCharsets.UTF_8),
                    URLEncoder.encode("123456", StandardCharsets.UTF_8)
            );
            outputStream.write(params.getBytes());
            outputStream.flush();
            outputStream.close();
        }

        httpURLConnection.connect();
        if (httpURLConnection.getResponseCode() == HttpStatus.OK.value()) {
            InputStream inputStream = httpURLConnection.getInputStream();
            String data = inputStream2String(inputStream);
            System.out.println(data);
        }

    }

    private void getRequestByHttpURLConnection() {
        try {
            // 1. 创建 URL 对象
            URL url = new URL("https://www.baidu.com");
            // 2. 调用 URL 对象的 openConnection 方法获取 HttpURLConnection 实例
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // 3. 设置请求方法（这里是 GET 请求）
            httpURLConnection.setRequestMethod("GET");
            // 4. 连接
            httpURLConnection.connect();
            // 5. 检查是否请求成功，状态码 200 表示成功
            if (httpURLConnection.getResponseCode() == 200) {
                // 6. 调用 HttpURLConnection 对象的 getInputStream 方法获取响应数据的输入流
                InputStream inputStream = httpURLConnection.getInputStream();
                // 7. 将输入流转换成字符串
                String data = inputStream2String(inputStream);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String inputStream2String(InputStream inputStream) {
        String data = "";
        BufferedReader bufferedReader = null;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            data = stringBuilder.toString();
            if (!TextUtils.isEmpty(data)) {
                // 去除最后一个多余的换行符
                data = data.substring(0, data.length() - 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }


}
