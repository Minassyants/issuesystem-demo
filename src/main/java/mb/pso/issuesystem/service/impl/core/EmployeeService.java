package mb.pso.issuesystem.service.impl.core;

import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.core.Employee;
import mb.pso.issuesystem.repository.core.CombinedRepository;
import mb.pso.issuesystem.repository.core.EmployeeRepository;
import mb.pso.issuesystem.service.AbstractCrudService;


@Service
public class EmployeeService extends AbstractCrudService<Employee, String> {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    protected String getId(Employee entity) {
        return entity.getDisplayName();
    }

    @Override
    protected CombinedRepository<Employee, String> getRepository() {
        return repository;
    }

    @Override
    public Employee create(Employee entity) {
        String id = getId(entity);
        if (getRepository().existsById(id)) {
            throw new IllegalArgumentException("Cannot create entity with an existing ID");
        }
        return getRepository().save(entity);
    }

}
