package mb.pso.issuesystem.listeners;

import org.springframework.stereotype.Component;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import mb.pso.issuesystem.entity.Client;
import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.Subject;
import mb.pso.issuesystem.entity.Vehicle;
import mb.pso.issuesystem.entity.es.IssueDocument;
import mb.pso.issuesystem.repository.es.IssueDocumentRepository;

@Component
public class IssueEntityListener {
    private IssueDocumentRepository issueDocumentRepository;

    public IssueEntityListener(IssueDocumentRepository issueDocumentRepository) {
        this.issueDocumentRepository = issueDocumentRepository;
    }

    @PostPersist
    @PostUpdate
    public void handlePostPersistOrPostUpdate(Issue issue) {
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
    }

    @PostRemove
    public void handlePostRemove(Issue issue) {
        issueDocumentRepository.deleteById(issue.getId());
    }

}
