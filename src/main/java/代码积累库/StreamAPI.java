package 代码积累库;

import kt.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-11 19:51
 */


public class StreamAPI {
    @Test
    public void ds() {
        Double avg = Stream.of(null, 3.0).collect(Collectors.averagingDouble(value -> Optional.ofNullable(value).orElse(0.0)));
        DoubleSummaryStatistics statistics = Stream.of(1.0, 3.0).collect(Collectors.summarizingDouble(Double::doubleValue));
        System.out.println(statistics);
        //DoubleSummaryStatistics{count=2, sum=4.000000, min=1.000000, average=2.000000, max=3.000000}
        long a=1;
        Long aa= 1L;
    }


    @Test
    public void toMap() {
        Map<Boolean, Integer> collect = new ArrayList<String>().stream().collect(
                Collectors.toMap(
                        String::isEmpty,//key
                        String::length,//value
                        Integer::sum// if the same key, value will sum
                ));
        Map<Boolean, Integer> map = new ArrayList<String>().stream().collect(
                Collectors.groupingBy(
                        String::isEmpty,
                        Collectors.collectingAndThen(Collectors.toList(), strings -> strings.stream().map(String::length).reduce(0, Integer::sum))
                )

        );
    }


    @Test
    public void gruopAndSort() {

        List<Person> people = Arrays.asList(
                new Person("Alice", 20),
                new Person("Bob", 25)

        );

        // 按名字分组,每一组内的列表再按照年龄排序
        Map<String, List<Person>> groupedByName = people.stream()
                .collect(Collectors.groupingBy(
                        Person::getName,
                        Collectors.collectingAndThen(Collectors.toList(), this::sortByAge)
                ));

        System.out.println(groupedByName);
    }

    private <RR, R> List<Person> sortByAge(List<Person> list) {
        list.sort(Comparator.comparing(Person::getAge));
        return list;
    }
    @Test
    public void range() {

        Stream.iterate(0, integer -> Math.min(integer + 1, 10 - 1))
                // 从0开始,每次加1,最大值为9,流的长度为10
                .limit(10)
                .forEach(index -> {
                    System.out.println(index + " ");
                });
        range(0, 9, 2)
                .forEach(index ->
                        System.out.println(index + " ")
                );
        range(1, 10, 3)
                .forEach(index ->
                        System.out.println(index + " ")
                );
        range(1, 9, 1, (index)-> System.out.println(index+"fun"));
    }

    Stream<Integer> range(Integer min, Integer max, Integer step) {
        return Stream.iterate(min, integer -> Math.min(integer + step, max))
                .limit((max - min) / step + 1)
                ;
    }
    void range(Integer min, Integer max, Integer step, Consumer<Integer> consumer) {
         Stream.iterate(min, integer -> Math.min(integer + step, max))
                .limit((max - min) / step + 1)
                .forEach(consumer);
    }

    @Test
    public void chars() {
        String collect = String.join(":", "foobar", "foo", "bar")
                .chars()
                .distinct()
                .mapToObj(it -> String.valueOf(((char) it)))
                .sorted()
                .collect(Collectors.joining( ));
        System.out.println(collect);
        boolean x = Stream.of("11", "21").anyMatch("211"::contains);
        System.out.println(x);
        boolean x1 = Stream.of("11", "21").anyMatch(s -> s.contains("11"));
        System.out.println(x1);
    }

    @Test
    public void random() {
        TreeSet<String> set = new TreeSet<>(String::compareTo);

        // 随机展示 5 至 20 之间不重复的整数并进行排序
        new Random(47)
                .ints(3, 5, 20)
                .boxed()
                .distinct()             // 使流中的整数不重复
                .limit(7)            // 获取前 7 个元素
                .sorted()               // 排序
                .forEach(System.out::println);
        int sum = IntStream.range(1, 10).sum();// 55

    }

    /**
     * 统计和分组
     */
    @Test
    public void collectors() {
        Map<String, String> map = users.stream()
                .collect(Collectors.toMap(User::getName, User::getPwd));
        Double collect = users.stream()
                .collect(Collectors.averagingInt(User::getId));
        Integer collect1 = users.stream()
                .filter(it -> it.isVIP)
                .mapToInt(User::getId)
                .sum();//.mapToInt(User::getId).sum();

        代码积累库.StreamAPI.User user = users.stream() // max
                .filter(it -> it.isVIP)
                .max(Comparator.comparingInt(User::getId))
                .orElseThrow();

        users.stream()// sort
                .sorted(Comparator.comparing(User::getName, String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(User::getPwd, String::compareTo)
                        .thenComparingInt(User::getId)
                ).forEach(System.out::println);


        IntSummaryStatistics collect2 = users.stream()
                .filter(it -> it.isVIP)
                .collect(Collectors.summarizingInt(User::getId));// 总结 根据id求最大最小求和平均值和个数

    }

    /**
     * count up the number that every letter show
     */
    @Test
    public void count() throws IOException {
        Files.lines(Paths.get("chatlog.txt"), Charset.defaultCharset())
                .filter(Pattern.compile(".*@gmail\\.com").asPredicate())
                .filter(it -> Pattern.compile(".*@gmail\\.com").matcher(it).find())
                .flatMap(s -> Arrays.stream(s.split("")))
                .collect(Collectors.groupingBy(
                        String::toString,
                        Collectors.counting()
                ))
                .forEach((key, value) -> System.out.println(key + " = " + value));

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class User {

        private Integer id;
        private String name;
        private String pwd;

        private Boolean isVIP;

    }

    ArrayList<User> users = new ArrayList<>() {
        {
            add(new User(1, "a", "111", true));
            add(new User(2, "b", "222", true));
            add(new User(3, "c", "333", false));
        }

    };

    @SneakyThrows
    @Test
    public void dsf() {
        /**
         * 流的基本使用
         */
        List<String> collect = users.stream()
                .filter(it -> it.getId() < 10)
                .sorted(Comparator.comparing(User::getId))  // 从小到大排序
                .sorted(Comparator.comparing(User::getName))
                .map(User::getName)
                .toList();
        Map<Boolean, List<User>> collect2 = users.stream().collect(Collectors.partitioningBy(User::getIsVIP));

        // 根据是不是vip划分two part,只能接受predicate

        Map<Integer, List<User>> collect1 = users.stream().collect(Collectors.groupingBy(User::getId));
        collect1.forEach((key, value) -> System.out.println(key + " = " + value));
        // 根据id 分为多组

        //  collect.forEach(System.out::println);
        Map<Boolean, List<User>> map = users.stream()
                .collect(Collectors.groupingBy(User::getIsVIP));
        // 将list按照属性分组 ,返回一个map ,key是属性,value是对应的相同属性的元素集合

        map.forEach((k, v) -> System.out.println(k + ":" + v));  // 遍历map
    }


    /**
     * \
     * 求同一组某个字段的和并计算一组多少个
     * Collectors.summarizingDouble(Trip::getLineLength)
     */
    @Test
    public void dsa() {


        Map<List<String>, DoubleSummaryStatistics> result = tripList.stream()
                .collect(Collectors.groupingBy(// 按照什么来分组,分组之后球什么
                        trip -> Arrays.asList(trip.getBureauName(), trip.getYear(), String.valueOf(trip.getVolevel())),
                        Collectors.summarizingDouble(Trip::getLineLength)
                ));
        // Group: [1, 2022, 100] Count: 1 Sum: 2.0
        // Group: [1, 2023, 100] Count: 2 Sum: 3.0
        // Group: [1, 2022, 200] Count: 1 Sum: 2.0
        result.forEach((group, stats) -> {
            // System.out.println("Group: " + group + " Count: " + stats.getCount() + " Sum: " + stats.getSum());
            System.out.println(group+stats.toString());
        });


    }

    ArrayList<Trip> tripList = new ArrayList<>() {{
        add(new Trip("1", "2023", 100, 1));
        add(new Trip("1", "2023", 100, 2));
        add(new Trip("1", "2022", 100, 2));
        add(new Trip("1", "2022", 200, 2));
    }};

    @Data
    @AllArgsConstructor
    class Trip {
        private String bureauName;
        private String year;
        private Integer volevel;
        private double lineLength;
    }
}
