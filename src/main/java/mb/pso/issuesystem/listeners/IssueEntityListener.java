package mb.pso.issuesystem.listeners;

import org.springframework.stereotype.Component;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import mb.pso.issuesystem.entity.core.Issue;
import mb.pso.issuesystem.service.impl.core.IssueDocumentService;


@Component
public class IssueEntityListener {
    private final IssueDocumentService issueDocumentService;

    public IssueEntityListener(IssueDocumentService issueDocumentService) {
        this.issueDocumentService = issueDocumentService;
    }

    @PostPersist
    @PostUpdate
    public void handlePostPersistOrPostUpdate(Issue issue) {
        issueDocumentService.convertAndSave(issue);
    }

    @PostRemove
    public void handlePostRemove(Issue issue) {
        issueDocumentService.deleteById(issue.getId());
    }

}
