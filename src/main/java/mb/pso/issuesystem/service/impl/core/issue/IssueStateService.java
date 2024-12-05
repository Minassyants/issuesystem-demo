package mb.pso.issuesystem.service.impl.core.issue;

import java.util.Optional;

import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.AdUser;
import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.enums.IssueStatus;
import mb.pso.issuesystem.exceptions.IllegalActionException;
import mb.pso.issuesystem.repository.core.CombinedRepository;
import mb.pso.issuesystem.repository.core.IssueRepository;
import mb.pso.issuesystem.service.AbstractCrudService;
import mb.pso.issuesystem.service.impl.core.EmployeeService;
import mb.pso.issuesystem.service.impl.ldap.AdUserService;

//[ ] REFACTOR
@Service
public class IssueStateService extends AbstractCrudService<Issue, Integer> {
    private final IssueRepository repository;
    private final EmployeeService employeeService;
    private final AdUserService adUserService;

    public IssueStateService(IssueRepository repository, EmployeeService employeeService, AdUserService adUserService) {
        this.repository = repository;
        this.employeeService = employeeService;
        this.adUserService = adUserService;
    }

    @Override
    protected Integer getId(Issue entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<Issue, Integer> getRepository() {
        return repository;
    }

    public Issue setInProgress(Integer issueId, String displayName) {
        Issue issue = getOrThrow(issueId);
        Optional<Employee> author = employeeService.get(displayName);

        assertStatus(issue, IssueStatus.NEW, "INPROGRESS can be only set from NEW");

        if (author.isEmpty()) {
            AdUser foundUser = adUserService.getByDisplayNameOrThrow(displayName);
            author = Optional.of(new Employee(foundUser.getDisplayName(), foundUser.getGivenName(), foundUser.getSn(),
                    foundUser.getMail()));
        }

        issue.setStatus(IssueStatus.INPROGRESS);
        issue.getChat().addToMembers(author.get());

        return update(issue);

    }

    public Issue setPending(Integer issueId) {
        Issue issue = getOrThrow(issueId);

        assertStatus(issue, IssueStatus.INPROGRESS, "PENDINGRESULT can be only set from INPROGRESS");

        issue.setStatus(IssueStatus.PENDINGRESULT);
        issue.getChat().setIsClosed(true);

        return update(issue);
    }

    public Issue setClosed(Integer issueId) {
        Issue issue = getOrThrow(issueId);

        assertStatus(issue, IssueStatus.PENDINGRESULT, "CLOSED can be only set from PRENDINGRESULT");

        issue.setStatus(IssueStatus.CLOSED);

        return update(issue);
    }

    public void assertStatus(Issue issue, IssueStatus expectedStatus, String errorMessage) {
        if (issue.getStatus() != expectedStatus) {
            throw new IllegalActionException(errorMessage);
        }
    }

}
