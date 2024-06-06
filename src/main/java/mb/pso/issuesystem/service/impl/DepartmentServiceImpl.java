package mb.pso.issuesystem.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.Department;
import mb.pso.issuesystem.repository.DepartmentRepository;
import mb.pso.issuesystem.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department create(Department department) {
        Department d = departmentRepository.save(department);
        return d;
    }

    @Override
    public void delete(Department department) {
        departmentRepository.delete(department);

    }

    @Override
    public Optional<Department> get(String id) {
        Optional<Department> d = departmentRepository.findById(id);
        return d;
    }

    @Override
    public Iterable<Department> getAll() {
        
        return departmentRepository.findAll();
    }

    @Override
    public Department update(Department department) {
        Optional<Department> d = departmentRepository.findById(department.getId());
        assert d.isPresent();
        return departmentRepository.save(department);
    }

}
