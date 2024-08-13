package mb.pso.issuesystem.service.notifications.impl;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.utility.EmailNotification;
import mb.pso.issuesystem.service.notifications.EmailNotificationService;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {
    private final WebClient webClient;

    public EmailNotificationServiceImpl() {
        this.webClient = WebClient.create("http://kz-alm-bsk-ws01.ukravto.loc:7008");

    }

    @Override
    public void sendEmail(EmailNotification emailNotification) {
        try {

            webClient.post()
                    .uri(builder -> builder.path("/api/mail/send").queryParam("prefix", emailNotification.getPrefix())
                            .queryParam("to", emailNotification.getTo())
                            .queryParam("subject", emailNotification.getSubject())
                            .queryParam("templateName", emailNotification.getTemplateName()).build())
                    .contentType(MediaType.APPLICATION_JSON)

                    .bodyValue(emailNotification.getBody().toString()).retrieve().bodyToMono(EmailNotification.class)
                    .doOnSuccess(System.out::println)
                    .doOnError(System.out::println).subscribe();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
