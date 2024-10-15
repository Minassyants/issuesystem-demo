package mb.pso.issuesystem.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import co.elastic.clients.util.DateTime;
import mb.pso.issuesystem.entity.Client;
import mb.pso.issuesystem.entity.Department;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.IssueAttribute;
import mb.pso.issuesystem.entity.IssueType;
import mb.pso.issuesystem.entity.Users;
import mb.pso.issuesystem.entity.Vehicle;
import mb.pso.issuesystem.entity.enums.IssueStatus;
import mb.pso.issuesystem.entity.enums.Roles;
import mb.pso.issuesystem.entity.es.IssueDocument;
import mb.pso.issuesystem.repository.IssueAttributeRepository;
import mb.pso.issuesystem.repository.IssueRepository;
import mb.pso.issuesystem.repository.IssueTypeRepository;
import mb.pso.issuesystem.repository.UserRepository;
import mb.pso.issuesystem.repository.es.IssueDocumentRepository;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private final UserRepository userRepository;
    private final IssueRepository issueRepository;
    private final IssueTypeRepository issueTypeRepository;
    private final IssueAttributeRepository issueAttributeRepository;
    private final IssueDocumentRepository obRepository;

    public CommandLineAppStartupRunner(UserRepository userRepository, IssueRepository issueRepository,
            IssueTypeRepository issueTypeRepository, IssueDocumentRepository obRepository,
            IssueAttributeRepository issueAttributeRepository) {
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
        this.issueTypeRepository = issueTypeRepository;
        this.issueAttributeRepository = issueAttributeRepository;
        this.obRepository = obRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Users user = new Users("alexandr.minassyants@mercedes-benz.kz", "admin",
                "$2a$10$bUo1HZovBZKHDbSbZGQdee392mH9NLMzbGBcKvUtVWsoAPDb094Qa", null);
        List<Roles> roles = new ArrayList<Roles>();
        roles.add(Roles.ADMIN);
        roles.add(Roles.USER);
        user.setRoles(roles);
        if (userRepository.findOne(Example.of(new Users(null, "admin", null, null))).isEmpty()) {
            userRepository.save(user);

        } else {
            System.out.println("User already exists!");
        }

        List<String> issueTypeStrings = List.of("Личное обращение", "По городскому телефону", "Почтовый клиент",
                "На сайте компании", "2Gis", "WhatsApp", "Журнал отзывов и предложений", "Ящик жалоб и предложений",
                "ПСО", "Повторный ремонт");
        for (String string : issueTypeStrings) {
            IssueType issueType = new IssueType(string);
            Optional<IssueType> i = issueTypeRepository.findOne(Example.of(issueType));
            if (i.isEmpty()) {
                issueTypeRepository.save(issueType);
                System.out.println("IssueType craeted!");
            } else {

                System.out.println("IssueType already exists!");
            }
        }

        List<String> issueAttributeStrings = List.of(
                "Ненадлежащее качество товара",
                "Ненадлежащее качество предоставления услуг",
                "Срыв сроков поставки автомобилей, запасных частей, аксессуаров",
                "Отказ от гарантийных обязательств",
                "Грубость и нестандартное поведение работника",
                "Несвоевременное предоставление документов на оплату",
                "Недовольство клиента к системе оплаты за производственные услуги и купленные запасные части",
                "Требования заключенного между сторонами договора",
                "Повторный заезд, ПСО и т.д.");
        for (String string : issueAttributeStrings) {
            IssueAttribute issueAttribute = new IssueAttribute(string);
            issueAttribute.setIsDeprecated(null);
            IssueAttribute i = issueAttributeRepository.findOne(Example.of(issueAttribute)).orElse(issueAttribute);
            i.setIsDeprecated(false);
            issueAttributeRepository.save(i);
            System.out.println("IssueAttribute craeted/updated!");
        }

        // <Issue> a = new ArrayList<Issue>();

        // i = 0; i < 50; i++) {
        // Issue issue1 = new Issue();

        // issue1.setStatus(IssueStatus.NEW);
        // i > 35)
        // etStatus(IssueStatus.CLOSED);

        // issue1.setStatus(IssueStatus.INPROGRESS);
        //

        // e1.setDocNumber("DOC001");

        // etClient(new Client(UUID.randomUUID().toString(),
        // UUID.randomUUID().toString(), UUID.randomUUID().toString(),
        // UUID.randomUUID().toString()));

        // etSubject(new Vehicle(UUID.randomUUID().toString(),
        // UUID.randomUUID().toString()));

        // issue1.setIssueDescription(UUID.randomUUID().toString());

        // // issue1.setIssuedDepartment(new Department(UUID.randomUUID().toString()));
        // // issue1.setIssuedEmployee(UUID.randomUUID().toString());
        // // issue1.setIssuedDemands(UUID.randomUUID().toString());

        // // issue1.setDepartmentFeedback(UUID.randomUUID().toString());
        // // issue1.setIssueResult("Result pending");
        // a.add(issue1);
        // }
        // issueRepository.saveAll(a);
        // IssueDocument t = new IssueDocument();
        // t.setTestBool(true);
        // t.setTestInteger(1);
        // t.setTestString("123");
        // t.setDepartment(new Department("testDep"));
        // t.setTestDate(Date.from(Instant.now()));
        // t.setTestDateTime(DateTime.of(Instant.now()));
        // t.setTestLocalDateTime(LocalDateTime.now());

        // obRepository.save(t);

    }
}