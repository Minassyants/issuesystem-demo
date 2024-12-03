package mb.pso.issuesystem.service.notifications;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mb.pso.issuesystem.entity.Notification;
import mb.pso.issuesystem.exceptions.NotificationNotFoundException;
import mb.pso.issuesystem.repository.NotificationRepository;

//[x] REFACTOR
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void markAsRead(Integer id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException("Notification with id " + id + " not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }
}
