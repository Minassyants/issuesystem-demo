package mb.pso.issuesystem.dto;

import java.time.LocalDateTime;

public record NotificationDto(Integer id, String type, String policy, Boolean isRead, Boolean isSent,
        EmployeeDto employee, LocalDateTime createdAt, String text, Integer refId) {

}
