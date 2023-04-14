package 代码积累库.mq.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Lintenner {

    @Resource
    private AmqpTemplate amqpTemplate;


    public void send(String id) {
        amqpTemplate.convertAndSend("directExchange", "direct", id);
        //交换机   路由key  要发送的数据
    }



    @RabbitListener(queues = {"direct_queue"})
    public void receive(String id) {
        System.out.println("already receive");
    }
}
