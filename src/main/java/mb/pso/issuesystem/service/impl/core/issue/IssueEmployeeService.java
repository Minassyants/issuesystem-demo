package mb.pso.issuesystem.service.impl.core.issue;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.core.Employee;
import mb.pso.issuesystem.entity.core.Issue;
import mb.pso.issuesystem.entity.enums.IssueStatus;
import mb.pso.issuesystem.exceptions.IllegalActionException;
import mb.pso.issuesystem.repository.core.CombinedRepository;
import mb.pso.issuesystem.repository.core.IssueRepository;
import mb.pso.issuesystem.service.AbstractCrudService;
import mb.pso.issuesystem.service.impl.core.EmployeeService;


@Service
public class IssueEmployeeService extends AbstractCrudService<Issue, Integer> {

    private final IssueRepository repository;
    private final EmployeeService employeeService;

    public IssueEmployeeService(IssueRepository repository, EmployeeService employeeService) {
        this.repository = repository;
        this.employeeService = employeeService;
    }

    @Override
    protected Integer getId(Issue entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<Issue, Integer> getRepository() {
        return repository;
    }

    public Set<Employee> getIssuedEmployeesByIssueId(Integer issueId) {
        return getOrThrow(issueId).getIssuedEmployees();
    }

    public Issue addToIssuedEmployees(Integer issueId, Employee employee) {
        Issue issue = getOrThrow(issueId);

        if (issue.getStatus() != IssueStatus.INPROGRESS)
            throw new IllegalActionException("Employee can be added only while INPROGRESS");

        Employee foundEmployee = employeeService.getOrThrow(employee.getDisplayName());
        issue.addIssuedEmployees(foundEmployee);

        return update(issue);
    }

    public Issue updateIssuedEmployees(Integer issueId, List<Employee> employees) {
        Issue issue = getOrThrow(issueId);

        if (issue.getStatus() != IssueStatus.INPROGRESS)
            throw new IllegalActionException("Employee can be added only while INPROGRESS");

        List<Employee> foundEmployees = employees.stream()
                .map(t -> employeeService.getOrThrow(t.getDisplayName())).toList();

        issue.setIssuedEmployees(foundEmployees);

        return update(issue);

    }

}
