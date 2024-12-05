package mb.pso.issuesystem.service.impl.core;

import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.IssueType;
import mb.pso.issuesystem.entity.QIssueType;
import mb.pso.issuesystem.repository.core.CombinedRepository;
import mb.pso.issuesystem.repository.core.IssueTypeRepository;
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

    public Iterable<IssueType> getAvailableIssueTypes() {
        Predicate predicate = QIssueType.issueType.isDeprecated.eq(false);
        return repository.findAll(predicate);
    }

}
