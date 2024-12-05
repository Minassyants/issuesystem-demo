package mb.pso.issuesystem.service.impl.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;

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

}
