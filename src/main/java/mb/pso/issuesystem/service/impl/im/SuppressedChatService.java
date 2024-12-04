package mb.pso.issuesystem.service.impl.im;

import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.im.QSuppressedChat;
import mb.pso.issuesystem.entity.im.SuppressedChat;
import mb.pso.issuesystem.repository.CombinedRepository;
import mb.pso.issuesystem.repository.im.SuppressedChatRepository;
import mb.pso.issuesystem.service.AbstractCrudService;

//[x] REFACTOR
/**
 * Service for managing suppressed chats.
 * Handles suppressing and unsuppressing chats for specific employees.
 */
@Service
public class SuppressedChatService extends AbstractCrudService<SuppressedChat, Integer> {

    private final SuppressedChatRepository repository;

    public SuppressedChatService(SuppressedChatRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Integer getId(SuppressedChat entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<SuppressedChat, Integer> getRepository() {
        return repository;
    }

    /**
     * Unsuppresses a chat for a specific employee.
     * If the chat is not found, no action is taken.
     *
     * @param chatId      the ID of the chat
     * @param displayName the display name of the employee
     */
    public void unsuppressChat(Integer chatId, String displayName) {
        QSuppressedChat suppressedChat = QSuppressedChat.suppressedChat;
        Predicate predicate = suppressedChat.employee.displayName.eq(displayName)
                .and(suppressedChat.chatId.eq(chatId));
        repository.findOne(predicate)
                .ifPresent(suppressed -> {
                    suppressed.setIsSuppressed(false);
                    repository.save(suppressed);
                });

    }

    /**
     * Unsuppresses a chat by directly modifying the provided {@link SuppressedChat}
     * entity.
     *
     * @param suppressedChat the {@link SuppressedChat} entity to unsuppress
     */
    public void unsuppressChat(SuppressedChat suppressedChat) {
        suppressedChat.setIsSuppressed(true);
        repository.save(suppressedChat);
    }

    /**
     * Suppresses a chat for a specific employee.
     * If no suppression record exists, a new one is created.
     *
     * @param chatId   the ID of the chat
     * @param employee the {@link Employee} for whom the chat is being suppressed
     */
    public void suppressChat(Integer chatId, Employee employee) {
        QSuppressedChat suppressedChat = QSuppressedChat.suppressedChat;
        Predicate predicate = suppressedChat.employee.displayName.eq(employee.getDisplayName())
                .and(suppressedChat.chatId.eq(chatId));
        repository.findOne(predicate).ifPresentOrElse(
                unsuppressed -> {
                    unsuppressed.setIsSuppressed(true);
                    repository.save(unsuppressed);
                },
                () -> {
                    final SuppressedChat unsuppressed = new SuppressedChat(employee,
                            chatId, true);
                    repository.save(unsuppressed);
                });
    }

    /**
     * Suppresses a chat by directly modifying the provided {@link SuppressedChat}
     * entity.
     *
     * @param unsuppressedChat the {@link SuppressedChat} entity to suppress
     */
    public void suppressChat(SuppressedChat unsuppressedChat) {
        unsuppressedChat.setIsSuppressed(true);
        repository.save(unsuppressedChat);
    }

}
