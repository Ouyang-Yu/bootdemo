package a杂;

import org.junit.jupiter.api.Test;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-10 15:25
 */

interface Person {
    void name();
}

class Man implements Person {

    @Override
    public void name() {
        System.out.println("man");
    }
}

class Woman implements Person {

    @Override
    public void name() {
        System.out.println("woman");
    }
}

public class 接口的多个实现类 {
    static Person person;

    //原来接口在使用之前   必须声明为具体的类型 不允许直接new 接口,除非写匿名实现类
    public static void main(String[] args) {
        person.name();
    }
    @Test
    public void testA() {

    }
}

