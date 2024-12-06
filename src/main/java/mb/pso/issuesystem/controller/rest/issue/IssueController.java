package mb.pso.issuesystem.controller.rest.issue;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import mb.pso.issuesystem.entity.AdUserDetails;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.IssueAttribute;
import mb.pso.issuesystem.entity.Subject;
import mb.pso.issuesystem.service.impl.core.IssueDocumentService;
import mb.pso.issuesystem.service.impl.core.issue.IssueService;

//[ ] REFACTOR
@RestController
@RequestMapping("/issues")
public class IssueController {
    private final IssueService issueService;
    private final IssueDocumentService issueDocumentServiceImpl;

    public IssueController(IssueService issueService, IssueDocumentService issueDocumentServiceImpl) {
        this.issueService = issueService;
        this.issueDocumentServiceImpl = issueDocumentServiceImpl;
    }

    @GetMapping
    public ResponseEntity<Page<Issue>> getAllIssuesPageable(@AuthenticationPrincipal Jwt jwt, @RequestParam int page,
            @RequestParam int size, @RequestParam Optional<String> q, @RequestParam Optional<List<String>> sf) {
        Page<Issue> issues = issueService
                .getAll(PageRequest.of(page, size, Sort.by(Direction.DESC, "id")), jwt, q, sf);
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Integer issueId,
            @AuthenticationPrincipal AdUserDetails user) {
        Issue issue = issueService.getOrThrow(issueId);
        return ResponseEntity.ok(issue);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('operator')")
    public ResponseEntity<Issue> registerNewIssue(@RequestBody Issue issue) {
        Issue createdIssue = issueService.registerNewIssue(issue);
        return ResponseEntity.ok(createdIssue);
    }

    @PostMapping("/{issueId}/issue-attributes")
    public ResponseEntity<Issue> updateIssueAttributes(@PathVariable Integer issueId,
            @RequestBody List<IssueAttribute> issueAttributes) {
        Issue updateIssue = issueService.updateIssueAttributes(issueId, issueAttributes);
        return ResponseEntity.ok(updateIssue);
    }

    @PostMapping("/{issueId}/subject")
    public ResponseEntity<Issue> updateIssueSubject(@PathVariable Integer issueId, @RequestBody Subject subject) {
        Issue updatedIssue = issueService.updateIssueSubject(issueId, subject);
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/{issueId}/issue-result")
    public ResponseEntity<Issue> updateIssueResult(@PathVariable Integer issueId, @RequestBody String issueResult) {
        Issue updatedIssue = issueService.updateIssueResult(issueId, issueResult);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/{issueId}/attached-files")
    public ResponseEntity<Issue> uploadFilesToIssue(@PathVariable Integer issueId,
            @RequestParam MultipartFile[] files) {
        Issue issue = issueService.uploadFilesToIssue(issueId, files);
        if (issue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(issue);
    }

    @PostMapping("/reindex")
    public void reindexSearch() {
        Iterable<Issue> issues = issueService.getAll();
        issues.forEach(issue -> {
            issueDocumentServiceImpl.convertAndSave(issue);
        });
    }

}
