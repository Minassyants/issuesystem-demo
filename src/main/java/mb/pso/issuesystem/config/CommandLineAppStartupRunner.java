package mb.pso.issuesystem.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import mb.pso.issuesystem.entity.Client;
import mb.pso.issuesystem.entity.Department;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.Users;
import mb.pso.issuesystem.entity.Vehicle;
import mb.pso.issuesystem.entity.enums.IssueStatus;
import mb.pso.issuesystem.entity.enums.Roles;
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
        List<Roles> roles = new ArrayList<Roles>();
        roles.add(Roles.ADMIN);
        roles.add(Roles.USER);
        user.setRoles(roles);
        if (userRepository.findOne(Example.of(new Users(null, "admin", null, null))).isEmpty())
            userRepository.save(user);
        // List<Issue> a = new ArrayList<Issue>();

        // for (int i = 0; i < 50; i++) {
        // Issue issue1 = new Issue();
        
        // issue1.setStatus(IssueStatus.NEW);
        // issue1.setDocNumber("DOC001");
        // issue1.setDocDate(new Date());
        // issue1.setClient(new Client(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        
        // issue1.setSubject(new Vehicle(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        
        // issue1.setIssueDescription(UUID.randomUUID().toString());
        
        // // issue1.setIssuedDepartment(new Department(UUID.randomUUID().toString()));
        // // issue1.setIssuedEmployee(UUID.randomUUID().toString());
        // // issue1.setIssuedDemands(UUID.randomUUID().toString());
        
        // // issue1.setDepartmentFeedback(UUID.randomUUID().toString());
        // // issue1.setIssueResult("Result pending");
        // a.add(issue1);
        // }
        // issueRepository.saveAll(a);

    }
}