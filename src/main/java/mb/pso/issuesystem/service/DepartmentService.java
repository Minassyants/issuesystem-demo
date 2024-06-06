package mb.pso.issuesystem.service;

import java.util.Optional;

import mb.pso.issuesystem.entity.Department;

public interface DepartmentService {
    public Department create(Department department);

    public Department update(Department department);

    public void delete(Department department);

    public Department get(String id);

    public Optional<Department> findByName(String name);

    public Iterable<Department> getAll();
}
