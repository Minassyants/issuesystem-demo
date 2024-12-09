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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import mb.pso.issuesystem.dto.core.IssueDto;
import mb.pso.issuesystem.entity.core.AdUserDetails;
import mb.pso.issuesystem.entity.core.Issue;
import mb.pso.issuesystem.entity.core.IssueAttribute;
import mb.pso.issuesystem.entity.core.Subject;
import mb.pso.issuesystem.service.impl.core.DtoMapper;
import mb.pso.issuesystem.service.impl.core.IssueDocumentService;
import mb.pso.issuesystem.service.impl.core.issue.IssueService;


@Tag(name = "Issues", description = "Operations related to Issues")
@RestController
@RequestMapping("/issues")
public class IssueController {
    private final IssueService issueService;
    private final IssueDocumentService issueDocumentService;
    private final DtoMapper mapper;

    public IssueController(IssueService issueService, IssueDocumentService issueDocumentService, DtoMapper mapper) {
        this.issueService = issueService;
        this.issueDocumentService = issueDocumentService;
        this.mapper = mapper;
    }

    @Operation(summary = "Get all users", description = "Return a pageable list of users")
    @GetMapping
    public ResponseEntity<Page<IssueDto>> getAllIssuesPageable(@AuthenticationPrincipal Jwt jwt,
            @RequestParam int page,
            @RequestParam int size,
            @Parameter(description = "Query for user search") @RequestParam Optional<String> q,
            @Parameter(description = "Specify a list of search fields for Elasticsearch. If no fields are provided, all fields will be used by default") @RequestParam Optional<List<String>> sf) {
        Page<Issue> issues = issueService
                .getAll(PageRequest.of(page, size, Sort.by(Direction.DESC, "id")), jwt, q, sf);
        return ResponseEntity.ok(mapper.toDtoPage(issues, IssueDto.class));
    }

    @Operation(summary = "Get issue by its ID", description = "Return issue by its ID")
    @GetMapping("/{issueId}")
    public ResponseEntity<IssueDto> getIssueById(@PathVariable Integer issueId,
            @AuthenticationPrincipal AdUserDetails user) {
        Issue issue = issueService.getOrThrow(issueId);
        return ResponseEntity.ok(mapper.toDto(issue, IssueDto.class));
    }

    @Operation(summary = "Creates new issue", description = "Creates new issue and returns created entity")
    @PostMapping
    @PreAuthorize("hasAuthority('operator')")
    public ResponseEntity<IssueDto> registerNewIssue(@RequestBody Issue issue) {
        Issue createdIssue = issueService.registerNewIssue(issue);
        return ResponseEntity.ok(mapper.toDto(createdIssue, IssueDto.class));
    }

    @Operation(summary = "Updates issue attributes", description = "Replaces issue attributes with provided list of new issue attributes")
    @PostMapping("/{issueId}/issue-attributes")
    public ResponseEntity<IssueDto> updateIssueAttributes(@PathVariable Integer issueId,
            @RequestBody List<IssueAttribute> issueAttributes) {
        Issue updateIssue = issueService.updateIssueAttributes(issueId, issueAttributes);
        return ResponseEntity.ok(mapper.toDto(updateIssue, IssueDto.class));
    }

    @Operation(summary = "Updates issue's subject", description = "Replaces issue's subject with provided one")
    @PostMapping("/{issueId}/subject")
    public ResponseEntity<IssueDto> updateIssueSubject(@PathVariable Integer issueId, @RequestBody Subject subject) {
        Issue updatedIssue = issueService.updateIssueSubject(issueId, subject);
        return ResponseEntity.ok(mapper.toDto(updatedIssue, IssueDto.class));
    }

    @Operation(summary = "Updates issue's result", description = "Replaces issue's result with provided one")
    @PostMapping("/{issueId}/issue-result")
    public ResponseEntity<IssueDto> updateIssueResult(@PathVariable Integer issueId, @RequestBody String issueResult) {
        Issue updatedIssue = issueService.updateIssueResult(issueId, issueResult);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(mapper.toDto(updatedIssue, IssueDto.class));
    }

    @Operation(summary = "Adds attached file to issue", description = "Adds provided file to the issue")
    @PostMapping("/{issueId}/attached-files")
    public ResponseEntity<IssueDto> uploadFilesToIssue(@PathVariable Integer issueId,
            @RequestParam MultipartFile[] files) {
        Issue issue = issueService.uploadFilesToIssue(issueId, files);
        if (issue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(mapper.toDto(issue, IssueDto.class));
    }

    @Operation(summary = "Reindexes elasticsearch index", description = "Recreates elasticsearch index with all issues")
    @PostMapping("/reindex")
    public void reindexSearch() {
        Iterable<Issue> issues = issueService.getAll();
        issues.forEach(issue -> {
            issueDocumentService.convertAndSave(issue);
        });
    }

}
