package mb.pso.issuesystem.service;

import java.util.Optional;

import mb.pso.issuesystem.entity.User;

public interface UserService {
    public User create(User user);

    public User update(User user);

    public void delete(User user);

    public Optional<User> get(String id);

    public Iterable<User> getAll();
}
