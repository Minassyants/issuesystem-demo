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

import mb.pso.issuesystem.dto.EmployeeDto;
import mb.pso.issuesystem.dto.IssueDto;
import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.service.impl.core.DtoMapper;
import mb.pso.issuesystem.service.impl.core.issue.IssueEmployeeService;

//[ ] REFACTOR
@RestController
@RequestMapping("/issues/{issueId}/employees")
public class IssueEmployeeController {

    private final DtoMapper mapper;
    private final IssueEmployeeService issueEmployeeService;

    public IssueEmployeeController(DtoMapper mapper, IssueEmployeeService issueEmployeeService) {
        this.mapper = mapper;
        this.issueEmployeeService = issueEmployeeService;
    }

    @GetMapping
    public ResponseEntity<Set<EmployeeDto>> getIssuedEmployeesByIssueId(@PathVariable Integer issueId) {
        Set<Employee> issuedEmployees = issueEmployeeService.getIssuedEmployeesByIssueId(issueId);
        return ResponseEntity.ok(mapper.toDtoSet(issuedEmployees, EmployeeDto.class));
    }

    @PatchMapping
    public ResponseEntity<IssueDto> addToIssuedEmployees(@PathVariable Integer issueId,
            @RequestBody Employee employee) {
        Issue updatedIssue = issueEmployeeService.addToIssuedEmployees(issueId, employee);
        return ResponseEntity.ok(mapper.toDto(updatedIssue, IssueDto.class));
    }

    @PostMapping
    public ResponseEntity<IssueDto> updateIssuedEmployees(@PathVariable Integer issueId,
            @RequestBody List<Employee> employees) {
        Issue updatedIssue = issueEmployeeService.updateIssuedEmployees(issueId, employees);
        return ResponseEntity.ok(mapper.toDto(updatedIssue, IssueDto.class));

    }

}
