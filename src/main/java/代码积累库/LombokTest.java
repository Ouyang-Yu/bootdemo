package 代码积累库;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.experimental.Accessors;

import java.util.List;

// @RequiredArgsConstructor
// 所有带有 @NonNull 注解的或者带有 final 修饰的成员变量生成对应的构造方法。
@Accessors(chain = true)
@Data
//* @Value注解和@Data类似，区别在于它会把所有成员变量默认定义为private final修饰，并且不会生成set方法。
@Builder
public class LombokTest {
    @Builder.Default// 在Builder中实现默认值
    private String name = "aa";
    @Singular// 在Builder中添加集合的单个元素
    private List<String> hobbies;

    public static void main(String[] args) {
        LombokTest build = LombokTest.builder()
                .hobby("1")
                .hobby("2")
                .build();
        System.out.println(build);

    }
}

