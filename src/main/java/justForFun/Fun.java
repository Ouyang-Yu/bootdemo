package justForFun;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

/**
 * OuYang <a href="mailto:2455356027@qq.com">Mail</a>
 * 2023/8/31 9:07
 */
public class Fun {
    /**
     * compute how long currentTimeMillis can be greater than long
     */
    @Test
    public void a() {
        System.out.println(System.currentTimeMillis());//1680591588177
        System.out.println(Long.MAX_VALUE);//9223372036854775807 = 19位


        BigInteger max = new BigInteger("9223372036854775807");
        BigInteger now = new BigInteger(String.valueOf(System.currentTimeMillis()));
        BigInteger day = new BigInteger(String.valueOf(60*60*1000)).multiply(new BigInteger("24"));
        BigInteger divide = max.subtract(now).divide(day);
        System.out.println(divide);//106751971716 天,大概2.92亿年,

        System.out.println((9223372036854775807L-1680591588177L)/(1000*60*60*24));
    }
}
