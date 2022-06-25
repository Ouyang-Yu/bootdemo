package a综合;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-22 16:13
 */
public class 遍历文件夹下所有文件 {
    static ArrayList<File> files = new ArrayList<>();
    public static void main(String[] args) {
        String path = "C:\\Users\\ouyan\\Desktop\\国信\\02_ware Workstation Pro 16";
//        File file = new File(path);
//        ArrayList<File> files = readAllFile(file,new ArrayList<>());

        getFiles(path)
                .forEach(System.out::println);
    }

   static ArrayList<File> getFiles(String path) {

        return readAllFile(new File(path), new ArrayList<>());
    }

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
