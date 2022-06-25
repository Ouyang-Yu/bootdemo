package a综合;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-08 16:51
 */
public class TimeAPI {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter inFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outFormat = DateTimeFormatter.ofPattern("EEE, MMM d yyyy, KK:mm a");
        System.out.println(now); //2022-05-08T16:34:17.744841800
        System.out.println(inFormat.format(now));//   2022/05/08 16:52:38
        System.out.println(outFormat.format(now)); //周日, 5月 8 2022, 04:34 下午

        System.out.println(now.getYear());
        System.out.println(now.getMonthValue());  //getMouth()返回月份英文简写
        System.out.println(now.getDayOfMonth());
        System.out.println(now.getHour());
        System.out.println(now.getMinute());
        System.out.println(now.getSecond());

        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }
}
