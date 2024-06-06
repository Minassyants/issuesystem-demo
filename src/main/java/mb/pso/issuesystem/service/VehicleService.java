package mb.pso.issuesystem.service;

import java.util.Optional;

import mb.pso.issuesystem.entity.Vehicle;

public interface VehicleService {
    public Vehicle create(Vehicle vehicle);

    public Vehicle update(Vehicle vehicle);

    public void delete(Vehicle vehicle);

    public Vehicle get(String id);

    public Optional<Vehicle> findByName(String name);

    public Iterable<Vehicle> getAll();
}
