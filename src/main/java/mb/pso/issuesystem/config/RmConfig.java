package mb.pso.issuesystem.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//[ ] REFACTOR
@Configuration
public class RmConfig {
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("default");
    }
}
