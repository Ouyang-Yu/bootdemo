package a杂;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-26 11:34
 */
public class 泛型方法 {
    public static <T> T get(T t) { //返回类型前面<>声明泛型
        return t;
    }

    public static void main(String[] args) {
        String sd = get("sd");
        Integer integer = get(123);
        Double aDouble = get(1.2);


    }
}
