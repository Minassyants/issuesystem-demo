package mb.pso.issuesystem.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.AdUserDetails;
import mb.pso.issuesystem.entity.Users;
import mb.pso.issuesystem.repository.UserRepository;
import mb.pso.issuesystem.service.UsersService;

@Service
public class UserServiceImpl implements UserDetailsService, UsersService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Users exUser = new Users();
        exUser.setUsername(username);

        Optional<Users> _user = userRepository.findOne(Example.of(exUser));
        if (!_user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        Users user = _user.get();
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(arg0 -> new SimpleGrantedAuthority(arg0.name().toLowerCase())).toList();
        return new AdUserDetails(user.getUsername(), user.getPassword(), authorities, user.getEmail());
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
