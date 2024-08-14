package mb.pso.issuesystem.service;

import java.util.Optional;

import mb.pso.issuesystem.entity.Users;

public interface UsersService {
    public Users create(Users user);

    public Users update(Users user);

    public void delete(Users user);

    public void deleteById(String id);

    public Optional<Users> get(String id);

    public Optional<Users> getByName(String name);

    public Iterable<Users> getAll();
}
