package mb.pso.issuesystem.controller.im;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import mb.pso.issuesystem.dto.webSocket.SocketMsg;
import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.im.Message;
import mb.pso.issuesystem.service.impl.ImServiceImpl;

//[x] REFACTOR
@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ImServiceImpl imService;

    public ChatController(SimpMessagingTemplate messagingTemplate, ImServiceImpl imService) {
        this.messagingTemplate = messagingTemplate;
        this.imService = imService;
    }

    @MessageMapping("/chat/{chatId}/unsuppress")
    public void unsurpressChat(@DestinationVariable Integer chatId, JwtAuthenticationToken jwt) {
        String displayName = extractDisplayName(jwt);
        imService.unsuppressChat(chatId, displayName);
    }

    @MessageMapping("/chat/sendmessage")
    public void sendMessage(Message message) {
        Message savedMessage = imService.sendMessage(message);
        sendToChatTopic(savedMessage.getChat().getId(), SocketMsg.MsgType.NEWMESSAGE, savedMessage);
    }

    @MessageMapping("/chat/{chatId}/addmembertochat")
    public void addMemberToChat(@DestinationVariable Integer chatId, Employee employee) {
        Employee addedEmployee = imService.addMemberToChat(chatId, employee);
        sendToChatTopic(chatId, SocketMsg.MsgType.ADDEDMEMBER, addedEmployee);
    }

    @MessageMapping("/chat/{chatId}/deletememberfromchat")
    public void deleteMemberFromChat(@DestinationVariable Integer chatId, Employee employee) {
        Employee removedEmployee = imService.deleteMemberFromChat(chatId, employee);
        sendToChatTopic(chatId, SocketMsg.MsgType.DELETEDMEMBER, removedEmployee);
    }

    @MessageMapping("/message/{id}/markasread")
    @Transactional
    public void markAsRead(@DestinationVariable Integer id, JwtAuthenticationToken jwt) {
        String displayName = extractDisplayName(jwt);
        Message readMessage = imService.markAsRead(id, displayName);
        sendToChatTopic(readMessage.getChat().getId(), SocketMsg.MsgType.READ, readMessage);
    }

    /**
     * Extracts the display name of the user from the JWT token.
     */
    private String extractDisplayName(JwtAuthenticationToken jwt) {
        Jwt principal = (Jwt) jwt.getPrincipal();
        return principal.getClaimAsString("displayname");
    }

    /**
     * Sends a message to a specific chat topic.
     */
    private void sendToChatTopic(Integer chatId, SocketMsg.MsgType msgType, Object payload) {
        messagingTemplate.convertAndSend(
                String.format("/topic/chat/%d", chatId),
                new SocketMsg(msgType, payload));
    }

}
