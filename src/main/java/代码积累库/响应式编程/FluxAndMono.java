package 代码积累库.响应式编程;

import co.elastic.clients.elasticsearch._types.TimeUnit;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FluxAndMono {

    @Test
    public void flux() {

        Flux.fromArray(new Integer[]{1, 2, 3}).subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        Flux.range(1, 10).subscribe(System.out::println);
        Flux.interval(Duration.of(10, ChronoUnit.SECONDS)).subscribe(System.out::println);
        // just()：可以指定序列中包含的全部元素。创建出来的 Flux 序列在发布这些元素之后会自动结束。
        // fromArray()：可以从一个数组、Iterable 对象或 Stream 对象中创建 Flux 对象。
        //
        // empty()：创建一个不包含任何元素，只发布结束消息的序列。
        //
        // range(int start, int count)：创建包含从 start 起始的 count 个数量的 Integer 对象的序列。
        //
        // interval(Duration period)和 interval(Duration delay, Duration period)：
        // 创建一个包含了从 0 开始递增的 Long 对象的序列。其中包含的元素按照指定的间隔来发布。
        // 除了间隔时间之外，还可以指定起始元素发布之前的延迟时间。
        //
        // 除了上述的方式之外，还可以使用 generate()、create()方法来自定义流数据的产生过程：
        //
        // generate()

        Flux.generate(sink -> {
            sink.next("Echo");
            //  sink.next("ds");
            sink.complete();
        }).subscribe(System.out::println);
        //    generate 只提供序列中单个消息的产生逻辑(同步通知)
        //    其中的 sink.next()最多只能调用一次，or


        Flux.create(sink -> {
            for (char i = 'a'; i <= 'z'; i++) {
                sink.next(i);
            }
            sink.complete();
        }).subscribe(System.out::print);


    }

    @Test
    public void mono() {

        Mono.fromSupplier(() -> "Mono1").subscribe(System.out::println);
        Mono.justOrEmpty(Optional.of("Mono2")).subscribe(System.out::println);
        Mono.create(monoSink -> monoSink.success("Mono3")).subscribe(System.out::println);
        Mono.empty().subscribe(System.out::println);

        Mono.justOrEmpty(Optional.empty()).subscribe(System.out::println);
        // Mono.empty().filterWhen()
        //         .flatMap()
        //         .map()
        //         .mapNotNull()
        //         .switchIfEmpty(Mono.error(new RuntimeException()));
     }

    @Test
    public void buffer() throws InterruptedException {
        Flux.range(1, 100)
                .buffer(20)
                .subscribe(System.out::println);
        Flux.interval(Duration.ofSeconds(1))
                .buffer(Duration.ofSeconds(1))
                .take(2)// 一秒产生一个,5秒一次buffer,取两个buffer
                .toStream().forEach(System.out::println);
        // .subscribe(System.out::println);不阔以!
        Flux.range(1, 10)
                .bufferUntil(i -> i % 2 == 0)// 满足条件就放到一组buff里
                .subscribe(System.out::println);
        Flux.range(1, 10)
                .bufferWhile(i -> i % 2 == 0)// 只取满足条件的元素,一个元素一个buffer
                .subscribe(System.out::println);
        Flux.range(1, 10)
                .filter(i -> i % 2 == 0)
                .subscribe(System.out::println);

        // 第一个buffer(20)是指凑足20个数字后再进行处理，该语句会输出5组数据(按20分组)
        // 第二个buffer(Duration duration)是指凑足一段时间后的数据再近些处理，这里是5秒钟做一次处理
        // 第三个bufferUtil(Predicate p)是指等到某个元素满足断言(条件)时进行收集处理，这里将会输出[1,2],[3,4]..
        // 第四个bufferWhile(Predicate p)则仅仅是收集满足断言(条件)的元素，这里将会输出2,4,6..这样的偶数


        Flux.just("apple", "orange", "banana", "kiwi", "strawberry")
                .buffer(3)
                .subscribe(System.out::println);
// 可以分片后并行执行
        Flux.just("apple", "orange", "banana", "kiwi", "strawberry")
                .buffer(3)
                .flatMap(x ->
                        Flux.fromIterable(x)
                                .map(String::toUpperCase)
                                .subscribeOn(Schedulers.parallel())
                )
                .subscribe(l -> System.out.println(Thread.currentThread().getName() + "线程执行：" + l));

        Thread.sleep(100000);
    }

    @Test
    public void take() {
        Flux.range(1, 10).take(2).subscribe(System.out::println);
        Flux.range(1, 10)
                .delayElements(Duration.ofSeconds(1))// delay elements
                .take(Duration.ofSeconds(3)) // take first 3 seconds
                .subscribe(System.out::println);
        Flux.range(1, 10).takeLast(2).subscribe(System.out::println);
        Flux.range(1, 10).takeWhile(i -> i < 5).subscribe(System.out::println);
        Flux.range(1, 10).takeUntil(i -> i == 6).subscribe(System.out::println);

    }

    @Test
    public void zip() {
        Flux.just("I", "You")
                .zipWith(Flux.just("Win", "Lose", "yeah"))
                .zipWith(Flux.just("haha", "nono"))
                .subscribe(System.out::println);
        // 第一个zipWith输出的是Tuple对象(不可变的元祖)
        // 第二个zipWith增加了一个BiFunction来实现合并计算，输出的是字符串。
        //
        // 注意到zipWith是分别按照元素在流中的顺序进行两两合并的，合并后的流长度则最短的流为准，遵循最短对齐原则。
        Flux.just("I", "You")
                .zipWith(
                        Flux.just("Win", "Lose"),
                        (s1, s2) -> String.format("%s!%s!", s1, s2)
                )
                .subscribe(System.out::println);
        //[[I,Win],haha]
        // [[You,Lose],nono]
        // I!Win!
        // You!Lose!


        Flux<String> characterFlux = Flux
                .just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux
                .just("Lasagna", "Lollipops", "Apples");

// 压缩成自定义对象
        Flux.zip(characterFlux, foodFlux, (c, f) -> c + " eats " + f)
                .subscribe(System.out::println);

    /**
     * 执行结果：
     * Garfield eats Lasagna
     * Kojak eats Lollipops
     * Barbossa eats Apples
     */
    }

    @Test
    public void combine() {
        Flux.combineLatest(Arrays::toString,
                        Flux.interval(Duration.of(100, ChronoUnit.MILLIS)).take(2),
                        Flux.interval(Duration.of(100, ChronoUnit.MILLIS)).take(2)
                ).toStream()
                .forEach(System.out::println);

    }

    @Test
    public void merge() {
        // merge后的元素是按产生时间排序的，而mergeSequential 则是按整个流被订阅的时间来排序，如下面的代码：
        Flux.merge(
                        Flux.interval(Duration.of(100, ChronoUnit.MILLIS)).take(2),
                        Flux.interval(Duration.of(100, ChronoUnit.MILLIS)).take(2)
                )
                .toStream()
                .forEach(System.out::println); // 0011
        System.out.println("---");
        Flux.mergeSequential(
                        Flux.interval(Duration.of(100, ChronoUnit.MILLIS)).take(2),
                        Flux.interval(Duration.of(100, ChronoUnit.MILLIS)).take(2)
                )
                .toStream()
                .forEach(System.out::println);// 0101

    }

    @Test
    public void flatMap() {
        Flux.just(1, 2)
                .flatMap(x -> Flux.interval(
                        Duration.of(x * 10, ChronoUnit.MILLIS),
                        Duration.of(10, ChronoUnit.MILLIS)).take(x))
                .toStream()
                .forEach(System.out::println);// 001
        System.out.println("***********");

        Flux.just(1, 2)
                .flatMapSequential(x -> Flux.interval(
                        Duration.of(x * 10, ChronoUnit.MILLIS),
                        Duration.of(10, ChronoUnit.MILLIS)).take(x))
                .toStream()
                .forEach(System.out::println);// 001

        // 使⽤flatMap()⽅法和subscribeOn()⽅法
        // flatMap并不像map操作那样简单地将⼀个对象转换到另⼀个对象，⽽是将对象转换为新的Mono或Flux。
        // 结果形成的Mono或Flux会扁平化为新的Flux。
        // 当与subscribeOn()⽅法结合使⽤时，flatMap操作可以释放Reactor反应式的异步能⼒。
        Flux.just("Michael", "Scottie Pippen", "Steve Kerr Ob")
                .flatMap(n ->
                        Mono.just(n)
                                .map(p -> {
                                    return Arrays.stream(p.split("\\s")).collect(Collectors.toList()); // 将String转为Integer
                                })
                                .subscribeOn(Schedulers.parallel()) // 定义异步
                ).subscribe(System.out::println);


    }

    @Test
    public void reduce() {
        Flux.range(1, 100).reduce((x, y) -> x + y)
                .subscribe(System.out::println);
        Flux.range(1, 100).reduceWith(() -> 100, (x, y) -> x + y)
                .subscribe(System.out::println);

    }

    @Test
    public void scheduler() {


        // single	单一可复用的线程
        // immediate	采用当前线程
        // elastic	弹性可复用的线程池(IO型)
        //  arallel	并行操作优化的线程池(CPU计算型)
        // timer	支持任务调度的线程池
        // fromExecutorService	自定义线程池

        Flux.create(sink -> {
                    sink.next(Thread.currentThread().getName());
                    sink.complete();
                })
                .publishOn(Schedulers.single())
                .map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
                .publishOn(Schedulers.immediate())
                .map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
                .subscribeOn(Schedulers.parallel())
                .toStream()
                .forEach(System.out::println);
        // 在这段代码中，使用 publishOn 指定了流发布的调度器，subscribeOn 则指定的是流产生的调度器。
        //
        // 首先是parallel调度器进行流数据的生成，接着使用一个single单线程调度器进行发布，此时经过第一个map转换为另一个Flux流，其中的消息叠加了当前线程的名称。最后进入的是一个elastic弹性调度器，再次进行一次同样的map转换。


    }

    @Test
    public void exception() {


        Flux.just(1, 2)// 将正常消息和错误消息分别打印
                .concatWith(Mono.error(new IllegalStateException()))
                .subscribe(System.out::println, System.err::println);


        Flux.just(1, 2)// 当产生错误时默认返回0
                .concatWith(Mono.error(new IllegalStateException()))
                .onErrorReturn(0)
                .subscribe(System.out::println);


        Flux.just(1, 2)// 自定义异常时的处理
                .concatWith(Mono.error(new IllegalArgumentException()))
                .onErrorResume(e -> {
                    if (e instanceof IllegalStateException) {
                        return Mono.just(0);
                    } else if (e instanceof IllegalArgumentException) {
                        return Mono.just(-1);
                    }
                    return Mono.empty();
                })
                .subscribe(System.out::println);


        Flux.just(1, 2) // 当产生错误时重试
                .concatWith(Mono.error(new IllegalStateException()))
                .retry(1)
                .subscribe(System.out::println);
        // 这里的retry(1)表示最多重试1次，而且重试将从订阅的位置开始重新发送流事件
    }

    @SneakyThrows
    @Test
    public void sda() {
        Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry")
                .subscribe(
                        f -> System.out.println("Here's some fruit: " + f)
                );

        String[] fruits = new String[]{
                "Apple", "Orange", "Grape", "Banana", "Strawberry"};
        Flux.fromArray(fruits);
        Flux.fromIterable(Arrays.asList(fruits)); // 集合

        Flux.fromStream(Arrays.stream(fruits)); // stream流


        Flux.range(1, 5).subscribe(
                f -> System.out.println("data is :" + f)
        );
        Flux.interval(Duration.ofSeconds(1))
                .take(5)
                .subscribe(f -> System.out.println("data2 is :" + f));


    }

    @Test
    public void mergeWith() {
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa")
                .delayElements(Duration.ofMillis(500)); // 每500毫秒发布⼀个数据

        Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples")
                .delaySubscription(Duration.ofMillis(250)) // 订阅后250毫秒后开始发布数据
                .delayElements(Duration.ofMillis(500)); // 每500毫秒发布⼀个数据

// 使⽤mergeWith()⽅法，将两个Flux合并，合并过后的Flux数据项发布顺序与源Flux的发布时间⼀致
// Garfield Lasagna Kojak Lollipops Barbossa Apples

        characterFlux.mergeWith(foodFlux)
                .subscribe(System.out::println);


    }

    @Test
    public void first() throws InterruptedException {
        Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth")
                .delaySubscription(Duration.ofMillis(100)); // 延迟100ms
        Flux<String> fastFlux = Flux.just("hare", "cheetah", "squirrel");
// 选择第⼀个反应式类型进⾏发布
        Flux.first(slowFlux, fastFlux).subscribe(System.out::println);
// 阻塞，等待结果
        Thread.sleep(1000);
/**
 * 执行结果：
 * hare
 * cheetah
 * squirrel
 */
    }


    @Test
    public void collectMap() {
        // 将第一个字符作为Map的key
        Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo")
                .collectMap(a -> a.charAt(0))
                .subscribe(System.out::println);
        // 元素的key相同,只算第一次(排序后
    /**
     * 执行结果：
     * {a=aardvark, e=eagle, k=kangaroo}
     */

    }

    @Test
    public void allAndAny() {
        Flux.just("aardvark", "elephant", "koala", "eagle", "kangaroo")
                .all(a -> a.contains("a"))
                // .any(a -> a.contains("a"))
                .subscribe(System.out::println);

    }

    @Test
    public void subscribe() {
        Flux<String> stringFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");


        Flux<Integer> ints3 = Flux.range(1, 4);
        ints3.subscribe(
                System.out::println,
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done")
        );

        stringFlux.subscribe(new Subscriber<String>() {
            // 保存订阅关系, 需要用它来给发布者响应
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("订阅者开始订阅");
                this.subscription = subscription;
                // 请求一个数据
                this.subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                System.out.println("订阅者开始处理数据" + item);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 处理完调用request再请求一个数据
                this.subscription.request(1);
                // 或者 已经达到了目标, 调用cancel告诉发布者不再接受数据了
                // this.subscription.cancel();
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                subscription.cancel();
            }


            @Override
            public void onComplete() {
                // 全部数据处理完了(发布者关闭了)
                System.out.println("订阅者处理完了!");
            }
        });
    }

    @Test
    public void doOnNext() {
        Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry")
                .doFirst(() -> System.out.println("first run"))
                .doOnNext(t -> System.out.println("发布者1处理数据：" + t))
                // .doOnNext(t -> System.out.println("发布者2处理数据：" + t))
                .doOnRequest(t -> System.out.println("request"))
                .doOnComplete(() -> System.out.println("completed"))
                .doFinally(it -> System.out.println("finally "))
                .doOnError(Throwable::printStackTrace)
                .then(Mono.defer(() -> Mono.just("我完成了")))
                .subscribe(t -> System.out.println("订阅者处理数据：" + t));

        // attention! 只有链式调用，才会触发发布者的doOnNext事件。
    }

}
