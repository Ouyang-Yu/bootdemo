package 代码积累库.mq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "",topic = "")
public class MessageListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String o) {

        System.out.println("done" + o);
    }
}
