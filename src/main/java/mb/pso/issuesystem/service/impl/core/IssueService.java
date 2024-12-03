package mb.pso.issuesystem.service.impl.core;

import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.repository.CombinedRepository;
import mb.pso.issuesystem.repository.IssueRepository;
import mb.pso.issuesystem.service.AbstractCrudService;

//[x] REFACTOR
@Service
public class IssueService extends AbstractCrudService<Issue, Integer> {

    private final IssueRepository repository;

    public IssueService(IssueRepository repository) {
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

}
