package OnJava;

import kt.Person;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author ouyangyu369@gmail.com
 * @time 2023-01-22 21:13
 */
public class _5_8任务 {
    class NapTask implements Runnable {
        int id;

        public NapTask(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "NapTask{" +
                    "id=" + id +
                    '}';
        }

        @SneakyThrows
        @Override
        public void run() {
            Thread.sleep(1000);
            System.out.println(this + " " + Thread.currentThread().getName());
        }
    }

    @SneakyThrows
    @Test
    public void test() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        IntStream.range(0, 10)

                .mapToObj(NapTask::new)
                .forEach(executor::execute);
        System.out.println("主线程所有任务已提交");
        executor.shutdown();// main告诉线程池不再接受新的任务,做完已有的就停止,线程池没有结束之后,main才结束
        while (!executor.isTerminated()) {
            System.out.println(Thread.currentThread().getName() + "正在运行");
            Thread.sleep(1000);

        }


    }

    @Test
    public void test1() {
        ExecutorService executor = Executors.newCachedThreadPool();
        // 通过结构一致性支持lambda和方法引用
        Future<?> future = executor.submit(() -> System.out.println(1));// 在传入runnable时候和execute()一样
        Future<Integer> submit = executor.submit(() -> {
            System.out.println("");
            return 1;
        });  // 所有的submit都会返回一个future00

    }

    AtomicBoolean running = new AtomicBoolean(true);

    @Test
    public void test2() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        IntStream.range(0, 10)
                .mapToObj(getMapper(running))
                .peek(executorService::execute)
                .forEach(it -> running.set(false));
        executorService.shutdown();
    }

    @NotNull
    private IntFunction<Runnable> getMapper(AtomicBoolean running) {
        return new IntFunction<Runnable>() {
            @Override
            public Runnable apply(int value) {
                return new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        while (running.get()) {
                            Thread.sleep(1000);
                        }
                        System.out.println(value + " ");
                    }
                };
            }
        };
    }

    @Test
    public void test3() {
        List<Runnable> tasks = IntStream.range(0, 10)
                .mapToObj(getMapper(running))
                .toList();
        tasks.forEach(it -> running.set(false));
        tasks.stream()
                .map(CompletableFuture::runAsync)
                .forEach(CompletableFuture::join);
    }

    static class Machine {
        private final int id;
        private State state = State.ONE;

        Machine(int id) {
            this.id = id;
        }

        public enum State {
            ONE, TWO, THREE,
            ;

            State step() {
                if (equals(THREE)) {
                    return THREE;
                }
                return values()[ordinal() + 1];
            }
        }


        @SneakyThrows
        static Machine work(Machine machine) {
            if (!machine.state.equals(State.THREE)) {
                Thread.sleep(100);
                machine.state = machine.state.step();
            }
            System.out.println(machine);
            return machine;
        }

        @Override
        public String toString() {
            return "Machine{" +
                    "id=" + id +
                    ", state=" + state +
                    '}';
        }
    }

    @SneakyThrows
    @Test
    public void test异步join_立即返回后台继续工作() {

        CompletableFuture.completedFuture(new Machine(0))
                .thenApplyAsync(Machine::work)
                .thenApplyAsync(Machine::work)
                .thenApplyAsync(Machine::work)
                .thenApplyAsync(Machine::work)
                .join();

    }

    @Test
    public void test同步_完成工作后返回() {
        CompletableFuture.completedFuture(new Machine(0))
                .thenApply(Machine::work)
                .thenApply(Machine::work)
                .thenApply(Machine::work)
                .thenApply(Machine::work);
    }

//    CompletableFuture<Integer> future(Integer integer) {
//        return CompletableFuture.completedFuture(integer);
//    }

    @Test
    public void test6() throws ExecutionException, InterruptedException {
        System.out.println(
                CompletableFuture.completedFuture(1).get()
        );
        CompletableFuture.completedFuture(2)
                .thenRunAsync(() -> System.out.println("thenRunAsync"))
                .get();
        CompletableFuture.completedFuture(4)
                .thenAcceptAsync(integer -> System.out.println("" + integer))
                .get()
        ;

        /**
         *  1.有返回值的future 一定要get   没有返回值的future 可以不写get不会立刻执行
         *  2.then是future对象的方法 没有then是静态方法
         *  3.run方法执行 空空函数   accept  方法执行 消费函数  supply执行生产函数  apply 接受function
         */

        CompletableFuture.runAsync(() -> System.out.println("runAsync is static")).get();
        System.out.println(
                CompletableFuture.supplyAsync(() -> 99).get()
        );
        CompletableFuture.completedFuture(4)
                .thenAcceptAsync(System.out::println)
                .get();
        System.out.println(CompletableFuture.completedFuture(5)
                .thenApplyAsync(integer -> integer + 42)
                .get());

        Integer x = CompletableFuture.completedFuture(6)
                .thenComposeAsync(integer -> CompletableFuture.completedFuture(integer + 99))
                .get();   // compose 和apply类似 也是接受一个function ,但compose返回值必须是future
        System.out.println(
                x
        );

        CompletableFuture<Object> f = new CompletableFuture<>();
        f.complete(9);
        System.out.println(f.get());

        System.out.println(
                new CompletableFuture<>().getNow(777)
        );

    }


    @Test
    public void d() {
        ArrayList<LineCoverSqlDTO> lineCoverSqlDTOS = new ArrayList<LineCoverSqlDTO>() {{
            add(new LineCoverSqlDTO("2", "1", 2.0));
            add(new LineCoverSqlDTO("1", "0", 1.0));
            add(new LineCoverSqlDTO("3", "2", 3.0));
            add(new LineCoverSqlDTO("4", "1", 4.0));
        }};

        for (LineCoverSqlDTO sqlDTO : lineCoverSqlDTOS) {
            sqlDTO.setAllSubTotalLength(getAllSubTotalLength(sqlDTO, lineCoverSqlDTOS));
        }
        lineCoverSqlDTOS.forEach(System.out::println);

    }

    private Double getAllSubTotalLength(LineCoverSqlDTO sqlDTO, List<LineCoverSqlDTO> list) {
        return sqlDTO.getTotalLength() +
                list.stream()
                        .filter(record -> record.getParentBureauId().equals(sqlDTO.getBureauId()))
                        .map(record -> getAllSubTotalLength(record, list))
                        .reduce((double) 0, Double::sum);
    }

    @Test
    public void d多少的() {
        String str = "Hello, World!";
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            int position = matcher.start();
            System.out.println("The first letter is at position " + position);
        } else {
            System.out.println("No letter found in the string");
        }
        System.out.println(pattern.matcher("湖北").start());
    }

    @Test
    public void dsd() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        int next = atomicInteger.getAndAccumulate(1, Integer::sum);
        System.out.println("next = " + next);
        System.out.println("atomicInteger = " + atomicInteger);
    }

    @Test
    public void sort() {

        List<Person> people = Arrays.asList(
                new Person("Alice", 20),
                new Person("Bob", 25),
                new Person("Charlie", 18),
                new Person("David", 30),
                new Person("Evelyn", 22),
                new Person("Frankie", 28),
                new Person("Grace", 24),
                new Person("Henry", 21)
        );

        Map<String, List<Person>> groupedByName = people.stream()
                .collect(
                        Collectors.groupingBy(
                                Person::getName,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        list -> {
                                            list.sort(Comparator.comparing(Person::getAge));
                                            return list;
                                        }
                                )
                        )
                );

        System.out.println(groupedByName);
    }

}

