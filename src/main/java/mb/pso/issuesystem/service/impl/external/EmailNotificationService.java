package mb.pso.issuesystem.service.impl.external;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import mb.pso.issuesystem.config.properties.EmailServiceProperties;
import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.utility.EmailNotification;

//[x] REFACTOR
@Service
public class EmailNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);

    private final WebClient webClient;

    @Value("${email-service.default-prefix}")
    private String defaultPrefix;

    public EmailNotificationService(EmailServiceProperties emailServiceProperties, WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(emailServiceProperties.getUrl()).build();

    }

    public void sendEmail(EmailNotification emailNotification) {
        try {
            webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/mail/send")
                            .queryParam("prefix", emailNotification.getPrefix())
                            .queryParam("to", emailNotification.getTo())
                            .queryParam("subject", emailNotification.getSubject())
                            .queryParam("templateName", emailNotification.getTemplateName())
                            .build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(emailNotification.getBody().toString())
                    .retrieve()
                    .bodyToMono(EmailNotification.class)
                    .doOnSuccess(response -> logger.info("Email sent successfully: {}", response))
                    .doOnError(error -> logger.error("Error sending email", error))
                    .subscribe();
        } catch (Exception e) {
            logger.error("Exception occurred while sending email", e);
        }

    }

    public void sendEmailNotifications(HashMap<Employee, String> notifications, String title, String templateId) {
        notifications.forEach((employee, text) -> {
            EmailNotification emailNotification = new EmailNotification.Builder()
                    .prefix(defaultPrefix)
                    .to(employee.getMail())
                    .templateName(templateId)
                    .subject(title)
                    .with("title", title)
                    .with("text", text)
                    .build();
            sendEmail(emailNotification);
        });

    }

}
