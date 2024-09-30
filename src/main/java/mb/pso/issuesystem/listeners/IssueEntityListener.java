package mb.pso.issuesystem.listeners;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import mb.pso.issuesystem.entity.AdditionalAttribute;
import mb.pso.issuesystem.entity.Client;
import mb.pso.issuesystem.entity.Department;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.IssueAttribute;
import mb.pso.issuesystem.entity.IssueType;
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
        IssueType issueType = issue.getType();
        List<IssueAttribute> issueAttributes = issue.getIssueAttributes();
        Department department = issue.getIssuedDepartment();
        List<AdditionalAttribute> additionalAttributes = issue.getAdditionalAttributes();
        IssueDocument issueDocument = new IssueDocument(
                issue.getId(),
                issue.getStatus(),
                issue.getDocDate(),
                client == null ? null : client.getName(),
                client == null ? null : client.getAddress(),
                client == null ? null : client.getEmail(),
                client == null ? null : client.getPhoneNumber(),
                issueType == null ? null : issueType.getName(),
                subject == null ? null : subject.getDescription(),
                subject == null ? null : subject instanceof Vehicle ? ((Vehicle) subject).getVin() : null,
                subject == null ? null : subject instanceof Vehicle ? "vehicle" : "good",
                issueAttributes == null ? null : issueAttributes.stream().map(arg0 -> arg0.getName()).toList(),
                issue.getIssueDescription(),
                department == null ? null : department.getName(),
                issue.getIssuedEmployees().stream().map(t -> t.toString()).toList(),
                issue.getDepartmentFeedbacks().stream().map(t -> t.getText()).toList(),
                issue.getIssuedDemands(),
                additionalAttributes == null ? null
                        : additionalAttributes.stream().map(arg0 -> arg0.getStringValue()).toList(),
                issue.getIssueResult());
        issueDocumentRepository.save(issueDocument);
    }

    @PostRemove
    public void handlePostRemove(Issue issue) {
        issueDocumentRepository.deleteById(issue.getId());
    }

}
