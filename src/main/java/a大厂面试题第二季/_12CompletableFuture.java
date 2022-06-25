package a大厂面试题第二季;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-06-22 17:32
 */
public class _12CompletableFuture {
    public static void main(String[] args) {
        CompletableFuture<String> stringCompletableFuture ;
        stringCompletableFuture=CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "asd";
            }
        });

        stringCompletableFuture.complete("xx");
        String join = stringCompletableFuture.join();

        System.out.println(join);

    }





}
