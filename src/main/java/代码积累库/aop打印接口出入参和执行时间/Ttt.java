package 代码积累库.aop打印接口出入参和执行时间;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-11 19:20
 */
@Slf4j
public class Ttt {
    public static void main(String[] args)  {

        aa(null);  // Spring的     IDE有提示但是可以运行
        NotNullByJetBrain(null);  // jetbrains的   IDE有提示且运行会报错
        bb(null);  // lombok 自动加的try catch

    }

    static void NotNullByJetBrain(@NotNull String string) {// jetBrain offer
        // if use a null arg is null,ide will prompt
        System.out.println(string);
    }

    static void aa(@NonNull String s) {  //spring offer
        System.out.println(s);
    }
    static void bb(@lombok.NonNull String s) { //only generate if null code ,ide will not prompt
        System.out.println(s);
    }
}
