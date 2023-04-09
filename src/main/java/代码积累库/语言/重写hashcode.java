package 代码积累库.语言;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author ouyangyu369@gmail.com
 * @date 2022-06-24  17:23
 */

public class 重写hashcode {

    public static void main(String[] args) {
        HashMap<String, AAA> map = new HashMap<>();
        AAA before = new AAA(999);
//        AAA after = new AAA(999);
        AAA after = before;
        //重写了hashcode后 这两个对象地址竟然是一样的
        //如果value 的地址不一样   key相同的话 是会覆盖原来的value的
        System.out.println(before);
        System.out.println(after);
        map.put("a", before);
        map.forEach((key, value) -> System.out.println(value));
        map.put("a", after);
        map.forEach((key, value) -> System.out.println(value));
    }
    static class AAA {
        Integer a;

        public AAA(Integer a) {
            this.a = a;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            AAA aaa = (AAA) o;
            return Objects.equals(a, aaa.a);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a);
        }
    }
}
