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
        this.webClient = WebClient.create("http://kz-alm-bsk-ws01.ukravto.loc:7000");
    }

    @Override
    public void sendEmail(EmailNotification emailNotification) {
            
        webClient.post().uri("items/Email")
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer MgRvN6ErScXnUsAEMYzLpxozVwyW-Y7w")
        .bodyValue(emailNotification).retrieve().bodyToMono(Void.class);
    }

}
