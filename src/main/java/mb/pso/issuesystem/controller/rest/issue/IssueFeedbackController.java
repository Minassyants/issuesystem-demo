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
import mb.pso.issuesystem.dto.core.DepartmentFeedbackDto;
import mb.pso.issuesystem.dto.core.IssueDto;
import mb.pso.issuesystem.entity.core.DepartmentFeedback;
import mb.pso.issuesystem.entity.core.Issue;
import mb.pso.issuesystem.service.impl.core.DtoMapper;
import mb.pso.issuesystem.service.impl.core.issue.IssueFeedbackService;


@Tag(name = "Issues", description = "Operations related to Issues")
@RestController
@RequestMapping("/issues/{issueId}/department-feedbacks")
public class IssueFeedbackController {

    private final DtoMapper mapper;
    private final IssueFeedbackService issueFeedbackService;

    public IssueFeedbackController(DtoMapper mapper, IssueFeedbackService issueFeedbackService) {
        this.mapper = mapper;
        this.issueFeedbackService = issueFeedbackService;
    }

    @Operation(summary = "Get all department feedback", description = "Returns all feedback by issue ID")
    @GetMapping
    public ResponseEntity<Set<DepartmentFeedbackDto>> getDepartmentFeedbacksByIssueId(@PathVariable Integer issueId) {
        Set<DepartmentFeedback> departmentFeedbacks = issueFeedbackService.getDepartmentFeedbacksByIssueId(issueId);
        return ResponseEntity.ok(mapper.toDtoSet(departmentFeedbacks, DepartmentFeedbackDto.class));
    }

    @Operation(summary = "Adds to issue's department feedback", description = "Adds a new feedback to the department feedback list")
    @PatchMapping
    public ResponseEntity<IssueDto> addToDepartmentFeedbacks(@PathVariable Integer issueId,
            @RequestBody DepartmentFeedback departmentFeedback) {
        Issue updatedIssue = issueFeedbackService.addToDepartmentFeedbacks(issueId, departmentFeedback);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(mapper.toDto(updatedIssue, IssueDto.class));
    }

    @Operation(summary = "Updates issue's department feedback", description = "Replaces current feedback with a new provided list of department feedback")
    @PostMapping
    public ResponseEntity<IssueDto> updateDepartmentFeedbacks(@PathVariable Integer issueId,
            @RequestBody List<DepartmentFeedback> departmentFeedbacks) {
        Issue updatedIssue = issueFeedbackService.updateDepartmentFeedbacks(issueId, departmentFeedbacks);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(mapper.toDto(updatedIssue, IssueDto.class));
    }

}
