package mb.pso.issuesystem.service.impl.im;

import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.im.Message;
import mb.pso.issuesystem.entity.im.MessageStatus;
import mb.pso.issuesystem.entity.im.QMessageStatus;
import mb.pso.issuesystem.repository.CombinedRepository;
import mb.pso.issuesystem.repository.MessageStatusRepository;
import mb.pso.issuesystem.service.AbstractCrudService;

//[x] REFACTOR
/**
 * Service class for managing message statuses.
 * Provides methods for retrieving, creating, and validating message statuses.
 */
@Service
public class MessageStatusService extends AbstractCrudService<MessageStatus, Integer> {

    private final MessageStatusRepository repository;

    public MessageStatusService(MessageStatusRepository repository) {
        this.repository = repository;

    }

    @Override
    protected Integer getId(MessageStatus entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<MessageStatus, Integer> getRepository() {
        return repository;
    }

    /**
     * Finds a message status by message ID and employee display name.
     * Throws an exception if no such status is found.
     *
     * @param messageId   the ID of the message
     * @param displayName the display name of the employee
     * @return the found {@link MessageStatus}
     * @throws IllegalArgumentException if no message status is found
     */
    public MessageStatus findOneOrThrow(Integer messageId, String displayName) {
        QMessageStatus messageStatus = QMessageStatus.messageStatus;
        Predicate predicate = messageStatus.message.id.eq(messageId)
                .and(messageStatus.employee.displayName.eq(displayName));

        return repository.findOne(predicate)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Message status for message ID %d and employee %s not found", messageId,
                                displayName)));
    }

    /**
     * Creates a new message status for the given message and employee with unread
     * status.
     *
     * @param message  the {@link Message} for which the status is created
     * @param employee the {@link Employee} associated with the message status
     * @return the created {@link MessageStatus}
     */
    public MessageStatus create(Message message, Employee employee) {
        return repository.save(new MessageStatus(message, employee));
    }

}
