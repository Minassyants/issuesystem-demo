package mb.pso.issuesystem.service.impl.core;

import java.util.List;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.IssueAttribute;
import mb.pso.issuesystem.entity.QIssueAttribute;
import mb.pso.issuesystem.repository.core.CombinedRepository;
import mb.pso.issuesystem.repository.core.IssueAttributeRepository;
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

    public List<IssueAttribute> getAvailableIssueAttributes() {
        Predicate predicate = QIssueAttribute.issueAttribute.isDeprecated.eq(false);
        return repository.findAll(predicate);
    }

}
