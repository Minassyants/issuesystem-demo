package mb.pso.issuesystem.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mb.pso.issuesystem.entity.Client;
import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.Subject;
import mb.pso.issuesystem.entity.Vehicle;
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
            Client client = issue.getClient();
            Subject subject = issue.getSubject();
            Employee employee = issue.getIssuedEmployee();
            IssueDocument issueDocument = new IssueDocument(
                    issue.getId(),
                    issue.getStatus(),
                    issue.getDocDate(),
                    client.getName(),
                    client.getAddress(),
                    client.getEmail(),
                    client.getPhoneNumber(),
                    issue.getType().getName(),
                    subject.getDescription(),
                    subject instanceof Vehicle ? ((Vehicle) subject).getVin() : null,
                    subject instanceof Vehicle ? "vehicle" : "good",
                    issue.getIssueAttributes().stream().map(arg0 -> arg0.getName()).toList(),
                    issue.getIssueDescription(),
                    issue.getIssuedDepartment().getName(),
                    employee.getGivenName(),
                    employee.getSn(),
                    employee.getMail(),
                    issue.getIssuedDemands(),
                    issue.getAdditionalAttributes().stream().map(arg0 -> arg0.getStringValue()).toList(),
                    issue.getDepartmentFeedback(),
                    issue.getIssueResult());
            issueDocumentRepository.save(issueDocument);
        });
    }

}
