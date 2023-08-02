package 代码积累库;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Strings;
import org.junit.jupiter.api.Test;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author ouyangyu369@gmail.com
 * @date 2022-06-28  12:28
 */

public class String处理 {
    @Test
    public void strEncode() throws UnsupportedEncodingException {
//        0xEF 0xBF 0xBD
        byte[] gbk = "大厦".getBytes("GBK");
        String unicodeStr = new String(gbk, UTF_8);
        System.out.println(unicodeStr);//����
        // 用GBK编码的字符串,用unicode解码时候会无法识别,显示为��
        String gbkStr = new String(unicodeStr.getBytes(UTF_8), "GBK");
        System.out.println(gbkStr);// 锟斤拷锟斤拷
        //��用unicode编码再用GBK解码会显示为锟斤拷


    }

    @Test
    public void dsd() {

        "".lines().forEach(System.out::println);
        String s = "".stripLeading();
        System.out.println("Hello\\nWorld\\tJava".translateEscapes());

    }


    @Test
    public void strmatch() {
        System.out.println("123".contains("12"));
        String sentence = "I love reading books. My favorite book is Harry Potter.";
        //表示只匹配单词,不匹配bookstore这种,大写B才是后者
        Matcher matcher = Pattern.compile("\\bbook\\b").matcher(sentence);
        while (matcher.find()) {
            System.out.println("Found at index " + matcher.start() + " - " + matcher.end());
        }
    }

    @Test
    public void emptyutils() {
        System.out.println(Strings.isNullOrEmpty("")); //guava
        System.out.println(StrUtil.isBlank("    ")); //hutool
        System.out.println(StrUtil.isEmpty(""));
        System.out.println("StringUtils.hasLength(\"\") = " + StringUtils.hasLength("")); //spring
        System.out.println("StringUtils.hasText(\"     \") = " + StringUtils.hasText("     "));
        System.out.println("ObjectUtils.isEmpty(Optional.of(null)) = " + ObjectUtils.isEmpty(Optional.empty()));

    }

    @Test
    public void format() {
        MessageFormatter.format("{}", 1).getMessage();
        String format = MessageFormat.format("{0}123", 1);
        String format1 = String.format("%s333", 213);
        String 啊 = "%dAAA".formatted("啊");//

        String format2 = StrUtil.format("{}{}", 1, 2);
    }
    /**
     * string format 用法
     * William James Smiths 转化为 Smiths,William.J
     */
    public String parseName(String name) {
        String[] words = name.split(" ");
//        MessageFormat.format("{0},{1}.{2}",)
        return String.format(
                "%s,%s.%c",
                words[2],
                words[0],
                words[1].toUpperCase().charAt(0)
        );
    }


    /**
     * Collectors的分组函数groupingBy
     * 统计str中每个字符出现次数
     */
    @Test
    public void count() {
        Map<String, Long> map = Stream.of("abcc".split(""))
                .collect(Collectors.groupingBy(String::toString, Collectors.counting()));
        map.forEach((k, v) -> System.out.println(k + "   :   " + v));
    }

}
