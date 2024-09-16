package mb.pso.issuesystem.listeners;


import org.springframework.stereotype.Component;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.es.IssueDocument;
import mb.pso.issuesystem.repository.es.IssueDocumentRepository;

@Component
public class IssueEntityListener  {
    private IssueDocumentRepository issueDocumentRepository;

    public IssueEntityListener(IssueDocumentRepository issueDocumentRepository) {
        this.issueDocumentRepository = issueDocumentRepository;
    }

    @PostPersist
    @PostUpdate
    public void handlePostPersistOrPostUpdate(Issue issue) {
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
    }

    @PostRemove
    public void handlePostRemove(Issue issue) {
        issueDocumentRepository.deleteById(issue.getId());
    }

}
