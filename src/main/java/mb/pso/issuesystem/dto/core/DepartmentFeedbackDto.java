package mb.pso.issuesystem.dto.core;

import java.util.List;

import mb.pso.issuesystem.dto.im.MessageDto;

public record DepartmentFeedbackDto(Integer id, EmployeeDto author, MessageDto message, String feedback,
        List<AttachedFileDto> attachedFiles) {

}
