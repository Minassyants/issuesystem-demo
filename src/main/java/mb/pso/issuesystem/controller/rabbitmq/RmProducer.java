package mb.pso.issuesystem.controller.rabbitmq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import mb.pso.issuesystem.entity.im.Message;

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

    public Boolean sendMessage(String rk, Message entity) {
        try {
            // TODO extract to app parameter
            rabbitTemplate.convertAndSend("issuesystem_chat", rk, entity.getId().toString());
        } catch (AmqpException e) {
            return false;
        }

        return true;
    }

}
