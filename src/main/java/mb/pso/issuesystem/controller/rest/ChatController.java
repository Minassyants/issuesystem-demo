package mb.pso.issuesystem.controller.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mb.pso.issuesystem.dto.ChatDto;
import mb.pso.issuesystem.dto.MessageDto;
import mb.pso.issuesystem.entity.im.Message;
import mb.pso.issuesystem.service.impl.core.DtoMapper;
import mb.pso.issuesystem.service.impl.im.ChatService;
import mb.pso.issuesystem.service.impl.im.MessageService;

//[ ] REFACTOR
@RestController
@RequestMapping("/chats")
public class ChatController {

    private final ChatService chatService;
    private final MessageService messageService;
    private final DtoMapper mapper;

    public ChatController(ChatService chatService, MessageService messageService, DtoMapper mapper) {
        this.chatService = chatService;
        this.messageService = messageService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatDto> get(@PathVariable Integer id) {
        return ResponseEntity.ok(mapper.toDto(chatService.getOrThrow(id), ChatDto.class));
    }

    @GetMapping("/{id}/messages")
    public ResponseEntity<Page<MessageDto>> getMessagesPageable(@PathVariable Integer id,
            @RequestParam(defaultValue = "0") Integer size,
            @RequestParam(defaultValue = "10") Integer page) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt"));
        Page<Message> messages = messageService.getAllByChatIdPageable(id, pageRequest);

        return ResponseEntity.ok(mapper.toDtoPage(messages, MessageDto.class));
    }
}
