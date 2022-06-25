package a综合;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-15 13:46
 */
public class Singleton {
    private static volatile Singleton singleton; //volatile禁止指令重排序 否则
    private Singleton () {  // 构造器私有化

    }

    public static Singleton getInstance() { //对外暴露方法  里面双重检查锁
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
    /**
     *  也可以用静态内部类
     *  另外枚举也是一种单例
     */

}
