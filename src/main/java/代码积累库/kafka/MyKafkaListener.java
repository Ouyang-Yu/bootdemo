package 代码积累库.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jetbrains.annotations.NotNull;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaListener {

    @KafkaListener(topics = "{topic}")
    public void onMessage(@NotNull ConsumerRecord<String, String> record) {

        System.out.println("send"+record.value());
    }






}
