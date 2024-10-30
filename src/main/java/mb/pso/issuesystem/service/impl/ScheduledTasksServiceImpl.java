package mb.pso.issuesystem.service.impl;

import java.util.Set;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import mb.pso.issuesystem.dto.webSocket.SocketMsg;

@Service
public class ScheduledTasksServiceImpl {

    private SimpUserRegistry simpUserRegistry;
    private SimpMessagingTemplate simpMessagingTemplate;

    public ScheduledTasksServiceImpl(SimpUserRegistry simpUserRegistry, SimpMessagingTemplate simpMessagingTemplate) {
        this.simpUserRegistry = simpUserRegistry;
        this.simpMessagingTemplate = simpMessagingTemplate;

    }

    // @Scheduled(fixedDelay = 7000)
    public void test() {
        Set<SimpUser> a = simpUserRegistry.getUsers();
        
        System.out.println("================");
        for (SimpUser simpUser : a) {
            simpMessagingTemplate.convertAndSendToUser(simpUser.getName(), "/topic/errors",
                    new SocketMsg(SocketMsg.MsgType.ERROR, simpUser.getName()));
        }
        System.out.println("================");
    }
}
