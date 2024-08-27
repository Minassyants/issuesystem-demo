package mb.pso.issuesystem.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import mb.pso.issuesystem.entity.Users;
import mb.pso.issuesystem.repository.UserRepository;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private final UserRepository userRepository;

    public CommandLineAppStartupRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Users user = new Users("alexandr.minassyants@mercedes-benz.kz", "admin", "$2a$10$bUo1HZovBZKHDbSbZGQdee392mH9NLMzbGBcKvUtVWsoAPDb094Qa", null);
        if (userRepository.findOne(Example.of(new Users(null,"admin",null,null))).isEmpty())
            userRepository.save(user);
    }
}