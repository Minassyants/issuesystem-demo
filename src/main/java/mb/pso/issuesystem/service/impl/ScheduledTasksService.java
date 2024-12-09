package mb.pso.issuesystem.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.QNotification;
import mb.pso.issuesystem.entity.core.Employee;
import mb.pso.issuesystem.entity.core.Notification;
import mb.pso.issuesystem.entity.enums.NotificationPolicy;
import mb.pso.issuesystem.entity.enums.NotificationType;
import mb.pso.issuesystem.entity.im.Chat;
import mb.pso.issuesystem.entity.im.MessageStatus;
import mb.pso.issuesystem.entity.im.QMessageStatus;
import mb.pso.issuesystem.entity.im.SuppressedChat;
import mb.pso.issuesystem.service.impl.core.NotificationService;
import mb.pso.issuesystem.service.impl.external.EmailNotificationService;
import mb.pso.issuesystem.service.impl.im.MessageStatusService;
import mb.pso.issuesystem.service.impl.im.SuppressedChatService;


@Service
public class ScheduledTasksService {

    private final EmailNotificationService emailNotificationService;
    private final NotificationService notificationService;
    private final SuppressedChatService suppressedChatService;
    private final MessageStatusService messageStatusService;

    public ScheduledTasksService(EmailNotificationService emailNotificationService,
            NotificationService notificationService, SuppressedChatService suppressedChatService,
            MessageStatusService messageStatusService) {
        this.emailNotificationService = emailNotificationService;
        this.notificationService = notificationService;
        this.suppressedChatService = suppressedChatService;
        this.messageStatusService = messageStatusService;
    }

    @Scheduled(fixedDelay = 280000)
    @Transactional
    public void sendEmployeeAddedToChat() {
        QNotification notification = QNotification.notification;
        Predicate predicate = notification.type.eq(NotificationType.employeeAddedToChat)
                .and(notification.isSent.eq(false)).and(notification.isRead.eq(false));
        HashMap<Employee, String> notifications = notificationService.gropNotificationsByEmployee(predicate);

        if (!notifications.isEmpty()) {
            emailNotificationService.sendEmailNotifications(notifications, "Новые обсуждения",
                    "issueSystemNotification");
        }
    }

    @Scheduled(fixedDelay = 300000)
    @Transactional
    public void sendNewIssueNotification() {

        QNotification notification = QNotification.notification;
        Predicate predicate = notification.isSent.eq(false).and(notification.isRead.eq(false))
                .and(notification.type.eq(NotificationType.newIssue));

        HashMap<Employee, String> notifications = notificationService.gropNotificationsByEmployee(predicate);

        if (!notifications.isEmpty()) {
            emailNotificationService.sendEmailNotifications(notifications, "Новые обращения",
                    "issueSystemNotification");
        }

    }

    @Scheduled(fixedDelay = 150000)
    @Transactional
    public void sendNewMessageNotification() {
        QNotification notification = QNotification.notification;
        Predicate predicate = notification.isSent.eq(false).and(notification.isRead.eq(false))
                .and(notification.type.eq(NotificationType.newMessageToChat));

        HashMap<Employee, String> notifications = notificationService.gropNotificationsByEmployee(predicate);

        if (!notifications.isEmpty()) {
            emailNotificationService.sendEmailNotifications(notifications, "Новые сообщения",
                    "issueSystemNotification");
        }
    }

    @Scheduled(fixedDelay = 120000)
    @Transactional
    public void createNewMessageNotifications() {
        List<Notification> newNotifications = new ArrayList<>();
        Set<Chat> chats = new HashSet<Chat>();

        QMessageStatus messageStatus = QMessageStatus.messageStatus;
        Predicate predicate = messageStatus.status.eq(0).and(messageStatus.notificationCreated.eq(false))
                .and(messageStatus.message.createdAt.lt(LocalDateTime.now().minusMinutes(2)));

        Iterable<MessageStatus> messageStatuses = messageStatusService.getAll(predicate);
        messageStatuses.forEach(status -> {
            chats.add(status.getMessage().getChat());
            status.setNotificationCreated(true);
        });

        chats.forEach(chat -> {
            for (Employee employee : chat.getMembers()) {
                SuppressedChat surpressedChat = suppressedChatService.getOrCreate(employee, chat.getId());

                if (!surpressedChat.getIsSuppressed()) {

                    Notification notification = new Notification(NotificationType.newMessageToChat,
                            NotificationPolicy.INAPP, employee, "Новые сообщения в обращении №" + chat.getId(),
                            chat.getId());

                    newNotifications.add(notification);
                    suppressedChatService.suppressChat(surpressedChat);
                }
            }
        });
        if (!newNotifications.isEmpty()) {
            messageStatusService.saveAll(messageStatuses);
            notificationService.saveAll(newNotifications);
        }

    }

}
