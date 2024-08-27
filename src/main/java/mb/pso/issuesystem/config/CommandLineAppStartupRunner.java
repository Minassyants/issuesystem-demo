package mb.pso.issuesystem.config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.Users;
import mb.pso.issuesystem.repository.IssueRepository;
import mb.pso.issuesystem.repository.UserRepository;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private final UserRepository userRepository;
    private final IssueRepository issueRepository;

    public CommandLineAppStartupRunner(UserRepository userRepository, IssueRepository issueRepository) {
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Users user = new Users("alexandr.minassyants@mercedes-benz.kz", "admin",
                "$2a$10$bUo1HZovBZKHDbSbZGQdee392mH9NLMzbGBcKvUtVWsoAPDb094Qa", null);
        if (userRepository.findOne(Example.of(new Users(null, "admin", null, null))).isEmpty())
            userRepository.save(user);
        // List<Issue> a = new ArrayList<Issue>();

        // for (int i = 0; i < 50; i++) {
        //     Issue q = new Issue();
        //     q.setIssueDescription(UUID.randomUUID().toString());
        //     q.setIssueResult(UUID.randomUUID().toString());
        //     q.setIssuedDemands(UUID.randomUUID().toString());
        //     a.add(q);
        // }
        // issueRepository.saveAll(a);

    }
}