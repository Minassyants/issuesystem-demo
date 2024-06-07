package mb.pso.issuesystem.service.impl;

import java.util.Optional;

import mb.pso.issuesystem.entity.User;
import mb.pso.issuesystem.repository.UserRepository;
import mb.pso.issuesystem.service.UserService;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {

        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);

    }

    @Override
    public Optional<User> get(String id) {

        return userRepository.findById(id);
    }

    @Override
    public Iterable<User> getAll() {

        return userRepository.findAll();
    }

    @Override
    public User update(User user) {
        Optional<User> u = userRepository.findById(user.getId());
        assert u.isPresent();
        return userRepository.save(user);
    }

}
