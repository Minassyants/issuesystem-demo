package mb.pso.issuesystem.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import mb.pso.issuesystem.config.properties.AdminUserProperties;
import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.IssueAttribute;
import mb.pso.issuesystem.entity.IssueType;
import mb.pso.issuesystem.entity.Users;
import mb.pso.issuesystem.entity.enums.Roles;
import mb.pso.issuesystem.repository.core.EmployeeRepository;
import mb.pso.issuesystem.repository.core.IssueAttributeRepository;
import mb.pso.issuesystem.repository.core.IssueRepository;
import mb.pso.issuesystem.repository.core.IssueTypeRepository;
import mb.pso.issuesystem.repository.core.UserRepository;
import mb.pso.issuesystem.repository.es.IssueDocumentRepository;

//[x] REFACTOR
@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);

    private final UserRepository userRepository;
    private final IssueTypeRepository issueTypeRepository;
    private final IssueAttributeRepository issueAttributeRepository;
    private final EmployeeRepository employeeRepository;
    private final AdminUserProperties adminUserProperties;

    public CommandLineAppStartupRunner(UserRepository userRepository, IssueRepository issueRepository,
            IssueTypeRepository issueTypeRepository, IssueDocumentRepository obRepository,
            IssueAttributeRepository issueAttributeRepository, EmployeeRepository employeeRepository,
            AdminUserProperties adminUserProperties) {
        this.userRepository = userRepository;
        this.issueTypeRepository = issueTypeRepository;
        this.issueAttributeRepository = issueAttributeRepository;
        this.employeeRepository = employeeRepository;
        this.adminUserProperties = adminUserProperties;
    }

    @Override
    public void run(String... args) throws Exception {
        setupAdminUser();
        setupIssueTypes();
        setupIssueAttributes();
        setupAdminEmployee();

    }

    private void setupAdminUser() {
        Users adminUser = new Users(
                adminUserProperties.getEmail(),
                adminUserProperties.getUsername(),
                adminUserProperties.getPassword());
        adminUser.setsAMAccountName("admin");
        adminUser.setRoles(List.of(Roles.ADMIN, Roles.USER));

        userRepository.findOne(Example.of(new Users(null, "admin", null)))
                .ifPresentOrElse(
                        user -> logger.info("Admin user already exists!"),
                        () -> {
                            userRepository.save(adminUser);
                            logger.info("Admin user created!");
                        });
    }

    private void setupIssueTypes() {
        List<String> issueTypeStrings = List.of(
                "Личное обращение", "По городскому телефону", "Почтовый клиент", "На сайте компании",
                "2Gis", "WhatsApp", "Журнал отзывов и предложений", "Ящик жалоб и предложений",
                "ПСО", "Повторный ремонт");
        addEntities(issueTypeStrings, IssueType::new, issueTypeRepository);
    }

    private void setupIssueAttributes() {
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
        addEntities(issueAttributeStrings, name -> {
            IssueAttribute attribute = new IssueAttribute(name);
            attribute.setIsDeprecated(false);
            return attribute;
        }, issueAttributeRepository);
    }

    private void setupAdminEmployee() {
        Employee adminEmployee = new Employee();
        adminEmployee.setDisplayName("admin");
        adminEmployee.setGivenName("admin");
        adminEmployee.setMail("alexandr.minassyants@mercedes-benz.kz");
        adminEmployee.setSn("admin");
        adminEmployee.setsAMAccountName("admin");

        employeeRepository.save(adminEmployee);
        logger.info("Admin employee created!");
    }

    private <T> void addEntities(List<String> names, EntityFactory<T> factory, JpaRepository<T, ?> repository) {
        names.forEach(name -> {
            T entity = factory.create(name);
            repository.findOne(Example.of(entity))
                    .ifPresentOrElse(
                            e -> logger.info("{} already exists!", entity),
                            () -> {
                                repository.save(entity);
                                logger.info("{} created!", entity);
                            });
        });
    }

    @FunctionalInterface
    interface EntityFactory<T> {
        T create(String name);
    }

}