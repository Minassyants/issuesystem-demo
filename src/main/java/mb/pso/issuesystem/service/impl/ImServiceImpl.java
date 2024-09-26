package mb.pso.issuesystem.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.im.Message;
import mb.pso.issuesystem.entity.im.QMessage;
import mb.pso.issuesystem.repository.im.MessageRepository;

@Service
// [ ] extract interface
public class ImServiceImpl {
    private final MessageRepository messageRepository;

    public ImServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    //[ ]  getMessagesByChatId
    public List<Message> getMessagesByChatId(Integer id) {
        return messageRepository.findByChatId(id);
    }

    public Page<Message> getMessagesByChatIdPageable(Integer id,Pageable pageable)
    {
        QMessage message = QMessage.message;
        Predicate predicate = message.chat.id.eq(id);
        return messageRepository.findAll(predicate, pageable);
    }

    //[x] sendMessage
    public void sendMessage(Message message) {
        messageRepository.save(message);
        // [ ] send message to MQ


    }

}
