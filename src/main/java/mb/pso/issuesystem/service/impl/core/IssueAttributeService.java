package mb.pso.issuesystem.service.impl.core;

import mb.pso.issuesystem.entity.IssueAttribute;
import mb.pso.issuesystem.repository.CombinedRepository;
import mb.pso.issuesystem.repository.IssueAttributeRepository;
import mb.pso.issuesystem.service.AbstractCrudService;

//[x] REFACTOR
public class IssueAttributeService extends AbstractCrudService<IssueAttribute, Integer> {
    private final IssueAttributeRepository repository;

    public IssueAttributeService(IssueAttributeRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Integer getId(IssueAttribute entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<IssueAttribute, Integer> getRepository() {
        return repository;
    }

}
