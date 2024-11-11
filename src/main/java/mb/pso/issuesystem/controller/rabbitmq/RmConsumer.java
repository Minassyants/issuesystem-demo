// package mb.pso.issuesystem.controller.rabbitmq;

// import org.springframework.amqp.rabbit.annotation.Exchange;
// import org.springframework.amqp.rabbit.annotation.Queue;
// import org.springframework.amqp.rabbit.annotation.QueueBinding;
// import org.springframework.amqp.rabbit.annotation.RabbitListener;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import mb.pso.issuesystem.service.notifications.impl.EmailNotificationServiceImpl;

// //@Component
// public class RmConsumer {
//     private final ObjectMapper objectMapper;
//     private final EmailNotificationServiceImpl emailNotificationServiceImpl;

//     public RmConsumer(ObjectMapper objectMapper, EmailNotificationServiceImpl emailNotificationServiceImpl) {
//         this.objectMapper = objectMapper;
//         this.emailNotificationServiceImpl = emailNotificationServiceImpl;
//     }

//     @RabbitListener(bindings = @QueueBinding(value = @Queue(name = "Pso.EmailNotifications"), exchange = @Exchange(value = "default", type = "topic"), key = "*.newIssue"))
//     public void sendEmailNotification(String message) {
//         // try {
//         //     Issue issue = objectMapper.readValue(message, Issue.class);
//         //     EmailNotification emailNotification = new EmailNotification(new String[] { "\"" + issue.getClient().getEmail() + "\"" },
//         //             "123", "123");
//         //     emailNotificationServiceImpl.sendEmail(emailNotification);
//         // } catch (Exception e) {
//         //     System.out.println("Error :" + e.getMessage());
//         // }
//     }
// }
