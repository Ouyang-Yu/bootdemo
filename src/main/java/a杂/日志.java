package a杂;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-12 17:10
 */
@Slf4j
public class 日志 {
    public static void main(String[] args) {
        //        //level
//        log.error();
//        log.warn();
//        log.info();
//        log.debug();

        //方法开始 info下 入参
        //方法结束 info下 返回结果

        //不要用e.printStackTrace

        if (log.isDebugEnabled()) {
            log.debug("args are {}", (Object) args); //use placeholder in log info
        }

        // 在异常处理中  日志带上 异常的详细信息 比如方法参数
    }
}
