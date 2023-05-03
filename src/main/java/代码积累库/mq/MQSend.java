package 代码积累库.mq;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

import javax.annotation.Resource;

public class MQSend {
// rocketmq.name-server=localhost:9876

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void send(String id) {
//        rocketMQTemplate.convertAndSend("id",id);
        rocketMQTemplate.asyncSend(
                "topicName",
                id,
                new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.println(sendResult.getSendStatus());
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        System.out.println(throwable.toString());
                    }
                }
        );
    }
}
