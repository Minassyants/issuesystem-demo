package mb.pso.issuesystem.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;

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
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<Users> get(String id) {
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
