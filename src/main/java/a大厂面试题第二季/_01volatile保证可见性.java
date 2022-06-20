package a大厂面试题第二季;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-16 18:18
 */
//class MyData {
//    volatile int num = 0;
//
//    void change() {
//        num = 99;
//    }
//}

public class _01volatile保证可见性 {
    static volatile int a = 0;

    static void change() {
        a = 99;
    }

    public static void main(String[] args) {
//        MyData data = new MyData();
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //  data.change();
            change();
        }).start();

        while (a == 0) {
            //如果main线程 不知道数据改变了 就会一直在这循环
        }
        System.out.println("num changed");


    }
}
