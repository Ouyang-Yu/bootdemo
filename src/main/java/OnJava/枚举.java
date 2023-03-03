package OnJava;

import org.junit.jupiter.api.Test;

/**
 * @author ouyangyu369@gmail.com
 * @time 2023-01-18 19:38
 */

public class 枚举 {
    enum Color {
        GREEN,BLUE("I love this"),;  //枚举元素结束加分号
        public String description;

        private Color() {

        }

        private Color(String desc) {
            this.description = desc;
        }

        public String getDescription() {
            return description;
        }


    }

    @Test
    public void test1() {
        for (Color c : Color.values()) {

            int ordinal = c.ordinal();//
            //枚举元素 ==地址相同 equals内容相同
            c.getDeclaringClass();//枚举类名
            c.name();c.toString();//same 都可以重写
            System.out.println(c.getDescription());
            System.out.println(c.description);
        }

    }
}
