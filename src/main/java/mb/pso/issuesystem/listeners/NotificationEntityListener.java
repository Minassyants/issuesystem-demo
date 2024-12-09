package mb.pso.issuesystem.listeners;

import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import jakarta.persistence.PostPersist;
import mb.pso.issuesystem.dto.core.NotificationDto;
import mb.pso.issuesystem.dto.ws.SocketMsg;
import mb.pso.issuesystem.entity.core.Notification;
import mb.pso.issuesystem.service.impl.core.DtoMapper;


@Component
public class NotificationEntityListener {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final DtoMapper mapper;

    public NotificationEntityListener(@Lazy SimpMessagingTemplate simpMessagingTemplate, DtoMapper mapper) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.mapper = mapper;
    }

    @PostPersist
    public void handlePostPersist(Notification notification) {
        simpMessagingTemplate.convertAndSendToUser(notification.getEmployee().getsAMAccountName(),
                "topic/notifications/count",
                new SocketMsg(SocketMsg.MsgType.NEWNOTIFICATION, mapper.toDto(notification, NotificationDto.class)));
    }
}
