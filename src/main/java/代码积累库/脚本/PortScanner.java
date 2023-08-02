package 代码积累库.脚本;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * 请注意，该程序需要在Linux操作系统下运行，并需要安装lsof和pidof命令。
 */
public class PortScanner {
    public static void main(String[] args) {
        while (true) {
            int port = new Scanner(System.in).nextInt();
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), port), 1000);

                String processName = findProcessName(port);
                int pid = getPID(processName);
                System.out.println("Port " + port + " is being used by process " + processName + " with PID " + pid);
                System.out.print("Do you want to kill the process? (Y/N) ");
                char input = (char) System.in.read();
                if (input == 'Y' || input == 'y') {
                    killProcess(processName);
                }
            } catch (IOException e) {
                System.out.println("Port " + port + " is not being used.");
            }
        }
    }

    private static String findProcessName(int port) throws IOException {
        Process p = Runtime.getRuntime().exec("lsof -i :" + port);
        String line;
        java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
        input.readLine(); // skip first line
        line = input.readLine();
        String[] parts = line.split("\\s+");
        return parts[0];
    }

    private static void killProcess(String processName) throws IOException {
        Process p = Runtime.getRuntime().exec("killall " + processName);
        try {
            int exitValue = p.waitFor();
            if (exitValue == 0) {
                System.out.println("Process " + processName + " has been killed.");
            } else {
                System.out.println("Failed to kill process " + processName + ".");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static int getPID(String processName) throws IOException {
        Process p = Runtime.getRuntime().exec("pidof " + processName);
        java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
        String line = input.readLine();
        return Integer.parseInt(line);
    }


    @Test
    public void pid() throws IOException {
        Process p = Runtime.getRuntime().exec("pidof " + 8847);
        java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
        String line = input.readLine();
        System.out.println(line);
    }

    @Test
    public void pid1() throws IOException {
        Process p = Runtime.getRuntime().exec("pidof " + 8847);
        java.io.BufferedReader input = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
        String line = input.readLine();
        System.out.println(line);
    }

}

