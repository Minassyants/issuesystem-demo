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

import io.vertx.core.json.JsonObject;
import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.Notification;
import mb.pso.issuesystem.entity.QNotification;
import mb.pso.issuesystem.entity.enums.NotificationPolicy;
import mb.pso.issuesystem.entity.enums.NotificationType;
import mb.pso.issuesystem.entity.im.Chat;
import mb.pso.issuesystem.entity.im.MessageStatus;
import mb.pso.issuesystem.entity.im.QMessageStatus;
import mb.pso.issuesystem.entity.im.QSurpressedChat;
import mb.pso.issuesystem.entity.im.SuppressedChat;
import mb.pso.issuesystem.entity.utility.EmailNotification;
import mb.pso.issuesystem.exceptions.EmployeeNotFoundException;
import mb.pso.issuesystem.repository.core.EmployeeRepository;
import mb.pso.issuesystem.repository.core.MessageStatusRepository;
import mb.pso.issuesystem.repository.core.NotificationRepository;
import mb.pso.issuesystem.repository.im.SuppressedChatRepository;
import mb.pso.issuesystem.service.impl.core.EmployeeService;
import mb.pso.issuesystem.service.impl.core.NotificationService;
import mb.pso.issuesystem.service.impl.external.EmailNotificationService;
import mb.pso.issuesystem.service.impl.im.MessageStatusService;
import mb.pso.issuesystem.service.impl.im.SuppressedChatService;

//[ ] REFACTOR
@Service
public class ScheduledTasksServiceImpl {

    private final EmailNotificationService emailNotificationServiceImpl;
    private final NotificationService notificationService;
    private final EmployeeService employeeService;
    private final SuppressedChatService suppressedChatService;
    private final MessageStatusService messageStatusService;

    @Scheduled(fixedDelay = 280000)
    @Transactional
    public void sendEmployeeAddedToChat() {
        QNotification notification = QNotification.notification;
        Predicate predicate = notification.type.eq(NotificationType.employeeAddedToChat)
                .and(notification.isSent.eq(false)).and(notification.isRead.eq(false));
        HashMap<Employee, String> notifications = notificationService.gropNotificationsByEmployee(predicate);

        if (!notifications.isEmpty()) {
            emailNotificationServiceImpl.sendEmailNotifications(notifications, "Новые обсуждения",
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
            emailNotificationServiceImpl.sendEmailNotifications(notifications, "Новые обращения",
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
            emailNotificationServiceImpl.sendEmailNotifications(notifications, "Новые сообщения", "issueSystemNotification");
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
        Iterable<MessageStatus> messageStatuses = messageStatusService.findAll(predicate);
        messageStatuses.forEach(t -> {
            chats.add(t.getMessage().getChat());
            t.setNotificationCreated(true);
        });

        QSurpressedChat qSurpressedChat = QSurpressedChat.surpressedChat;

        chats.forEach(t -> {
            for (Employee employee : t.getMembers()) {
                Predicate surpressedPredicate = qSurpressedChat.employee.eq(employee)
                        .and(qSurpressedChat.chatId.eq(t.getId()));
                SuppressedChat surpressedChat = suppressedChatService.findOne(surpressedPredicate)
                        .orElse(new SuppressedChat(employee, t.getId(), false));
                if (!surpressedChat.getIsSuppressed()) {
                    Notification n = new Notification();
                    n.setEmployee(employee);
                    n.setPolicy(NotificationPolicy.INAPP);
                    n.setType(NotificationType.newMessageToChat);
                    n.setText("Новые сообщения в обращении №" + t.getId());
                    n.setRefId(t.getId());
                    n.setIsRead(false);
                    n.setIsSent(false);
                    newNotifications.add(n);
                    surpressedChat.setIsSuppressed(true);
                    suppressedChatService.save(surpressedChat);
                }
            }
        });
        if (!newNotifications.isEmpty()) {
            messageStatusService.saveAll(messageStatuses);
            notificationService.saveAll(newNotifications);
        }

    }

}
