package mb.pso.issuesystem.service.impl;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.im.Chat;
import mb.pso.issuesystem.entity.im.Message;
import mb.pso.issuesystem.entity.im.QMessage;
import mb.pso.issuesystem.repository.EmployeeRepository;
import mb.pso.issuesystem.repository.im.ChatRepository;
import mb.pso.issuesystem.repository.im.MessageRepository;

@Service
// [ ] extract interface
public class ImServiceImpl {
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRepository chatRepository;
    private final EmployeeRepository employeeRepository;

    public ImServiceImpl(MessageRepository messageRepository, SimpMessagingTemplate simpMessagingTemplate,
            ChatRepository chatRepository, EmployeeRepository employeeRepository) {
        this.messageRepository = messageRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatRepository = chatRepository;
        this.employeeRepository = employeeRepository;
    }

    public Chat deleteMemberFromChat(Integer chatId, Employee employee) {
        Chat chat = chatRepository.findById(chatId).orElse(null);
        if (chat != null) {
            if (chat.getMembers().removeIf(t -> t.getMail().equals(employee.getMail())))
                return chatRepository.save(chat);
        }
        return null;
    }

    public Chat addMemberToChat(Integer chatId, Employee employee) {
        Chat chat = chatRepository.findById(chatId).orElse(null);
        if (chat != null) {
            employee = employeeRepository.findOne(Example.of(employee)).orElse(employee);
            chat.getMembers().add(employee);
            return chatRepository.save(chat);
        }
        return null;
    }

    public Chat getChatById(Integer id) {
        return chatRepository.findById(id).orElseGet(null);
    }

    // [x] getMessagesByChatId
    public List<Message> getMessagesByChatId(Integer id) {
        return messageRepository.findByChatId(id);
    }

    public Page<Message> getMessagesByChatIdPageable(Integer id, Pageable pageable) {
        QMessage message = QMessage.message;
        Predicate predicate = message.chat.id.eq(id);
        return messageRepository.findAll(predicate, pageable);
    }

    // [x] sendMessage
    // [ ] добавить индикацую успешности отправки сообщения
    public void sendMessage(Message message) {
        Chat chat = chatRepository.findById(message.getChat().getId()).orElse(null);

        if (chat != null) {
            Employee author = employeeRepository.findOne(Example.of(message.getAuthor())).orElse(message.getAuthor());
            message.setAuthor(author);
            Message msg = messageRepository.save(message);

            simpMessagingTemplate.convertAndSend("/topic/chat/" + msg.getChat().getId().toString(), msg);
        }

    }

}
