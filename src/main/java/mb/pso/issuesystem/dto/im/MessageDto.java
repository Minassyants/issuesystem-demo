package mb.pso.issuesystem.dto.im;

import mb.pso.issuesystem.dto.core.EmployeeDto;

public record MessageDto(Integer id, MessageContentDto content, EmployeeDto author) {

}
