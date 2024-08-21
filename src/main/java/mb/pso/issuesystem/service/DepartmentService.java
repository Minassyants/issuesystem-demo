package mb.pso.issuesystem.service;

import java.util.Optional;

import mb.pso.issuesystem.entity.Department;

public interface DepartmentService {
    public Department create(Department department);

    public Department update(Department department);

    public void delete(Department department);

    public Optional<Department> get(Integer id);

    public Iterable<Department> getAll();
}
