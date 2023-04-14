package 代码积累库.文件处理;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-22 16:13
 */
public class 遍历文件夹下所有文件 {

    public static void main(String[] args) {
        String path = "C:\\Users\\ouyan\\Desktop\\国信\\02_ware Workstation Pro 16";

        getFiles(path)
                .forEach(System.out::println);
    }

   static ArrayList<File> getFiles(String path) { //封装隐藏细节,外面只需要提供path

        return readAllFile(new File(path), new ArrayList<>());
    }

    /**
     *
     * @param file
     * @param files
     * @return
     */
    private static ArrayList<File> readAllFile(File file,ArrayList<File> files) {

        if (file.isFile()) {
            files.add(file);
        } else if (file.isDirectory()) {
            for (File item : Objects.requireNonNull(file.listFiles())) {
                readAllFile(item,files);
            }
        }
        return files;
    }
}
