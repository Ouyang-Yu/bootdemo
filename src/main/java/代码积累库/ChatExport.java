package 代码积累库;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;

public class ChatExport {
    public static void main(String[] args) throws IOException, AWTException, InterruptedException, UnsupportedFlavorException {
        String filename = "chatlog.txt"; // 指定保存文件名
        FileWriter writer = new FileWriter(filename);

        Robot robot = new Robot();
        robot.setAutoDelay(1000);

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String lastText = "";

        while (true) {
            // 将聊天窗口中的内容复制到剪贴板
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_A);
            robot.keyRelease(KeyEvent.VK_A);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_C);
            robot.keyRelease(KeyEvent.VK_C);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            // 获取剪贴板中的文本
            Transferable contents = clipboard.getContents(null);
            if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String text = (String) contents.getTransferData(DataFlavor.stringFlavor);
                // 如果聊天内容发生变化，则将变化的内容写入文件中
                if (!text.isEmpty() && !text.equals(lastText)) {

                    writer.write(text.replaceAll("\\r\\n|\\r|\\n", System.lineSeparator()));
                    writer.flush();
                    lastText = text;
                }
            }

            // 等待一段时间后再次检查内容是否发生变化
            Thread.sleep(5000);
        }
    }
}

