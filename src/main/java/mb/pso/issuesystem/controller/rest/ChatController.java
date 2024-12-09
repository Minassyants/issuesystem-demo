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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import mb.pso.issuesystem.dto.im.ChatDto;
import mb.pso.issuesystem.dto.im.MessageDto;
import mb.pso.issuesystem.entity.im.Message;
import mb.pso.issuesystem.service.impl.core.DtoMapper;
import mb.pso.issuesystem.service.impl.im.ChatService;
import mb.pso.issuesystem.service.impl.im.MessageService;


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

    @Operation(summary = "Retrieve chat by ID", description = "Fetch a chat by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Chat retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Chat not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ChatDto> get(@PathVariable Integer id) {
        return ResponseEntity.ok(mapper.toDto(chatService.getOrThrow(id), ChatDto.class));
    }

    @Operation(summary = "Retrieve messages of a chat")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Messages retrieved successfully")
    })
    @GetMapping("/{id}/messages")
    public ResponseEntity<Page<MessageDto>> getMessagesPageable(@PathVariable Integer id,
            @RequestParam(defaultValue = "0") @Min(0) Integer size,
            @RequestParam(defaultValue = "10") @Min(1) Integer page) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt"));
        Page<Message> messages = messageService.getAllByChatIdPageable(id, pageRequest);

        return ResponseEntity.ok(mapper.toDtoPage(messages, MessageDto.class));
    }
}
