package mb.pso.issuesystem.service;

import mb.pso.issuesystem.entity.Subject;
import mb.pso.issuesystem.entity.Vehicle;

public interface VehicleService {
    public Vehicle create(Vehicle vehicle);

    public Vehicle update(Vehicle vehicle);

    public void delete(Vehicle vehicle);

    public Subject get(Integer id);

    public Iterable<Subject> getAll();
}
