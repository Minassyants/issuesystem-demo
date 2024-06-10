package mb.pso.issuesystem.service.notifications.impl;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.utility.EmailNotification;
import mb.pso.issuesystem.service.notifications.EmailNotificationService;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public EmailNotificationServiceImpl(ObjectMapper objectMapper) {
        this.webClient = WebClient.create("http://kz-alm-bsk-ws01.ukravto.loc:7000");
        this.objectMapper = objectMapper;
    }

    @Override
    public void sendEmail(EmailNotification emailNotification) {
        try {
            String body = objectMapper.writeValueAsString(emailNotification);
            webClient.post().uri("/items/Email")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer MgRvN6ErScXnUsAEMYzLpxozVwyW-Y7w")
                    .bodyValue(body).retrieve().bodyToMono(EmailNotification.class)
                    .doOnSuccess(System.out::println)
                    .doOnError(System.out::println).subscribe();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
