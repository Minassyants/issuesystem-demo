package mb.pso.issuesystem.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import io.vertx.core.json.JsonObject;
import mb.pso.issuesystem.dto.webSocket.SocketMsg;
import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.Notification;
import mb.pso.issuesystem.entity.QNotification;
import mb.pso.issuesystem.entity.enums.NotificationPolicy;
import mb.pso.issuesystem.entity.enums.NotificationType;
import mb.pso.issuesystem.entity.im.Chat;
import mb.pso.issuesystem.entity.im.MessageStatus;
import mb.pso.issuesystem.entity.im.QMessageStatus;
import mb.pso.issuesystem.entity.im.QSurpressedChat;
import mb.pso.issuesystem.entity.im.SurpressedChat;
import mb.pso.issuesystem.entity.utility.EmailNotification;
import mb.pso.issuesystem.exceptions.EmployeeNotFoundException;
import mb.pso.issuesystem.repository.EmployeeRepository;
import mb.pso.issuesystem.repository.MessageStatusRepository;
import mb.pso.issuesystem.repository.NotificationRepository;
import mb.pso.issuesystem.repository.im.SurpressedChatRepository;
import mb.pso.issuesystem.service.notifications.impl.EmailNotificationServiceImpl;

@Service
public class ScheduledTasksServiceImpl {

    private SimpUserRegistry simpUserRegistry;
    private SimpMessagingTemplate simpMessagingTemplate;
    private NotificationRepository notificationRepository;
    private EmployeeRepository employeeRepository;
    private EmailNotificationServiceImpl emailNotificationServiceImpl;
    private MessageStatusRepository messageStatusRepository;
    private SurpressedChatRepository surpressedChatRepository;

    public ScheduledTasksServiceImpl(SimpUserRegistry simpUserRegistry, SimpMessagingTemplate simpMessagingTemplate,
            NotificationRepository notificationRepository, EmployeeRepository employeeRepository,
            MessageStatusRepository messageStatusRepository,
            EmailNotificationServiceImpl emailNotificationServiceImpl,
            SurpressedChatRepository surpressedChatRepository) {
        this.simpUserRegistry = simpUserRegistry;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.notificationRepository = notificationRepository;
        this.employeeRepository = employeeRepository;
        this.emailNotificationServiceImpl = emailNotificationServiceImpl;
        this.messageStatusRepository = messageStatusRepository;
        this.surpressedChatRepository = surpressedChatRepository;

    }

    @Scheduled(fixedDelay = 280000)
    public void sendEmployeeAddedToChat() {
        QNotification notification = QNotification.notification;
        Predicate predicate = notification.type.eq(NotificationType.employeeAddedToChat)
                .and(notification.isSent.eq(false)).and(notification.isRead.eq(false));
        HashMap<Employee, String> texts = new HashMap<>();
        Iterable<Notification> n = notificationRepository.findAll(predicate);
        for (Notification t : n) {
            String text = texts.get(t.getEmployee());
            if (text == null)
                text = "";
            text += t.getText() + "\n";
            texts.put(t.getEmployee(), text);
            t.setIsSent(true);
        }

        if (!texts.isEmpty()) {
            texts.forEach((t, u) -> {
                EmailNotification emailNotification = new EmailNotification("bsk1c",
                        t.getMail(),
                        "issueSystemNotification", "Новые обсуждения");
                JsonObject body = new JsonObject();
                body.put("title", "Новые обсуждения");
                body.put("text", u);
                emailNotification.setBody(body);
                emailNotificationServiceImpl.sendEmail(emailNotification);
            });
            notificationRepository.saveAll(n);
        }

    }

    @Scheduled(fixedDelay = 300000)
    @Transactional
    public void sendNewIssueNotification() {
        String text = "";
        Employee admin = employeeRepository.findById("admin").orElse(null);
        if (admin == null)
            throw new EmployeeNotFoundException("admin");
        QNotification notification = QNotification.notification;
        Predicate predicate = notification.isSent.eq(false).and(notification.isRead.eq(false))
                .and(notification.type.eq(NotificationType.newIssue));
        Iterable<Notification> notifications = notificationRepository.findAll(predicate);
        for (Notification n : notifications) {
            text += n.getText() + "\n";
            n.setIsSent(true);
        }
        if (!text.isBlank()) {
            EmailNotification emailNotification = new EmailNotification("bsk1c",
                    admin.getMail(),
                    "issueSystemNotification", "Новые обращения");
            JsonObject body = new JsonObject();
            body.put("title", "Новые обращения");
            body.put("text", text);
            emailNotification.setBody(body);
            emailNotificationServiceImpl.sendEmail(emailNotification);
            notificationRepository.saveAll(notifications);
        }

    }

    @Scheduled(fixedDelay = 150000)
    @Transactional
    public void sendNewMessageNotification() {
        QNotification notification = QNotification.notification;
        HashMap<Employee, String> texts = new HashMap<>();
        Predicate predicate = notification.isSent.eq(false).and(notification.isRead.eq(false))
                .and(notification.type.eq(NotificationType.newMessageToChat));
        Iterable<Notification> n = notificationRepository.findAll(predicate);
        for (Notification t : n) {
            String text = texts.get(t.getEmployee());
            if (text == null)
                text = "";
            text += t.getText() + "\n";
            texts.put(t.getEmployee(), text);
            t.setIsSent(true);
        }
        if (!texts.isEmpty()) {
            texts.forEach((t, u) -> {
                EmailNotification emailNotification = new EmailNotification("bsk1c",
                        t.getMail(),
                        "issueSystemNotification", "Новые сообщения");
                JsonObject body = new JsonObject();
                body.put("title", "Новые сообщения");
                body.put("text", u);
                emailNotification.setBody(body);
                emailNotificationServiceImpl.sendEmail(emailNotification);
            });
            notificationRepository.saveAll(n);
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
        Iterable<MessageStatus> messageStatuses = messageStatusRepository.findAll(predicate);
        messageStatuses.forEach(t -> {
            chats.add(t.getMessage().getChat());
            t.setNotificationCreated(true);
        });

        QSurpressedChat qSurpressedChat = QSurpressedChat.surpressedChat;

        chats.forEach(t -> {
            for (Employee employee : t.getMembers()) {
                Predicate surpressedPredicate = qSurpressedChat.employee.eq(employee)
                        .and(qSurpressedChat.chatId.eq(t.getId()));
                SurpressedChat surpressedChat = surpressedChatRepository.findOne(surpressedPredicate)
                        .orElse(new SurpressedChat(employee, t.getId(), false));
                if (!surpressedChat.getIsSurpressed()) {
                    Notification n = new Notification();
                    n.setEmployee(employee);
                    n.setPolicy(NotificationPolicy.INAPP);
                    n.setType(NotificationType.newMessageToChat);
                    n.setText("Новые сообщения в обращении №" + t.getId());
                    n.setRefId(t.getId());
                    n.setIsRead(false);
                    n.setIsSent(false);
                    newNotifications.add(n);
                    surpressedChat.setIsSurpressed(true);
                    surpressedChatRepository.save(surpressedChat);
                }
            }
        });
        if (!newNotifications.isEmpty()) {
            messageStatusRepository.saveAll(messageStatuses);
            notificationRepository.saveAll(newNotifications);
        }

    }

    // // @Scheduled(fixedDelay = 7000)
    // public void test() {
    // Set<SimpUser> a = simpUserRegistry.getUsers();

    // System.out.println("================");
    // for (SimpUser simpUser : a) {
    // simpMessagingTemplate.convertAndSendToUser(simpUser.getName(),
    // "/topic/errors",
    // new SocketMsg(SocketMsg.MsgType.ERROR, simpUser.getName()));
    // }
    // System.out.println("================");
    // }
}
