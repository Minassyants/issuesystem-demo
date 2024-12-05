package mb.pso.issuesystem.service.impl.core.issue;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.DepartmentFeedback;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.enums.IssueStatus;
import mb.pso.issuesystem.exceptions.IllegalActionException;
import mb.pso.issuesystem.repository.core.CombinedRepository;
import mb.pso.issuesystem.repository.core.IssueRepository;
import mb.pso.issuesystem.service.AbstractCrudService;

//[ ] REFACTOR
@Service
public class IssueFeedbackService extends AbstractCrudService<Issue, Integer> {

    private final IssueRepository repository;

    public IssueFeedbackService(IssueRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Integer getId(Issue entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<Issue, Integer> getRepository() {
        return repository;
    }

    public Set<DepartmentFeedback> getDepartmentFeedbacksByIssueId(Integer issueId) {
        return getOrThrow(issueId).getDepartmentFeedbacks();
    }

    public Issue addToDepartmentFeedbacks(Integer issueId, DepartmentFeedback departmentFeedback) {
        Issue issue = getOrThrow(issueId);

        if (issue.getStatus() != IssueStatus.INPROGRESS)
            throw new IllegalActionException("Feedback can be added only while INPROGRESS");

        issue.addDepartmentFeedbacks(departmentFeedback);

        return update(issue);
    }

    public Issue updateDepartmentFeedbacks(Integer issueId, List<DepartmentFeedback> departmentFeedbacks) {
        Issue issue = getOrThrow(issueId);

        if (issue.getStatus() != IssueStatus.INPROGRESS)
            throw new IllegalActionException("Feedback can be added only while INPROGRESS");

        issue.setDepartmentFeedbacks(departmentFeedbacks);

        return update(issue);
    }

}
