package 代码积累库.脚本;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * netstat -ano|findstr "8847"
 * TCP    127.0.0.1:12495        127.0.0.1:8847         ESTABLISHED     9420
 * <p>
 * <p>
 * tasklist |findstr 26196 模糊查询进程
 * taskkill /T /F /PID 26196
 * <p>
 * <p>
 * tasklist /fi 'imagename eq xx' 精确匹配进程
 * tasklist /fi 'pid eq 1196'
 * java.exe                      1196 Console                    3    502,788 K
 */
public class Win端口占用查询 {
    public static String inputStreamToString(InputStream inputStream) throws IOException {
        java.io.ByteArrayOutputStream resultStream = new java.io.ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            resultStream.write(buffer, 0, length);
        }
        return resultStream.toString(StandardCharsets.UTF_8);
        // return new String(com.google.common.io.ByteStreams.toByteArray(inputStream), StandardCharsets.UTF_8);
        // 谷歌的字节流工具类
    }

    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.println("please enter an post");
            int port = new Scanner(System.in).nextInt();

            String pid = getPidByPort(String.valueOf(port));

            if (pid != null) {

                System.out.println(infoByPID(pid));
                System.out.print("Do you want to kill the process? (Y/N) ");
                char input = (char) System.in.read();
                if (input == 'Y' || input == 'y') {
                    killProcess(pid);
                }
            } else {
                System.out.println("no process by port " + port);
            }
        }
    }

    private static String infoByPID(String pid) throws IOException {
        // tasklist /fi 'pid eq 1196'
        // 不知道为什么加了条件的netstat -ano | findstr 111执行结果为空,但netstat -ano 不带条件就行
        Process exec = Runtime.getRuntime().exec("tasklist /fi 'pid eq " + pid + "'");
        return inputStreamToString(exec.getInputStream());
    }

    private static String getPidByPort(String port) throws IOException {
        Process p = Runtime.getRuntime().exec("netstat -ano  ");
        // Process p = Runtime.getRuntime().exec("netstat -ano|findstr " + port);
        // String out= new String(com.google.common.io.ByteStreams.toByteArray(p.getInputStream()), StandardCharsets.UTF_8);
        String out = inputStreamToString(p.getInputStream());
        var pids = new ArrayList<String>();
        out.lines().forEach(line -> {
            if (line.contains(port + "")) {
                String[] split = line.trim().split("\\s+");
                String local = split[1];
                String localPost = local.substring(local.lastIndexOf(":") + 1);
                if (localPost.equals(port)) {
                    // pid[0] = split[4];
                    pids.add(split[4]);
                }
            }
        });
        if (pids.size() == 0) {
            return "";
        }

        return pids.get(0);// 不用考虑多个pid,都是重复

        // if (pids.size() != 1) {
        //     if (pids.size() == 0) {
        //         return null;
        //     }
        //     if (new HashSet<>(pids).size() > 1) {
        //         System.out.println("more than one pid " + String.join(",", pids));
        //     }
        //     return String.join(",", new HashSet<>(pids));
        // }
        // return pids.get(0);

    }


    private static void killProcess(String pid) throws IOException {
        Process p = Runtime.getRuntime().exec("taskkill /f /pid " + pid);
        try {
            int exitValue = p.waitFor();
            if (exitValue == 0) {
                System.out.println("Process " + pid + " has been killed.");
            } else {
                System.out.println("Failed to kill process " + pid + ".");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
