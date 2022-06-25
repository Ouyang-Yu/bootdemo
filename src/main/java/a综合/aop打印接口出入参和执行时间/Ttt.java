package a综合.aop打印接口出入参和执行时间;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;  //if arg is null,ide will prompt
import org.springframework.lang.NonNull;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-11 19:20
 */
@Slf4j
public class Ttt {
    @CountTime
    public static void main(String[] args)  {


      //  te(null);  //jetbrains的   IDE有提示且运行会报错
        aa(null);  //Spring的     IDE有提示但是可以运行
        //bb(null);  //lombok 自动加的try catch

        for (int i = 0; i < 500000000; i++) {

        }

    }

    @CountTime
    @SneakyThrows
    static void te(@NotNull String string) {// jetBrain offer
        // if use a null arg is null,ide will prompt
        System.out.println(string);
        Thread.sleep(0x7d0);
    }

    static void aa(@NonNull String s) {  //spring offer
        System.out.println(s);
    }
    static void bb(@lombok.NonNull String s) { //only generate if null code ,ide will not prompt
        System.out.println(s);
    }
}
