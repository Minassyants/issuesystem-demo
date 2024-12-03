package mb.pso.issuesystem.controller.im;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import mb.pso.issuesystem.service.impl.core.NotificationService;

//[x] REFACTOR
@Controller
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @MessageMapping("/notifications/{id}")
    public void markAsRead(@DestinationVariable Integer id) {
        notificationService.markAsRead(id);
    }
}