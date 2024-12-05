package mb.pso.issuesystem.service.impl.core;

import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.DepartmentFeedback;
import mb.pso.issuesystem.repository.core.CombinedRepository;
import mb.pso.issuesystem.repository.core.DepartmentFeedbackRepository;
import mb.pso.issuesystem.service.AbstractCrudService;

//[ ] REFACTOR
@Service
public class DepartmentFeedbackService extends AbstractCrudService<DepartmentFeedback, Integer> {

    private final DepartmentFeedbackRepository repository;

    public DepartmentFeedbackService(DepartmentFeedbackRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Integer getId(DepartmentFeedback entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<DepartmentFeedback, Integer> getRepository() {
        return repository;
    }

}
