package mb.pso.issuesystem.controller.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import mb.pso.issuesystem.dto.core.NotificationDto;
import mb.pso.issuesystem.entity.core.Notification;
import mb.pso.issuesystem.service.impl.core.DtoMapper;
import mb.pso.issuesystem.service.impl.core.NotificationService;


@RestController
@RequestMapping("/notifications")
@Tag(name = "Notifications", description = "API for managing user notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final DtoMapper mapper;

    public NotificationController(NotificationService notificationService, DtoMapper mapper) {
        this.notificationService = notificationService;
        this.mapper = mapper;
    }

    @Operation(summary = "Get user notifications", description = "Retrieve a pageable list of user notifications")
    @GetMapping
    public ResponseEntity<Page<NotificationDto>> getUserNotifications(
            @AuthenticationPrincipal @Parameter(description = "Authenticated user's JWT token") Jwt jwt,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") @Min(1) int size) {

        PageRequest pageable = PageRequest.of(page, size,
                Sort.by(Sort.Order.asc("isRead"), Sort.Order.desc("createdAt")));
        Page<Notification> notifications = notificationService.get(pageable, jwt);

        return ResponseEntity.ok(mapper.toDtoPage(notifications, NotificationDto.class));
    }

    @Operation(summary = "Get unread notification count", description = "Retrieve the count of unread notifications")
    @GetMapping("/count")
    public ResponseEntity<Long> getNotificationCount(
            @AuthenticationPrincipal @Parameter(description = "Authenticated user's JWT token") Jwt jwt) {

        return ResponseEntity.ok(notificationService.count(jwt));
    }

}
