package 代码积累库;

import cn.hutool.Hutool;
import cn.hutool.core.io.IoUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ouyangyu369@gmail.com
 * @date 2022-06-29  11:11
 */
@Slf4j
public class 文件IO {

    @Test
    public void readFile() throws FileNotFoundException {
        Hutool.getAllUtils().forEach(System.out::println);
        String s = IoUtil.toStr(new ByteArrayOutputStream(1), StandardCharsets.UTF_8);
        byte[] bytes = DigestUtils.md5Digest("".getBytes(StandardCharsets.UTF_8));
    }
    @Test
    public void s() throws IOException {
        //从文件读取行内容 到流 并统计每个字符出现次数
        Files.lines(Paths.get(".gitignore"), Charset.defaultCharset())
                .flatMap(s -> Stream.of(s.split("")))
                .collect(Collectors.groupingBy(String::toString, Collectors.counting()))
                .forEach((k, v) -> System.out.println(k + ":" + v));
    }

    //nio 实现文件拷贝   Files.copy 提供实现
    public static void copyFileByChannel(File source, File dest) throws IOException {
        try (
                FileChannel sourceChannel = new FileInputStream(source).getChannel();
                FileChannel targetChannel = new FileOutputStream(dest).getChannel();
        ) {
            for (long count = sourceChannel.size(); count > 0; ) {
                long transferred = sourceChannel.transferTo(sourceChannel.position(), count, targetChannel);
                sourceChannel.position(sourceChannel.position() + transferred);
                count -= transferred;
            }

        }
    }

    @Test
    public void getenv() {
        System.out.println(System.getenv("mysql"));

        System.getenv().forEach((k, v) -> System.out.println(k + "    :    " + v));
    }

    static Object reflect(Object object, String className, String methodName, Object[] parameters, Class<?>[] parameterTypes) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Class<?> objectClass = Class.forName(className);
        Method method = objectClass.getMethod(methodName, parameterTypes);
        return method.invoke(object, parameters);
    }


    @Test
    public void sda() {
        deleteExpiredFiles("", 2);
    }

    /**
     * 删除给定路径下所有过期文件,包括嵌套的文件
     *
     * @param fromDir 文件夹路径
     * @param dayExpired  过期多少天了的应该被删除
     * @return 删除成功的文件个数
     */
    public Integer deleteExpiredFiles(@NotNull String fromDir, @NotNull Integer dayExpired) {
        File srcDir = new File(fromDir);
        if (!srcDir.exists()) {
            return 0;
        }
        File[] files = srcDir.listFiles();
        if (files == null || files.length == 0) {
            return 0;
        }

        final int[] count = {0};
        for (File file : files) {
            if (file.isFile()) {
                long day = Math.abs(file.lastModified() - System.currentTimeMillis()) / (1000 * 60 * 60 * 24);
                if (day >= dayExpired) {
                    if (file.delete()) {
                        count[0]++;
                    } else {
                        log.info("delete failed");
                    }
                }
            } else {
                deleteExpiredFiles(file.getAbsolutePath(), dayExpired);
            }
        }
        Stream.of(files)
                .filter(it -> System.currentTimeMillis() - it.lastModified() >= dayExpired)
                .forEach(file -> {
                    if (file.delete()) {
                        count[0]++;
                    } else {
                        log.info("delete failed");
                    }
                });

        return count[0];
    }

}


