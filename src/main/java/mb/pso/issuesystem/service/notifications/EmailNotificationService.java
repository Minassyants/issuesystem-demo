package mb.pso.issuesystem.service.notifications;

import mb.pso.issuesystem.entity.utility.EmailNotification;

public interface EmailNotificationService {
    
    public void sendEmail(EmailNotification emailNotification);
}
