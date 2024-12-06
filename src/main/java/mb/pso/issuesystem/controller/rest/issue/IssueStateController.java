package mb.pso.issuesystem.controller.rest.issue;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mb.pso.issuesystem.dto.IssueDto;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.service.impl.core.DtoMapper;
import mb.pso.issuesystem.service.impl.core.issue.IssueStateService;
import mb.pso.issuesystem.utils.JwtUtils;

//[ ] REFACTOR
@RestController
@RequestMapping("/issues/{issueId}")
public class IssueStateController {

    private final DtoMapper mapper;
    private final IssueStateService issueStateService;

    public IssueStateController(DtoMapper mapper, IssueStateService issueStateService) {
        this.mapper = mapper;
        this.issueStateService = issueStateService;
    }

    @PostMapping("/closed")
    public ResponseEntity<IssueDto> setClosed(@PathVariable Integer issueId) {
        Issue updatedIssue = issueStateService.setClosed(issueId);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(mapper.toDto(updatedIssue, IssueDto.class));
    }

    @PostMapping("/in-progress")
    public ResponseEntity<IssueDto> setInProgress(@AuthenticationPrincipal Jwt jwt, @PathVariable Integer issueId) {
        String displayName = JwtUtils.extractDisplayName(jwt);
        Issue updatedIssue = issueStateService.setInProgress(issueId, displayName);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(mapper.toDto(updatedIssue, IssueDto.class));
    }

    @PostMapping("/pending")
    public ResponseEntity<IssueDto> setPendingResult(@PathVariable Integer issueId) {
        Issue updatedIssue = issueStateService.setPending(issueId);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(mapper.toDto(updatedIssue, IssueDto.class));
    }

}
