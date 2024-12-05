package mb.pso.issuesystem.service.impl.core;

import org.springframework.data.domain.Example;

import mb.pso.issuesystem.entity.Subject;
import mb.pso.issuesystem.repository.core.CombinedRepository;
import mb.pso.issuesystem.repository.core.SubjectRepository;
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

    public Subject resolve(Subject subject) {
        return get(Example.of(subject)).orElse(subject);
    }

}
