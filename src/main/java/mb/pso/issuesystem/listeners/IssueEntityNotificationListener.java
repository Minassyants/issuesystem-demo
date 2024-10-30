package mb.pso.issuesystem.listeners;

import java.text.DateFormat;

import org.springframework.stereotype.Component;

import jakarta.persistence.PostPersist;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.Notification;
import mb.pso.issuesystem.entity.enums.NotificationPolicy;
import mb.pso.issuesystem.entity.enums.NotificationType;
import mb.pso.issuesystem.exceptions.EmployeeNotFoundException;
import mb.pso.issuesystem.repository.EmployeeRepository;
import mb.pso.issuesystem.repository.NotificationRepository;

@Component
public class IssueEntityNotificationListener {

    private NotificationRepository notificationRepository;
    private EmployeeRepository employeeRepository;

    public IssueEntityNotificationListener(NotificationRepository notificationRepository,
            EmployeeRepository employeeRepository) {
        this.notificationRepository = notificationRepository;
        this.employeeRepository = employeeRepository;
    }

    @PostPersist
    public void handlePostPersist(Issue issue) {
        Notification notification = new Notification();
        notification.setPolicy(NotificationPolicy.BOTH);
        notification.setType(NotificationType.newIssue);
        notification.setIsRead(false);
        notification.setIsSent(false);
        notification.setRefId(issue.getId());
        notification.setText("№ " + issue.getId().toString() + " от "
                + DateFormat.getDateTimeInstance().format(issue.getDocDate()));
        employeeRepository.findById("admin").ifPresentOrElse(t -> {
            notification.setEmployee(t);
        }, () -> {
            throw new EmployeeNotFoundException("admin");
        });
        notificationRepository.save(notification);
    }

}
