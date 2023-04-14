package 代码积累库.JAVA;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class 新API {
    void onMessage(int a) {
        switch (a) { //since 14
            case 0 -> System.out.println("zero");
            case 1 -> System.out.println("one");
            case 2, 3, 4 -> System.out.println("few");
            default -> throw new IllegalArgumentException();
        }
    }

    @Test
    public void strEncode() throws UnsupportedEncodingException {
//        0xEF 0xBF 0xBD
        byte[] gbk = "大厦".getBytes("GBK");
        String unicodeStr = new String(gbk, UTF_8);
        System.out.println(unicodeStr);//����
        //用GBK编码的字符串,用unicode解码时候会无法识别,显示为��
        String gbkStr = new String(unicodeStr.getBytes(UTF_8), "GBK");
        System.out.println(gbkStr);//锟斤拷锟斤拷
        //��用unicode编码再用GBK解码会显示为锟斤拷


    }
}
