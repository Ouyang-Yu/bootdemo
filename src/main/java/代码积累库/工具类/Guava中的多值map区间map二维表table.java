package 代码积累库.工具类;

import com.google.common.collect.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * 多值map
 * 区间map
 * 二维表table
 */
public class Guava中的多值map区间map二维表table {
    @Test
    public void dsdsd() {
        String volevel = "+858";
        System.out.println(Integer.parseInt(volevel));

        System.out.println(volevel.substring((volevel.length() - 3)));
        System.out.println(volevel.substring((0)));

    }

    @Test
    public void map() {

        new HashMap<Integer, Integer>() {{
            put(1, 1);
            put(1, 2);
            // 普通map的key是不能重复的,ketSet,这样只会覆盖,要想一个key对应多个value,只能value放list
        }}.forEach((a, b) -> System.out.println(a + b));
    }

    @Test
    public void multiMap() {
        Multimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.put("day",1);
        multimap.put("day",2);
        multimap.put("day",8);
        multimap.put("month",3);
        multimap.forEach((a,b)-> System.out.println(a+b));
        System.out.println(multimap);//{day=[1, 2, 8]}
//每个key对应的都是一个集合
    }

    @Test
    @SuppressWarnings("all")
    public void rangeMap() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closedOpen(0,60),"fail");
        rangeMap.put(Range.closed(60,90),"satisfactory");
        rangeMap.put(Range.openClosed(90,100),"excellent");

        System.out.println(rangeMap.get(59));

    }
    @Test
    public void table() {
        Table<String, String, Integer> table = HashBasedTable.create();

        table.put("Hydra", "Jan", 20);
        table.put("Hydra", "Feb", 28);
        table.put("Trunks", "Jan", 28);
        table.put("Trunks", "Feb", 16);

        Integer dayCount = table.get("Hydra", "Feb");

        // 1、获得key或value的集合
        Set<String> rowKeys = table.rowKeySet();
        Set<String> columnKeys = table.columnKeySet();
        Collection<Integer> values = table.values();
        //2.计算某行某列的累加和
        Integer jan = table.row("Jan")
                .values().stream()
                .reduce(Integer::sum)
                .orElse(0);
        // 3.行和列的转置，直接调用Tables的静态方法transpose
        Tables.transpose(table)
                .cellSet()
                .forEach(cell -> System.out.println(cell.getRowKey() + cell.getColumnKey() + cell.getValue()));

    }


    @Test
    public void ds() {
        ArrayList<String> strings = new ArrayList<>();
        add(strings);// 改变了实参
        strings.forEach(System.out::println);

        int a = 1;
        add(a);
        System.out.println("dsd" + a);// 没有改变

        Integer integer = 1;
        add(integer);
        System.out.println(integer);// 没有改变
    }

    private void add(Integer a) {
        a = a + 1;
        System.out.println(a);
    }

    private void add(ArrayList<String> strings) {
        strings.add("11");
    }


}
