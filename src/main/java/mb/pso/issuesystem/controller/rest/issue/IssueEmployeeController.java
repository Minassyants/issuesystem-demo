package mb.pso.issuesystem.controller.rest.issue;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mb.pso.issuesystem.dto.core.EmployeeDto;
import mb.pso.issuesystem.dto.core.IssueDto;
import mb.pso.issuesystem.entity.core.Employee;
import mb.pso.issuesystem.entity.core.Issue;
import mb.pso.issuesystem.service.impl.core.DtoMapper;
import mb.pso.issuesystem.service.impl.core.issue.IssueEmployeeService;


@Tag(name = "Issues", description = "Operations related to Issues")
@RestController
@RequestMapping("/issues/{issueId}/employees")
public class IssueEmployeeController {

    private final DtoMapper mapper;
    private final IssueEmployeeService issueEmployeeService;

    public IssueEmployeeController(DtoMapper mapper, IssueEmployeeService issueEmployeeService) {
        this.mapper = mapper;
        this.issueEmployeeService = issueEmployeeService;
    }

    @Operation(summary = "Get all issued employees", description = "Return all employees by issue ID")
    @GetMapping
    public ResponseEntity<Set<EmployeeDto>> getIssuedEmployeesByIssueId(@PathVariable Integer issueId) {
        Set<Employee> issuedEmployees = issueEmployeeService.getIssuedEmployeesByIssueId(issueId);
        return ResponseEntity.ok(mapper.toDtoSet(issuedEmployees, EmployeeDto.class));
    }

    @Operation(summary = "Adds to issued employees list", description = "Adds a new employee to the issue")
    @PatchMapping
    public ResponseEntity<IssueDto> addToIssuedEmployees(@PathVariable Integer issueId,
            @RequestBody Employee employee) {
        Issue updatedIssue = issueEmployeeService.addToIssuedEmployees(issueId, employee);
        return ResponseEntity.ok(mapper.toDto(updatedIssue, IssueDto.class));
    }

    @Operation(summary = "Updates issued employees", description = "Replaces list of employees with a provided one")
    @PostMapping
    public ResponseEntity<IssueDto> updateIssuedEmployees(@PathVariable Integer issueId,
            @RequestBody List<Employee> employees) {
        Issue updatedIssue = issueEmployeeService.updateIssuedEmployees(issueId, employees);
        return ResponseEntity.ok(mapper.toDto(updatedIssue, IssueDto.class));

    }

}
