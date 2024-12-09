package mb.pso.issuesystem.service.impl.core;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.core.AdUserDetails;
import mb.pso.issuesystem.entity.core.Users;
import mb.pso.issuesystem.repository.core.CombinedRepository;
import mb.pso.issuesystem.repository.core.UserRepository;
import mb.pso.issuesystem.service.AbstractCrudService;


@Service
public class UserService extends AbstractCrudService<Users, Integer> implements UserDetailsService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Users exUser = new Users();
        exUser.setUsername(username);

        Optional<Users> _user = repository.findOne(Example.of(exUser));
        if (!_user.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        Users user = _user.get();
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(arg0 -> new SimpleGrantedAuthority(arg0.name().toLowerCase())).toList();
        return new AdUserDetails(user.getUsername(), user.getPassword(), authorities, user.getEmail(),
                user.getUsername(), user.getUsername(), user.getUsername(), user.getsAMAccountName());
    }

    @Override
    protected Integer getId(Users entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<Users, Integer> getRepository() {
        return repository;
    }

    public Users getByName(String name) {

        return repository.findByUsername(name);
    }

}
