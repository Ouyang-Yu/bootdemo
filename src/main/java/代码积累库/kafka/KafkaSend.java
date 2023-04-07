package 代码积累库.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class KafkaSend {

//    spring.kafka.bootstrap-servers=localhost:9092
//    spring.kafka.consumer.group-id=group
    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;

    public void send(String id) {
        kafkaTemplate.send("topic", id);
        System.out.println("send");
    }

}
