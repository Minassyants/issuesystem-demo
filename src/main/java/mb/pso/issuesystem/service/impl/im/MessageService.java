package mb.pso.issuesystem.service.impl.im;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.core.Employee;
import mb.pso.issuesystem.entity.im.Chat;
import mb.pso.issuesystem.entity.im.Message;
import mb.pso.issuesystem.entity.im.MessageStatus;
import mb.pso.issuesystem.entity.im.QMessage;
import mb.pso.issuesystem.repository.core.CombinedRepository;
import mb.pso.issuesystem.repository.im.MessageRepository;
import mb.pso.issuesystem.service.AbstractCrudService;
import mb.pso.issuesystem.service.impl.core.EmployeeService;


/**
 * Service class for managing messages within chats.
 * Extends {@link AbstractCrudService} to provide basic CRUD operations
 * and adds custom methods for sending messages, marking them as read,
 * and retrieving messages by chat.
 */
@Service
public class MessageService extends AbstractCrudService<Message, Integer> {

    private final MessageRepository repository;
    private final MessageStatusService messageStatusService;
    private final ChatService chatService;
    private final EmployeeService employeeService;

    public MessageService(MessageRepository repository, MessageStatusService messageStatusService,
            ChatService chatService, EmployeeService employeeService) {
        this.repository = repository;
        this.messageStatusService = messageStatusService;
        this.chatService = chatService;
        this.employeeService = employeeService;
    }

    @Override
    protected Integer getId(Message entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<Message, Integer> getRepository() {
        return repository;
    }

    /**
     * Marks a message as read by a specific user.
     *
     * @param messageId   the ID of the message to be marked as read
     * @param displayName the display name of the user marking the message as read
     * @return the updated {@link Message}
     */
    public Message markAsRead(Integer messageId, String displayName) {

        Message message = getOrThrow(messageId);

        MessageStatus status = messageStatusService.findOneOrThrow(messageId, displayName);

        status.setStatus(1);
        status = messageStatusService.update(status);
        message.addToStatuses(status);

        return update(message);
    }

    /**
     * Sends a new message to a chat and updates message statuses for all chat
     * members.
     *
     * @param message the {@link Message} to be sent
     * @return the saved {@link Message}
     */
    @Transactional
    public Message sendMessage(Message message) {
        Chat chat = chatService.getOrThrow(message.getChat().getId());

        chatService.validateChatIsNotClosed(chat);

        Employee author = employeeService.getOrCreate(message.getAuthor());

        message.setAuthor(author);

        for (Employee employee : chat.getMembers()) {
            MessageStatus status = messageStatusService.create(message, employee);
            message.addToStatuses(status);
        }

        return update(message);

    }

    public List<Message> getAllByChatId(Integer id) {
        return repository.findByChatId(id);
    }

    public Page<Message> getAllByChatIdPageable(Integer id, Pageable pageable) {
        Predicate predicate = QMessage.message.chat.id.eq(id);
        return repository.findAll(predicate, pageable);
    }

}
