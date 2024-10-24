package mb.pso.issuesystem.controller.im;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;

import mb.pso.issuesystem.dto.webSocket.SocketMsg;
import mb.pso.issuesystem.entity.AdUserDetails;
import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.im.Message;
import mb.pso.issuesystem.service.impl.ImServiceImpl;

@Controller
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ImServiceImpl imServiceImpl;

    public ChatController(SimpMessagingTemplate simpMessagingTemplate, ImServiceImpl imServiceImpl) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.imServiceImpl = imServiceImpl;
    }

    @MessageMapping("/chat/sendmessage")
    public void sendMessage(Message message) {
        Message msg = imServiceImpl.sendMessage(message);
        simpMessagingTemplate.convertAndSend("/topic/chat/" + msg.getChat().getId().toString(),
                new SocketMsg(SocketMsg.MsgType.NEWMESSAGE, msg));
    }

    @MessageMapping("/chat/{chatId}/addmembertochat")
    public void addMemberToChat(@DestinationVariable Integer chatId, Employee employee) {
        Employee addedEmployee = imServiceImpl.addMemberToChat(chatId, employee);
        simpMessagingTemplate.convertAndSend("/topic/chat/" + chatId + "/addmember",
                new SocketMsg(SocketMsg.MsgType.ADDEDMEMBER, addedEmployee));
    }

    @MessageMapping("/chat/{chatId}/deletememberfromchat")
    public void deleteMemberFromChat(@DestinationVariable Integer chatId, Employee employee) {
        Employee deletedEmployee = imServiceImpl.deleteMemberFromChat(chatId, employee);
        simpMessagingTemplate.convertAndSend("/topic/chat/" + chatId + "/deletemember",
                new SocketMsg(SocketMsg.MsgType.DELETEDMEMBER, deletedEmployee));
    }

    @MessageMapping("/message/{id}/markasread")
    public void markAsRead(@DestinationVariable Integer id, JwtAuthenticationToken jwt) {
        String displayName = ((Jwt) jwt.getPrincipal()).getClaimAsString("displayname");
        
    }

}
