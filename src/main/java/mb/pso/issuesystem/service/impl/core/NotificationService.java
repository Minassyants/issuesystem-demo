package mb.pso.issuesystem.service.impl.core;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mb.pso.issuesystem.entity.Notification;
import mb.pso.issuesystem.exceptions.NotificationNotFoundException;
import mb.pso.issuesystem.repository.CombinedRepository;
import mb.pso.issuesystem.repository.NotificationRepository;
import mb.pso.issuesystem.service.AbstractCrudService;

//[x] REFACTOR
@Service
public class NotificationService extends AbstractCrudService<Notification, Integer> {
    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void markAsRead(Integer id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notification with id " + id + " not found"));
        notification.setIsRead(true);
        repository.save(notification);
    }

    @Override
    protected Integer getId(Notification entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<Notification, Integer> getRepository() {
        return repository;
    }

}
