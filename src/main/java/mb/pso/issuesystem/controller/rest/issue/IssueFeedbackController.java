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

import mb.pso.issuesystem.dto.IssueDto;
import mb.pso.issuesystem.entity.DepartmentFeedback;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.service.impl.core.DtoMapper;
import mb.pso.issuesystem.service.impl.core.issue.IssueFeedbackService;

//[ ] REFACTOR
@RestController
@RequestMapping("/issues/{issueId}/department-feedbacks")
public class IssueFeedbackController {

    private final DtoMapper mapper;
    private final IssueFeedbackService issueFeedbackService;

    public IssueFeedbackController(DtoMapper mapper, IssueFeedbackService issueFeedbackService) {
        this.mapper = mapper;
        this.issueFeedbackService = issueFeedbackService;
    }

    @GetMapping
    public ResponseEntity<Set<DepartmentFeedback>> getDepartmentFeedbacksByIssueId(@PathVariable Integer issueId) {
        Set<DepartmentFeedback> departmentFeedbacks = issueFeedbackService.getDepartmentFeedbacksByIssueId(issueId);
        return ResponseEntity.ok(departmentFeedbacks);
    }

    @PatchMapping
    public ResponseEntity<IssueDto> addToDepartmentFeedbacks(@PathVariable Integer issueId,
            @RequestBody DepartmentFeedback departmentFeedback) {
        Issue updatedIssue = issueFeedbackService.addToDepartmentFeedbacks(issueId, departmentFeedback);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(mapper.toDto(updatedIssue, IssueDto.class));
    }

    @PostMapping
    public ResponseEntity<IssueDto> updateDepartmentFeedbacks(@PathVariable Integer issueId,
            @RequestBody List<DepartmentFeedback> departmentFeedbacks) {
        Issue updatedIssue = issueFeedbackService.updateDepartmentFeedbacks(issueId, departmentFeedbacks);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(mapper.toDto(updatedIssue, IssueDto.class));
    }

}
