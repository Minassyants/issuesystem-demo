package mb.pso.issuesystem.listeners;

import java.text.DateFormat;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import jakarta.persistence.PrePersist;
import mb.pso.issuesystem.entity.core.Issue;
import mb.pso.issuesystem.entity.core.Notification;
import mb.pso.issuesystem.entity.enums.NotificationPolicy;
import mb.pso.issuesystem.entity.enums.NotificationType;
import mb.pso.issuesystem.service.impl.core.EmployeeService;
import mb.pso.issuesystem.service.impl.core.NotificationService;

@Component
public class IssueEntityNotificationListener {

    private NotificationService notificationService;
    private EmployeeService employeeService;

    public IssueEntityNotificationListener(@Lazy NotificationService notificationService,
            @Lazy EmployeeService employeeService) {
        this.notificationService = notificationService;
        this.employeeService = employeeService;
    }

    // [ ] Переделать админ на роль оператор
    @PrePersist
    public void handlePostPersist(Issue issue) {
        Notification notification = new Notification(NotificationType.newIssue, NotificationPolicy.BOTH,
                employeeService.getOrThrow("admin"),
                "№ " + issue.getId().toString() + " от "
                        + DateFormat.getDateTimeInstance().format(issue.getDocDate()),
                issue.getId());

        notificationService.create(notification);
    }

}
