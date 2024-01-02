package 代码积累库.工具库的使用;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.extra.pinyin.engine.pinyin4j.Pinyin4jEngine;
import cn.hutool.http.HttpUtil;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Hutool工具 {


    @Test
    public void pinyin() {
        String lv = new Pinyin4jEngine().getPinyin('吕');//hutool
        String[] lvs = PinyinHelper.toHanyuPinyinStringArray('吕');//pinyin4j
        String[] lvss = PinyinHelper.toHanyuPinyinStringArray('欧');//pinyin4j
        System.out.println("lv = " + lv);
        System.out.println(Arrays.toString(lvs));
        System.out.println("lvss = " + Arrays.stream(lvss).toList());

    }

    @Test
    public void digest() {
        String s = DigestUtil.md5Hex("123456"); System.out.println(s); byte[] bytes = DigestUtil.sha256("123456");
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }

    @Test
    public void s() {
        Convert.toStr(new Object(), "2"); int[] a = {1}; Console.log("this is {}", a); // 和pl优势可以占位符
    }

    @Test
    public void mail() {
        MailUtil.send(
                new ArrayList<>() {{add("toEmail");}},
                "测试",
                "邮件来自Hutool群发测试",
                false
        );
        // 在 classpath（在标准 Maven 项目中为src/main/resources）的 config 目录下新建mail.setting文件，完整配置如下（邮件服务器必须支持并打开 SMTP 协议）：
        //
        //# 邮件服务器的SMTP地址，可选，默认为smtp.<发件人邮箱后缀>
        // host = smtp.yeah.net
        //# 邮件服务器的SMTP端口，可选，默认25
        // port = 25
        //# 发件人（必须正确，否则发送失败）
        // from = hutool@yeah.net
        //# 用户名，默认为发件人邮箱前缀
        // user = hutool
        //# 密码（注意，某些邮箱需要为SMTP服务单独设置授权码，详情查看相关帮助）
        // pass = q1w2e3
        MailAccount account = new MailAccount(); {
            account.setHost("smtp.yeah.net"); account.setPort(25); account.setAuth(true);
            account.setFrom("hutool@yeah.net"); account.setUser("hutool"); account.setPass("q1w2e3");
        }

        MailUtil.send(account, CollUtil.newArrayList("hutool@foxmail.com"), "测试", "邮件来自Hutool测试", false);

    }

    @Test
    public void id() {
        //fast 比random 快
        //simple没有横杠
        System.out.println(cn.hutool.core.lang.UUID.randomUUID());
        System.out.println(IdUtil.randomUUID());
        System.out.println(IdUtil.simpleUUID());
        System.out.println(IdUtil.fastUUID());
        System.out.println(IdUtil.fastSimpleUUID());
        System.out.println(IdUtil.getSnowflakeNextId());

        System.out.println(UUID.randomUUID());
        //b6af54b7-7c48-4ad1-bbf9-9811e9045f78
        // d5c39b70-0f5e-4b42-a026-af422d4fe7db
        // 0a82c6bf49dd4783b94309601e496b9f
        // 8df6ac5f-f2d3-4555-9a49-10a9eaa2b638
        // 1c541ed4965c422e949912e010adc386
        // cn.hutool.core.lang.Snowflake@1757cd72
        // fbb7a7f4-511a-4949-9842-80740617da3b

    }

    @Test
    public void net() {
        String urlString = "https://www.baidu.com";
        String result1 = HttpUtil.get(urlString, StandardCharsets.UTF_8);
        HttpUtil.post(urlString, "请求参数的map或者请求体");
        // paramMap.put("file", FileUtil.file("D:\\face.jpg")); //uploadFile


    }

    @Test
    public void cache() {
        // Hutool 提供了常见的几种缓存策略的实现：
        //
        // FIFO(first in first out) ：先进先出策略。
        // LFU(least frequently used) ：最少使用率策略。
        // LRU(least recently used) ：最近最久未使用策略。
        // Timed ：定时策略。
        // Weak ：弱引用策略。
        // 并且，Hutool 还支持将小文件以 byte[] 的形式缓存到内容中，减少文件的访问，以解决频繁读取文件引起的性能问题。
        //
        // FIFO(first in first out) 策略缓存使用:
        //
        Cache<String, String> fifoCache = CacheUtil.newFIFOCache(3); {

            // 加入元素，每个元素可以设置其过期时长，DateUnit.SECOND.getMillis()代表每秒对应的毫秒数，在此为3秒
            fifoCache.put("key1", "value1", DateUnit.SECOND.getMillis() * 3);
            fifoCache.put("key2", "value2", DateUnit.SECOND.getMillis() * 3);
            fifoCache.put("key3", "value3", DateUnit.SECOND.getMillis() * 3);

            // 由于缓存容量只有3，当加入第四个元素的时候，根据FIFO规则，最先放入  ]的对象将被移除
            fifoCache.put("key4", "value4", DateUnit.SECOND.getMillis() * 3);
        }
        // value1为null
        String value1 = fifoCache.get("key1");
    }

}
