package mb.pso.issuesystem.service.impl.core;

import java.util.HashMap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.Notification;
import mb.pso.issuesystem.entity.QNotification;
import mb.pso.issuesystem.exceptions.NotificationNotFoundException;
import mb.pso.issuesystem.repository.core.CombinedRepository;
import mb.pso.issuesystem.repository.core.NotificationRepository;
import mb.pso.issuesystem.service.AbstractCrudService;
import mb.pso.issuesystem.utils.JwtUtils;

//[x] REFACTOR
@Service
public class NotificationService extends AbstractCrudService<Notification, Integer> {
    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Integer getId(Notification entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<Notification, Integer> getRepository() {
        return repository;
    }

    @Transactional
    public void markAsRead(Integer id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(
                        String.format("Notification witd id `%d` not found", id)));
        notification.setIsRead(true);
        repository.save(notification);
    }

    public long count(Jwt jwt) {
        QNotification notification = QNotification.notification;
        Predicate predicate = notification.isRead.eq(false)
                .and(notification.employee.displayName.eq(JwtUtils.extractDisplayName(jwt)));
        return repository.count(predicate);
    }

    public Page<Notification> get(Pageable pageable, Jwt jwt) {
        QNotification notification = QNotification.notification;
        Predicate predicate = notification.employee.displayName.eq(JwtUtils.extractDisplayName(jwt));
        return repository.findAll(predicate, pageable);
    }

    /**
     * Retrieves notifications grouped by {@link Employee}.
     * <p>
     * <strong>
     * Retrieved notifications automatically get persisted with
     * {@code IsSent = true}.
     * </strong>
     * </p>
     *
     * @param predicate predicate to retrieve notifications.
     * @return {@code HashMap<Employee,String>} with combined notification texts as
     *         values.
     */
    public HashMap<Employee, String> gropNotificationsByEmployee(Predicate predicate) {
        HashMap<Employee, String> notifications = new HashMap<>();

        Iterable<Notification> foundNotifications = repository.findAll(predicate);

        for (Notification notification : foundNotifications) {
            String text = notifications.getOrDefault(notification.getEmployee(), "");
            text += notification.getText() + "\n";
            notifications.put(notification.getEmployee(), text);
            notification.setIsSent(true);
        }
        repository.saveAll(foundNotifications);
        return notifications;
    }

}
