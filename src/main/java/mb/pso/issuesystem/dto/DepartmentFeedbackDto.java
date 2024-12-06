package mb.pso.issuesystem.dto;

import java.util.List;

public record DepartmentFeedbackDto(Integer id, EmployeeDto author, MessageDto message, String feedback,
        List<AttachedFileDto> attachedFiles) {

}
