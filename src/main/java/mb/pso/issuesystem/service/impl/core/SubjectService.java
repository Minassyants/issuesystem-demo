package mb.pso.issuesystem.service.impl.core;

import mb.pso.issuesystem.entity.Subject;
import mb.pso.issuesystem.repository.CombinedRepository;
import mb.pso.issuesystem.repository.SubjectRepository;
import mb.pso.issuesystem.service.AbstractCrudService;

//[x] REFACTOR
public class SubjectService extends AbstractCrudService<Subject, Integer> {

    private final SubjectRepository repository;

    public SubjectService(SubjectRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Integer getId(Subject entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<Subject, Integer> getRepository() {
        return repository;
    }

}
