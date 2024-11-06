package mb.pso.issuesystem.controller.im;

import java.util.Optional;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import mb.pso.issuesystem.entity.Notification;
import mb.pso.issuesystem.exceptions.IllegalActionException;
import mb.pso.issuesystem.repository.NotificationRepository;

@Controller
public class NotificationController {

    // FIXME extract to service
    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @MessageMapping("/notifications/{id}")
    public void markAsRead(@DestinationVariable Integer id) {
        Notification notification = notificationRepository.findById(id).orElse(null);
        if (notification == null) {
            // FIXME выделить отдельный класс для исключения
            throw new IllegalActionException("notification not found");
        }
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }
}