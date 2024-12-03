package mb.pso.issuesystem.service.impl.core;

import java.util.List;

import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.AdditionalAttribute;
import mb.pso.issuesystem.entity.Client;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.IssueAttribute;
import mb.pso.issuesystem.entity.IssueType;
import mb.pso.issuesystem.entity.Subject;
import mb.pso.issuesystem.entity.Vehicle;
import mb.pso.issuesystem.entity.es.IssueDocument;
import mb.pso.issuesystem.entity.es.IssueDocument.Builder;
import mb.pso.issuesystem.repository.es.IssueDocumentRepository;

//[x] REFACTOR
@Service
public class IssueDocumentService {

    private final IssueDocumentRepository repository;

    public IssueDocumentService(IssueDocumentRepository repository) {
        this.repository = repository;
    }

    public void convertAndSave(Issue issue) {
        Client client = issue.getClient();
        Subject subject = issue.getSubject();
        IssueType issueType = issue.getType();
        List<IssueAttribute> issueAttributes = issue.getIssueAttributes();
        List<AdditionalAttribute> additionalAttributes = issue.getAdditionalAttributes();

        Builder issueDocumentBuilder = new IssueDocument.Builder()
                .id(issue.getId())
                .status(issue.getStatus())
                .docDate(issue.getDocDate())
                .issueDescription(issue.getIssueDescription())
                .issuedEmployees(issue.getIssuedEmployees().stream().map(t -> t.toString()).toList())
                .departmentFeedbacks(issue.getDepartmentFeedbacks().stream().map(t -> t.getText()).toList())
                .issuedDemands(issue.getIssuedDemands())
                .issueResult(issue.getIssueResult());
        if (client != null) {
            issueDocumentBuilder
                    .clientName(client.getName())
                    .clientAddress(client.getAddress())
                    .clientEmail(client.getEmail())
                    .clientPhoneNumber(client.getPhoneNumber());
        }
        if (issueType != null) {
            issueDocumentBuilder
                    .type(issueType.getName());
        }
        if (subject != null) {
            issueDocumentBuilder
                    .subjectDescription(subject.getDescription())
                    .subjectVin(subject instanceof Vehicle ? ((Vehicle) subject).getVin() : null)
                    .subjectType(subject instanceof Vehicle ? "vehicle" : "good");
        }
        if (issueAttributes != null) {
            issueDocumentBuilder.issueAttributes(issueAttributes.stream().map(arg0 -> arg0.getName()).toList());
        }
        if (additionalAttributes != null) {
            issueDocumentBuilder.additionalAttributes(
                    additionalAttributes.stream().map(arg0 -> arg0.getStringValue()).toList());
        }

        IssueDocument issueDocument = issueDocumentBuilder.build();

        repository.save(issueDocument);
    }
}
