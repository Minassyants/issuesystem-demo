package mb.pso.issuesystem.service.impl.core;

import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.IssueType;
import mb.pso.issuesystem.repository.CombinedRepository;
import mb.pso.issuesystem.repository.IssueTypeRepository;
import mb.pso.issuesystem.service.AbstractCrudService;

//[x] REFACTOR
@Service
public class IssueTypeService extends AbstractCrudService<IssueType, Integer> {

    private final IssueTypeRepository repository;

    public IssueTypeService(IssueTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Integer getId(IssueType entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<IssueType, Integer> getRepository() {
        return repository;
    }

}
