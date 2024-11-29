package mb.pso.issuesystem.listeners;

import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import jakarta.persistence.PostPersist;
import mb.pso.issuesystem.dto.webSocket.SocketMsg;
import mb.pso.issuesystem.entity.Notification;
//[ ] REFACTOR
@Component
public class NotificationEntityListener {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationEntityListener(@Lazy SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostPersist
    public void handlePostPersist(Notification notification) {
        simpMessagingTemplate.convertAndSendToUser(notification.getEmployee().getsAMAccountName(),
                "topic/notifications/count", new SocketMsg(SocketMsg.MsgType.NEWNOTIFICATION, notification));
    }
}
