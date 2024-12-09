package mb.pso.issuesystem.controller.rest.issue;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import mb.pso.issuesystem.dto.core.IssueDto;
import mb.pso.issuesystem.entity.core.Issue;
import mb.pso.issuesystem.service.impl.core.DtoMapper;
import mb.pso.issuesystem.service.impl.core.issue.IssueStateService;
import mb.pso.issuesystem.utils.JwtUtils;


@Tag(name = "Issues", description = "Operations related to Issues")
@RestController
@RequestMapping("/issues/{issueId}")
public class IssueStateController {

    private final DtoMapper mapper;
    private final IssueStateService issueStateService;

    public IssueStateController(DtoMapper mapper, IssueStateService issueStateService) {
        this.mapper = mapper;
        this.issueStateService = issueStateService;
    }

    @Operation(summary = "Closes issue", description = "Sets status as closed")
    @PostMapping("/closed")
    public ResponseEntity<IssueDto> setClosed(@PathVariable Integer issueId) {
        return updateIssueState(issueId, () -> issueStateService.setClosed(issueId));
    }

    @Operation(summary = "Sets issue in progress", description = "Marks the issue as 'in progress'")
    @PostMapping("/in-progress")
    public ResponseEntity<IssueDto> setInProgress(@AuthenticationPrincipal Jwt jwt, @PathVariable Integer issueId) {
        String displayName = JwtUtils.extractDisplayName(jwt);
        return updateIssueState(issueId, () -> issueStateService.setInProgress(issueId, displayName));
    }

    @Operation(summary = "Sets issue pending", description = "Marks the issue as 'pending'")
    @PostMapping("/pending")
    public ResponseEntity<IssueDto> setPendingResult(@PathVariable Integer issueId) {
        return updateIssueState(issueId, () -> issueStateService.setPending(issueId));
    }

    private ResponseEntity<IssueDto> updateIssueState(Integer issueId, IssueUpdater updater) {
        Issue updatedIssue = updater.update();
        if (updatedIssue == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mapper.toDto(updatedIssue, IssueDto.class));
    }

    @FunctionalInterface
    private interface IssueUpdater {
        Issue update();
    }

}
