package mb.pso.issuesystem.controller.rabbitmq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RmProducer {
    private final RabbitTemplate rabbitTemplate;

    public RmProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public Boolean write(String rk, String entity) {
        try {
            rabbitTemplate.convertAndSend("default", rk, entity);
        } catch (AmqpException e) {
            return false;
        }

        return true;
    }

}
