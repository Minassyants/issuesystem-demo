package mb.pso.issuesystem.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.es.IssueDocument;
import mb.pso.issuesystem.repository.es.IssueDocumentRepository;
import mb.pso.issuesystem.service.impl.IssueServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/issue")
public class IssueController {
    private final IssueServiceImpl issueServiceImpl;
    // TODO должен быть сервис у IssueDocument
    private final IssueDocumentRepository issueDocumentRepository;

    public IssueController(IssueServiceImpl issueServiceImpl, IssueDocumentRepository issueDocumentRepository) {
        this.issueServiceImpl = issueServiceImpl;
        this.issueDocumentRepository = issueDocumentRepository;
    }

    @PostMapping
    public ResponseEntity<Issue> create(@RequestBody Issue issue) {
        Issue i = issueServiceImpl.create(issue);

        return ResponseEntity.ok(i);
    }

    @GetMapping("/reindex")
    public void reindexSearch() {
        Iterable<Issue> issues = issueServiceImpl.getAll();
        issues.forEach(issue -> {
            IssueDocument issueDocument = new IssueDocument();
            issueDocument.setId(issue.getId());
            issueDocument.setStatus(issue.getStatus());
            issueDocument.setDocDate(issue.getDocDate());
            issueDocument.setClient(issue.getClient());
            issueDocument.setType(issue.getType());
            issueDocument.setSubject(issue.getSubject());
            issueDocument.setIssueAttributes(issue.getIssueAttributes());
            issueDocument.setIssueDescription(issue.getIssueDescription());
            issueDocument.setIssuedDepartment(issue.getIssuedDepartment());
            issueDocument.setIssuedEmployee(issue.getIssuedEmployee());
            issueDocument.setIssuedDemands(issue.getIssuedDemands());
            issueDocument.setAdditionalAttributes(issue.getAdditionalAttributes());
            issueDocument.setDepartmentFeedback(issue.getDepartmentFeedback());
            issueDocument.setIssueResult(issue.getIssueResult());
            issueDocumentRepository.save(issueDocument);
        });
    }

}
