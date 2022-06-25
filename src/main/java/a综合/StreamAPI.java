package a综合;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-11 19:51
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
 class User {

    private Integer id;
    private String name;
    private String pwd;

    private Boolean excellent;

}
public class StreamAPI {

    @SneakyThrows
    public static void main(String[] args)  {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User(1, "a", "111",true));
        users.add(new User(2, "b", "222",true));
        users.add(new User(3, "c", "333", false));

        /**
         * 流的基本使用
         */
        List<String> collect = users.stream()
                .filter(it -> it.getId() < 10)
                .sorted(Comparator.comparing(User::getId))  //从小到大排序
                .sorted(new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return o1.getId() - o2.getId();
                    }
                })
                .map(User::getName)  //将每一个元素映射为 name,代替原来的元素
                .collect(Collectors.toList());


        collect.forEach(System.out::println);
        Map<Boolean, List<User>> map = users.stream()
                .collect(Collectors.groupingBy(User::getExcellent));
        //将list按照属性分组 ,返回一个map ,key是属性,value是对应的相同属性的元素集合

        map.forEach((k, v) -> System.out.println(k + ":" + v));  //遍历map

        /**
         * 除list生成流,还有其他方式
         */
        Stream<int[]> stream =  Stream.of(new int[10]);//从数组中获取stream
        Stream<Integer> integerStream = Stream.of(1, 2, 3);

        try {
            Stream<String> lines = Files.lines(Paths.get("test.txt"), Charset.defaultCharset());//从文件中获取stream
        } catch (IOException e) {
            System.out.println("none");
        }

        Stream<Integer> limit = Stream.iterate(0, n -> n + 2).limit(5);
        limit.forEach(System.out::println); //通过初始值迭代生成

        Stream<Double> random = Stream.generate(Math::random).limit(5);
        random.forEach(System.out::println);//通过函数 生成
        /**
         * 中间操作
         * skip跳过流中的元素
         * distinct去除重复元素
         * limit返回指定流个数
         * map通过某种规则映射成为另外元素
         * allMatch匹配所有
         * anyMatch匹配其中一个
         *
         */
        /**
         * 终端操作
         *count统计流中元素个数
         * findFirst查找第一个
         * reduce将流中的元素组合起来  .reduce(0, Integer::sum)
         * 通过min/max获取最小最大值
         * foreach进行元素遍历
         */


    }
}
