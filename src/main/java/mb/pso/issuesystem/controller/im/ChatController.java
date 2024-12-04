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
import mb.pso.issuesystem.service.impl.im.ChatService;
import mb.pso.issuesystem.service.impl.im.MessageService;
import mb.pso.issuesystem.service.impl.im.SuppressedChatService;

//[x] REFACTOR
@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final SuppressedChatService suppressedChatService;
    private final MessageService messageService;
    private final ChatService chatService;

    public ChatController(SimpMessagingTemplate messagingTemplate, SuppressedChatService suppressedChatService,
            ChatService chatService,
            MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.suppressedChatService = suppressedChatService;
        this.messageService = messageService;
        this.chatService = chatService;
    }

    @MessageMapping("/chat/{chatId}/unsuppress")
    public void unsurpressChat(@DestinationVariable Integer chatId, JwtAuthenticationToken jwt) {
        String displayName = extractDisplayName(jwt);
        suppressedChatService.unsuppressChat(chatId, displayName);
    }

    @MessageMapping("/chat/sendmessage")
    public void sendMessage(Message message) {
        Message savedMessage = messageService.sendMessage(message);
        sendToChatTopic(savedMessage.getChat().getId(), SocketMsg.MsgType.NEWMESSAGE, savedMessage);
    }

    @MessageMapping("/chat/{chatId}/addmembertochat")
    public void addMemberToChat(@DestinationVariable Integer chatId, Employee employee) {
        Employee addedEmployee = chatService.addMemberToChat(chatId, employee);
        sendToChatTopic(chatId, SocketMsg.MsgType.ADDEDMEMBER, addedEmployee);
    }

    @MessageMapping("/chat/{chatId}/deletememberfromchat")
    public void deleteMemberFromChat(@DestinationVariable Integer chatId, Employee employee) {
        Employee removedEmployee = chatService.deleteMemberFromChat(chatId, employee);
        sendToChatTopic(chatId, SocketMsg.MsgType.DELETEDMEMBER, removedEmployee);
    }

    @MessageMapping("/message/{id}/markasread")
    @Transactional
    public void markAsRead(@DestinationVariable Integer id, JwtAuthenticationToken jwt) {
        String displayName = extractDisplayName(jwt);
        Message readMessage = messageService.markAsRead(id, displayName);
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
