package mb.pso.issuesystem.service.impl;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.Notification;
import mb.pso.issuesystem.entity.enums.NotificationPolicy;
import mb.pso.issuesystem.entity.enums.NotificationType;
import mb.pso.issuesystem.entity.im.Chat;
import mb.pso.issuesystem.entity.im.Message;
import mb.pso.issuesystem.entity.im.MessageStatus;
import mb.pso.issuesystem.entity.im.QMessage;
import mb.pso.issuesystem.entity.im.QMessageStatus;
import mb.pso.issuesystem.entity.im.QSuppressedChat;
import mb.pso.issuesystem.exceptions.ChatNotFoundException;
import mb.pso.issuesystem.exceptions.EmployeeNotFoundException;
import mb.pso.issuesystem.exceptions.IllegalActionException;
import mb.pso.issuesystem.exceptions.MessageNotFoundException;
import mb.pso.issuesystem.repository.EmployeeRepository;
import mb.pso.issuesystem.repository.MessageStatusRepository;
import mb.pso.issuesystem.repository.NotificationRepository;
import mb.pso.issuesystem.repository.im.ChatRepository;
import mb.pso.issuesystem.repository.im.MessageRepository;
import mb.pso.issuesystem.repository.im.SuppressedChatRepository;

//[ ] REFACTOR
@Service
// [ ] extract interface
public class ImServiceImpl {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final EmployeeRepository employeeRepository;
    private final MessageStatusRepository messageStatusRepository;
    private final NotificationRepository notificationRepository;
    private final SuppressedChatRepository suppressedChatRepository;

    public ImServiceImpl(MessageRepository messageRepository, ChatRepository chatRepository,
            EmployeeRepository employeeRepository,
            MessageStatusRepository messageStatusRepository, NotificationRepository notificationRepository,
            SuppressedChatRepository suppressedChatRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.employeeRepository = employeeRepository;
        this.messageStatusRepository = messageStatusRepository;
        this.notificationRepository = notificationRepository;
        this.suppressedChatRepository = suppressedChatRepository;
    }

    public Message markAsRead(Integer messageId, String displayName) {

        Message msg = messageRepository.findById(messageId).orElse(null);
        if (msg == null)
            throw new MessageNotFoundException(messageId.toString());

        QMessageStatus messageStatus = QMessageStatus.messageStatus;
        Predicate predicate = messageStatus.message.id.eq(messageId)
                .and(messageStatus.employee.displayName.eq(displayName));
        MessageStatus status = messageStatusRepository.findOne(predicate).orElse(null);

        if (status == null) {
            Employee employee = employeeRepository.findById(displayName).orElse(null);
            if (employee == null)
                throw new EmployeeNotFoundException(displayName);
            status = new MessageStatus();
            status.setEmployee(employee);
            status.setMessage(msg);
        }

        status.setStatus(1);
        messageStatusRepository.save(status);
        msg.addToStatuses(status);
        msg = messageRepository.save(msg);

        return msg;
    }

    @Transactional
    public Employee deleteMemberFromChat(Integer chatId, Employee employee) {
        Chat chat = chatRepository.findById(chatId).orElse(null);

        if (chat == null)
            throw new ChatNotFoundException(chatId.toString());

        if (chat.getIsClosed())
            throw new IllegalActionException("Chat is already closed.");

        Employee foundEmployee = employeeRepository.findById(employee.getDisplayName()).orElse(null);
        if (foundEmployee == null)
            throw new EmployeeNotFoundException(employee.getDisplayName());
        // FIXME вынести в отдельный метод класса chat
        if (chat.getMembers().removeIf(t -> t.equals(foundEmployee)))
            chatRepository.save(chat);

        return foundEmployee;
    }

    @Transactional
    public Employee addMemberToChat(Integer chatId, Employee employee) {
        Chat chat = chatRepository.findById(chatId).orElse(null);

        if (chat == null)
            throw new ChatNotFoundException(chatId.toString());

        if (chat.getIsClosed())
            throw new IllegalActionException("Chat is already closed.");

        employee = employeeRepository.findOne(Example.of(employee)).orElse(employee);
        employee = employeeRepository.save(employee);
        chat.addToMembers(employee);
        chat = chatRepository.save(chat);

        Notification notification = new Notification();
        notification.setEmployee(employee);
        notification.setPolicy(NotificationPolicy.BOTH);
        notification.setType(NotificationType.employeeAddedToChat);
        notification.setIsRead(false);
        notification.setIsSent(false);
        notification.setText("Обращение № " + chatId.toString());
        notification.setRefId(chat.getId());
        notificationRepository.save(notification);

        return employee;
    }

    public Chat getChatById(Integer id) {
        return chatRepository.findById(id).orElseGet(null);
    }

    // [x] getMessagesByChatId
    public List<Message> getMessagesByChatId(Integer id) {
        return messageRepository.findByChatId(id);
    }

    public Page<Message> getMessagesByChatIdPageable(Integer id, Pageable pageable) {
        QMessage message = QMessage.message;
        Predicate predicate = message.chat.id.eq(id);
        return messageRepository.findAll(predicate, pageable);
    }

    // [x] sendMessage
    // [ ] добавить индикацию успешности отправки сообщения
    @Transactional
    public Message sendMessage(Message message) {
        Chat chat = chatRepository.findById(message.getChat().getId()).orElse(null);

        if (chat == null)
            throw new ChatNotFoundException(message.getChat().getId().toString());

        if (chat.getIsClosed())
            throw new IllegalActionException("Chat is already closed.");

        Employee author = employeeRepository.findOne(Example.of(message.getAuthor())).orElse(message.getAuthor());
        author = employeeRepository.save(author);
        message.setAuthor(author);
        for (Employee employee : chat.getMembers()) {
            MessageStatus status = new MessageStatus();
            status.setEmployee(employee);
            status.setMessage(message);
            status.setStatus(0);
            status = messageStatusRepository.save(status);
            message.addToStatuses(status);
        }
        Message msg = messageRepository.save(message);

        return msg;

    }

    public void unsuppressChat(Integer chatId, String displayName) {
        QSuppressedChat suppressedChat = QSuppressedChat.suppressedChat;
        Predicate predicate = suppressedChat.employee.displayName.eq(displayName).and(suppressedChat.chatId.eq(chatId));
        suppressedChatRepository.findOne(predicate).ifPresentOrElse(t -> {
            t.setIsSuppressed(false);
            suppressedChatRepository.save(t);
        }, () -> {
        });

    }

}
