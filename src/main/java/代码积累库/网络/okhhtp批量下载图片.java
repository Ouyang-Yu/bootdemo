package 代码积累库.网络;

 import lombok.val;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class okhhtp批量下载图片 {

    @Test
    public void ds() {
        for (int i = 1; i <= 69; i++) {
            String name = "";
            if (i < 10) {
                name = "00" + i;
            } else if (i < 99) {
                name = "0" + i;
            } else {
                name = "" + i;
            }
            this.download(
                    "https://img.itmtu.com/mm/s/slct/alpha/alpha.008/0" + name + ".jpg",
                    "C:\\4\\",
                    name,
                    new OnDownloadListener() {
                        @Override
                        public void onDownloadSuccess(File file) {
                            System.out.println("success");
                        }

                        @Override
                        public void onDownloading(int progress) {
                            System.out.println(progress + "");
                        }

                        @Override
                        public void onDownloadFailed(Exception e) {
                            System.err.println(e.getMessage());
                        }
                    }
            );
        }
    }
    // 80%换新服务是指

    interface OnDownloadListener {
        /**
         * 下载成功之后的文件
         */
        void onDownloadSuccess(File file);

        /**
         * 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载异常信息
         */
        void onDownloadFailed(Exception e);
    }

    /**
     * @param url          下载连接
     * @param destFileDir  下载的文件储存目录
     * @param destFileName 下载文件名称
     * @param listener     下载监听
     */
    public void download(final String url, final String destFileDir, final String destFileName, final OnDownloadListener listener) {

        val okHttpClient = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                // .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 1080)))
                .build();
        // 构建一个非常基本的request，只有一个url不带任何设置和参数
        Request request = new Request.Builder()
                .url(url)
                .build();

        // 异步请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败监听回调
                listener.onDownloadFailed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try (
                        InputStream inputStream = response.body().byteStream();

                ) {

                    // 储存下载文件的目录
                    // 2Kb的小缓存
                    byte[] buf = new byte[2048];
                    int len = 0;
                    File dir = new File(destFileDir);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File file = new File(dir, destFileName);

                    FileOutputStream fileOutputStream = null;

                    // 获取响应的字节流 这个时候 请求的数据还没返回接受
                    // inputStream = response.body().byteStream();
                    // 获取下下载文件的大小 用于判断下载进度百分比
                    long total = response.body().contentLength();
                    // 创建一个文件输出流用于写文件
                    fileOutputStream = new FileOutputStream(file);
                    // 文件已写入长度
                    long sum = 0;
                    while ((len = inputStream.read(buf)) != -1) {
                        fileOutputStream.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中更新进度条
                        listener.onDownloading(progress);
                    }
                    fileOutputStream.flush();
                    // 下载完成
                    listener.onDownloadSuccess(file);
                } catch (Exception e) {
                    listener.onDownloadFailed(e);
                }
            }
        });
    }
}
