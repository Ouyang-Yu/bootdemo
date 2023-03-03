package 代码积累库;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

/**
 * @author ouyangyu369@gmail.com
 * @time 2023-02-27 15:12
 */
public class Mytest {
    @Test
    public void test() throws Exception {
        Long aa = null;
        long bb = 0;
        System.out.println(new Timestamp(aa));
        System.out.println(new Timestamp(bb));
    }

}
