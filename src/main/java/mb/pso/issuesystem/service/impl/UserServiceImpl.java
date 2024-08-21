package mb.pso.issuesystem.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.Users;
import mb.pso.issuesystem.repository.UserRepository;
import mb.pso.issuesystem.service.UsersService;

@Service
public class UserServiceImpl implements UsersService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Users create(Users user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Users user) {
        userRepository.delete(user);

    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<Users> getByName(String name) {
        
        return Optional.of(userRepository.findByUsername(name).get(0));
    }

    @Override
    public Optional<Users> get(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Iterable<Users> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Users update(Users user) {
        Optional<Users> u = userRepository.findById(user.getId());
        assert u.isPresent();
        return userRepository.save(user);
    }

}
