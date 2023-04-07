package com.ouyang.boot.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class rabbitMQConfig {
    //spring.rabbitmq.host=localhost
    //spring.rabbitmq.port=5672
    @Bean
    public org.springframework.amqp.core.Queue directQueue() {
        return new Queue("direct_queue",true,true,true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(directQueue())
                .to(directExchange())
                .with("direct");
    }


}
