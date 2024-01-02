package 代码积累库.kafka;

import org.springframework.stereotype.Component;

/**
 * 金刚位跳转策略配置
 */
@Component
public class DiamondJumpContext {

    // private final Map<Integer, DiamondJumpType> map = new HashMap<>();
    //
    // /**
    //  * 由spring自动注入DiamondJumpType子类
    //  *
    //  * @param diamondJumpTypes 金刚位跳转类型集合
    //  */
    // public DiamondJumpContext(List<DiamondJumpType> diamondJumpTypes) {
    //     for (DiamondJumpType diamondJumpType : diamondJumpTypes) {
    //         map.put(diamondJumpType.getType(), diamondJumpType);
    //     }
    // }
    //
    // public DiamondJumpType getInstance(Integer jumpType) {
    //     return map.get(jumpType);
    // }
}
