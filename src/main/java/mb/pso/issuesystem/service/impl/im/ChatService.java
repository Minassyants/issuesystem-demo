package mb.pso.issuesystem.service.impl.im;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mb.pso.issuesystem.entity.core.Employee;
import mb.pso.issuesystem.entity.core.Notification;
import mb.pso.issuesystem.entity.enums.NotificationPolicy;
import mb.pso.issuesystem.entity.enums.NotificationType;
import mb.pso.issuesystem.entity.im.Chat;
import mb.pso.issuesystem.exceptions.IllegalActionException;
import mb.pso.issuesystem.repository.core.CombinedRepository;
import mb.pso.issuesystem.repository.im.ChatRepository;
import mb.pso.issuesystem.service.AbstractCrudService;
import mb.pso.issuesystem.service.impl.core.EmployeeService;
import mb.pso.issuesystem.service.impl.core.NotificationService;


/**
 * Service class for managing chats.
 * Extends {@link AbstractCrudService} to provide basic CRUD operations
 * and adds custom methods for managing chat members.
 */
@Service
public class ChatService extends AbstractCrudService<Chat, Integer> {

    private final ChatRepository repository;
    private final NotificationService notificationService;
    private final EmployeeService employeeService;

    public ChatService(ChatRepository repository,
            NotificationService notificationService,
            EmployeeService employeeService) {
        this.repository = repository;
        this.notificationService = notificationService;
        this.employeeService = employeeService;
    }

    @Override
    protected Integer getId(Chat entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<Chat, Integer> getRepository() {
        return repository;
    }

    /**
     * Removes a member from a chat.
     *
     * @param chatId   the ID of the chat
     * @param employee the {@link Employee} to be removed
     * @return the removed {@link Employee}
     * @throws IllegalActionException if the chat is closed
     */
    @Transactional
    public Employee deleteMemberFromChat(Integer chatId, Employee employee) {

        Chat chat = getOrThrow(chatId);

        validateChatIsNotClosed(chat);

        Employee foundEmployee = employeeService.getOrThrow(employee.getDisplayName());

        if (chat.getMembers().remove(foundEmployee))
            update(chat);

        return foundEmployee;
    }

    /**
     * Adds a member to a chat and sends a notification.
     *
     * @param chatId   the ID of the chat
     * @param employee the {@link Employee} to be added
     * @return the added {@link Employee}
     * @throws IllegalActionException if the chat is closed
     */
    @Transactional
    public Employee addMemberToChat(Integer chatId, Employee employee) {
        Chat chat = getOrThrow(chatId);

        validateChatIsNotClosed(chat);

        employee = employeeService.getOrCreate(employee);
        chat.addToMembers(employee);

        update(chat);

        Notification notification = new Notification();
        notification.setEmployee(employee);
        notification.setPolicy(NotificationPolicy.BOTH);
        notification.setType(NotificationType.employeeAddedToChat);
        notification.setText("Обращение № " + chatId.toString());
        notification.setRefId(chat.getId());
        notificationService.create(notification);

        return employee;
    }

    public void validateChatIsNotClosed(Chat chat) {
        if (chat.getIsClosed()) {
            throw new IllegalActionException("Chat is already closed.");
        }
    }

}
