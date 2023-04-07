package 代码积累库;

import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;

public class MyMail {
    @Resource
    private JavaMailSender javaMailSender; //read config file and auto assemble

    @Test
    public void mail() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl() {{
            setHost("smtp.qq.com");
            setUsername("");
            setPassword("");
        }};


//        javax.mail.internet.MimeMessage mimeMessage = mailSender.createMimeMessage();
//        new MimeMessageHelper(mimeMessage, true){{
//            setFrom("");
//            setTo("");
//            setSubject("");
//            setText("", true);
//            addAttachment("fileName.zip",new File(""));
//        }};
//        mailSender.send(mimeMessage);

    }
}
