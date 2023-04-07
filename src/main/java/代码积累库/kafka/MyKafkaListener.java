package 代码积累库.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;

@Component
public class MyKafkaListener {

    @KafkaListener(topics = "{topic}")
    public void onMessage(@NotNull ConsumerRecord<String, String> record) {

        System.out.println("send"+record.value());
    }





}
