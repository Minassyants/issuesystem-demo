package mb.pso.issuesystem.service.impl;

import java.util.Optional;

import mb.pso.issuesystem.entity.Good;
import mb.pso.issuesystem.entity.Subject;
import mb.pso.issuesystem.entity.Vehicle;
import mb.pso.issuesystem.repository.SubjectRepository;
import mb.pso.issuesystem.service.GoodService;
import mb.pso.issuesystem.service.VehicleService;

public class SubjectServiceImpl implements VehicleService, GoodService {

    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Vehicle create(Vehicle vehicle) {

        return subjectRepository.save(vehicle);
    }

    @Override
    public void delete(Vehicle vehicle) {
        subjectRepository.delete(vehicle);

    }

    @Override
    public Subject get(Integer id) {

        return null;
    }

    @Override
    public Iterable<Subject> getAll() {

        return subjectRepository.findAll();
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        Optional<Subject> v = subjectRepository.findById(vehicle.getId());
        assert v.isPresent();
        return subjectRepository.save(vehicle);
    }

    @Override
    public Good create(Good good) {
        return subjectRepository.save(good);
    }

    @Override
    public void delete(Good good) {
        subjectRepository.delete(good);

    }

    @Override
    public Good update(Good good) {
        Optional<Subject> g = subjectRepository.findById(good.getId());
        assert g.isPresent();
        return subjectRepository.save(good);
    }

}
