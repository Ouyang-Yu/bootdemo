package 代码积累库;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-08 16:51
 */
public class TimeAPI {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter inFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); //HH24小时
        DateTimeFormatter outFormat = DateTimeFormatter.ofPattern("EEE, MMM d yyyy, KK:mm a");
        System.out.println(now); //2022-05-08T16:34:17.744841800
        System.out.println(inFormat.format(now));//2022/05/08 16:52:38
        System.out.println(outFormat.format(now)); //周日, 5月 8 2022, 04:34 下午

        System.out.println(now.getYear());
        System.out.println(now.getMonthValue());  //区别于getMouth()返回月份英文简写
        System.out.println(now.getDayOfMonth());
        System.out.println(now.getHour());
        System.out.println(now.getMinute());
        System.out.println(now.getSecond());


    }
}
