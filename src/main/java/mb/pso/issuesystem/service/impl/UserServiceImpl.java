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

import mb.pso.issuesystem.entity.Users;
import mb.pso.issuesystem.repository.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService {
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
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

}
