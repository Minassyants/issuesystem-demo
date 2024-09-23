package mb.pso.issuesystem.service.webclient.impl;

import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import io.vertx.core.json.JsonObject;
import mb.pso.issuesystem.entity.AdditionalAttribute;
import mb.pso.issuesystem.entity.AttachedFile;
import mb.pso.issuesystem.entity.BasicReportRow;
import mb.pso.issuesystem.entity.Client;
import mb.pso.issuesystem.entity.Department;
import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.AdUser;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.IssueAttribute;
import mb.pso.issuesystem.entity.IssueType;
import mb.pso.issuesystem.entity.QAdUser;
import mb.pso.issuesystem.entity.QIssue;
import mb.pso.issuesystem.entity.Subject;
import mb.pso.issuesystem.entity.enums.IssueStatus;
import mb.pso.issuesystem.entity.es.IssueDocument;
import mb.pso.issuesystem.entity.utility.EmailNotification;
import mb.pso.issuesystem.repository.AdditionalAttributeRepository;
import mb.pso.issuesystem.repository.ClientRepository;
import mb.pso.issuesystem.repository.DepartmentRepository;
import mb.pso.issuesystem.repository.EmployeeRepository;
import mb.pso.issuesystem.repository.IssueAttributeRepository;
import mb.pso.issuesystem.repository.IssueRepository;
import mb.pso.issuesystem.repository.IssueTypeRepository;
import mb.pso.issuesystem.repository.SubjectRepository;
import mb.pso.issuesystem.repository.ldap.AdUserRepository;
import mb.pso.issuesystem.service.notifications.impl.EmailNotificationServiceImpl;
import mb.pso.issuesystem.service.s3.MinioService;
import mb.pso.issuesystem.service.webclient.WebClientService;

@Service
public class WebClientServiceImpl implements WebClientService {

    private final IssueRepository issueRepository;
    private final IssueAttributeRepository issueAttributeRepository;
    private final DepartmentRepository departmentRepository;
    private final ClientRepository clientRepository;
    private final IssueTypeRepository issueTypeRepository;
    private final SubjectRepository subjectRepository;
    private final AdditionalAttributeRepository additionalAttributeRepository;
    private final EmployeeRepository employeeRepository;
    private final AdUserRepository adUserRepository;
    private final EmailNotificationServiceImpl emailNotificationServiceImpl;
    private final ElasticsearchOperations elasticsearchOperations;
    private final MinioService minioService;

    public WebClientServiceImpl(IssueRepository issueRepository, DepartmentRepository departmentRepository,
            EmailNotificationServiceImpl emailNotificationServiceImpl,
            IssueAttributeRepository issueAttributeRepository, ClientRepository clientRepository,
            IssueTypeRepository issueTypeRepository, SubjectRepository subjectRepository,
            AdditionalAttributeRepository additionalAttributeRepository, EmployeeRepository employeeRepository,
            AdUserRepository adUserRepository, ElasticsearchOperations elasticsearchOperations,
            MinioService minioService) {
        this.issueRepository = issueRepository;
        this.issueAttributeRepository = issueAttributeRepository;
        this.departmentRepository = departmentRepository;
        this.clientRepository = clientRepository;
        this.issueTypeRepository = issueTypeRepository;
        this.subjectRepository = subjectRepository;
        this.additionalAttributeRepository = additionalAttributeRepository;
        this.employeeRepository = employeeRepository;
        this.adUserRepository = adUserRepository;
        this.emailNotificationServiceImpl = emailNotificationServiceImpl;
        this.elasticsearchOperations = elasticsearchOperations;
        this.minioService = minioService;

    }

    @Override
    public Issue registerNewIssue(Issue issue) {

        // TODO Тут надо убрать поиск, может съедать новые заявки, должен быть дебаунс
        // или какой то доп флаг для избежания дубликации.
        Issue exampleIssue = new Issue();
        exampleIssue.setClient(issue.getClient());
        exampleIssue.setSubject(issue.getSubject());
        exampleIssue.setIssueDescription(issue.getIssueDescription());
        Example<Issue> example = Example.of(exampleIssue);
        Optional<Issue> cI = issueRepository.findOne(example);
        Issue createdIssue;
        if (cI.isPresent())
            createdIssue = cI.get();
        else {
            issue.setStatus(IssueStatus.NEW);
            if (issue.getDocDate() == null)
                issue.setDocDate(new Date());

            // Ищем клиента по номеру телефона
            Client exampleClient = new Client();
            exampleClient.setPhoneNumber(issue.getClient().getPhoneNumber());
            Optional<Client> ce = clientRepository.findOne(Example.of(exampleClient));
            if (ce.isPresent())
                issue.setClient(ce.get());

            // Ищем IssueType
            if (issue.getType() != null) {
                Optional<IssueType> it = issueTypeRepository.findOne(Example.of(issue.getType()));
                if (it.isPresent())
                    issue.setType(it.get());
            }
            // Ищем Subject
            Optional<Subject> issueSubject = subjectRepository.findOne(Example.of(issue.getSubject()));
            if (issueSubject.isPresent())
                issue.setSubject(issueSubject.get());

            // Ищем IssueAttributes
            if (issue.getIssueAttributes() != null) {
                List<IssueAttribute> l = new ArrayList<IssueAttribute>();
                for (IssueAttribute i : issue.getIssueAttributes()) {
                    Optional<IssueAttribute> f = issueAttributeRepository.findOne(Example.of(i));
                    if (f.isPresent())
                        l.add(f.get());
                    else
                        l.add(i);
                }
                issue.setIssueAttributes(l);
            }

            // Ищем Department
            if (issue.getIssuedDepartment() != null) {
                Optional<Department> iDepartment = departmentRepository
                        .findOne(Example.of(issue.getIssuedDepartment()));
                if (iDepartment.isPresent())
                    issue.setIssuedDepartment(iDepartment.get());
            }

            if (issue.getIssuedEmployee() != null) {
                Optional<Employee> iEmployee = employeeRepository.findOne(Example.of(issue.getIssuedEmployee()));
                if (iEmployee.isPresent())
                    issue.setIssuedEmployee(iEmployee.get());
            }

            // Ищем AdditionalAttribute
            if (issue.getAdditionalAttributes() != null) {
                List<AdditionalAttribute> l = new ArrayList<AdditionalAttribute>();
                for (AdditionalAttribute additionalAttribute : issue.getAdditionalAttributes()) {
                    Optional<AdditionalAttribute> f = additionalAttributeRepository
                            .findOne(Example.of(additionalAttribute));
                    if (f.isPresent())
                        l.add(f.get());
                    else
                        l.add(additionalAttribute);
                }
                issue.setAdditionalAttributes(l);
            }

            createdIssue = issueRepository.save(issue);

            // TODO пока выключил оповещения
            // EmailNotification emailNotification = new EmailNotification("bsk1c",
            // createdIssue.getClient().getEmail(),
            // "issueRegisteredForClient", "Регистрация обращения");
            // JsonObject body = new JsonObject();
            // body.put("name", createdIssue.getClient().getName());
            // emailNotification.setBody(body);
            // emailNotificationServiceImpl.sendEmail(emailNotification);
        }
        return createdIssue;
    }

    @Override
    public List<Issue> getAllIssues() {
        List<Issue> issues = (List<Issue>) issueRepository.findAll();
        return issues;
    }

    @Override
    public Page<Issue> getAllIssues(Pageable pageable, Authentication authentication, Optional<String> q,
            Optional<List<String>> searchFieldsOptional) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String roles = jwt.getClaimAsString("scope");
        QIssue issue = QIssue.issue;
        BooleanBuilder where = new BooleanBuilder();
        if (q.isPresent()) {
            // NativeQuery query = NativeQuery.builder().withQuery(t -> t.queryString(t1 ->
            // t1.query(q.get()))).build();
            NativeQuery query = NativeQuery.builder()
                    .withQuery(t -> t.queryString(arg0 -> arg0.fuzziness("auto").query(q.get()))).build();
            if (searchFieldsOptional.isPresent()) {
                List<String> searchFields = searchFieldsOptional.get();
                if (searchFields.size() > 0)
                    query = NativeQuery.builder().withQuery(arg0 -> arg0.multiMatch(arg1 -> arg1.fields(
                            searchFields)
                            .query(q.get()))).build();

            }

            // TODO индекс надо в переменную выводить
            SearchHits<IssueDocument> a = elasticsearchOperations.search(query, IssueDocument.class,
                    IndexCoordinates.of("pso_issue_gzk"));
            List<Integer> w = a.map(arg0 -> arg0.getContent().getId()).toList();
            where.and(issue.id.in(w));
        }

        if (roles.contains("employee")) {
            where.and(issue.issuedEmployee.mail.eq(jwt.getClaimAsString("email"))
                    .and(issue.status.eq(IssueStatus.INPROGRESS)));
        }

        Page<Issue> issues = issueRepository.findAll(where, pageable);
        return issues;
    }

    @Override
    public Optional<Issue> getIssueById(Integer id) {
        Optional<Issue> issue = issueRepository.findById(id);
        return issue;
    }

    public Issue updateInternalInfo(Issue issue) {
        Optional<Issue> _oldIssue = issueRepository.findById(issue.getId());
        if (!_oldIssue.isPresent())
            return null;

        Issue oldIssue = _oldIssue.get();
        Department department = issue.getIssuedDepartment();
        Optional<Department> c = departmentRepository.findOne(Example.of(department));
        if (c.isPresent())
            oldIssue.setIssuedDepartment(c.get());
        else
            oldIssue.setIssuedDepartment(departmentRepository.save(department));

        Employee employee = issue.getIssuedEmployee();
        Optional<Employee> e = employeeRepository.findOne(Example.of(employee));
        if (e.isPresent())
            oldIssue.setIssuedEmployee(e.get());
        else
            oldIssue.setIssuedEmployee(employeeRepository.save(employee));

        issue = issueRepository.save(oldIssue);
        return issue;

    }

    public Issue setInProgress(Issue issue) {
        Optional<Issue> _oldIssue = issueRepository.findById(issue.getId());
        if (!_oldIssue.isPresent())
            return null;

        Issue oldIssue = _oldIssue.get();
        if (oldIssue.getStatus() != IssueStatus.NEW | !oldIssue.hasDepartment()
                | !oldIssue.hasEmployee())
            return null;
        oldIssue.setStatus(IssueStatus.INPROGRESS);
        issue = issueRepository.save(oldIssue);

        EmailNotification emailNotification = new EmailNotification("bsk1c",
                issue.getIssuedEmployee().getMail(),
                "issueRegisteredForEmployee", "Новая рекламация");
        JsonObject body = new JsonObject();
        body.put("name",
                issue.getIssuedEmployee().getGivenName().concat(" ").concat(issue.getIssuedEmployee().getSn()));
        body.put("login",
                issue.getIssuedEmployee().getGivenName().substring(0, 1).concat(".")
                        .concat(issue.getIssuedEmployee().getSn()));
        emailNotification.setBody(body);
        emailNotificationServiceImpl.sendEmail(emailNotification);

        return issue;

    }

    public Issue updateDepartmentFeedback(Issue issue) {
        Optional<Issue> _oldIssue = issueRepository.findById(issue.getId());
        if (!_oldIssue.isPresent())
            return null;

        Issue oldIssue = _oldIssue.get();
        oldIssue.setDepartmentFeedback(issue.getDepartmentFeedback());
        issue = issueRepository.save(oldIssue);
        return issue;
    }

    public Issue setPending(Issue issue) {
        Optional<Issue> _oldIssue = issueRepository.findById(issue.getId());
        if (!_oldIssue.isPresent())
            return null;

        Issue oldIssue = _oldIssue.get();
        if (oldIssue.getStatus() != IssueStatus.INPROGRESS |
                oldIssue.getDepartmentFeedback() == null | oldIssue.getDepartmentFeedback().isEmpty())
            return null;
        oldIssue.setStatus(IssueStatus.PENDINGRESULT);
        issue = issueRepository.save(oldIssue);
        return issue;
    }

    public Issue updateIssueResult(Issue issue) {
        Optional<Issue> _oldIssue = issueRepository.findById(issue.getId());
        if (!_oldIssue.isPresent())
            return null;

        Issue oldIssue = _oldIssue.get();
        oldIssue.setIssueResult(issue.getIssueResult());
        issue = issueRepository.save(oldIssue);
        return issue;
    }

    public Issue setClosed(Issue issue) {
        Optional<Issue> _oldIssue = issueRepository.findById(issue.getId());
        if (!_oldIssue.isPresent())
            return null;

        Issue oldIssue = _oldIssue.get();
        if (oldIssue.getStatus() != IssueStatus.PENDINGRESULT |
                oldIssue.getIssueResult() == null | oldIssue.getIssueResult().isEmpty())
            return null;
        oldIssue.setStatus(IssueStatus.CLOSED);
        issue = issueRepository.save(oldIssue);
        return issue;
    }

    public Iterable<AdUser> findEmployeesByGivenNameSn(String queryString) {
        QAdUser adUser = QAdUser.adUser;
        queryString = queryString.replace(" ", "*");
        Predicate predicate = adUser.displayName.like("*".concat(queryString).concat("*"));

        // Predicate predicate =
        // adUser.givenName.like("*".concat(queryString).concat("*"))
        // .or(adUser.sn.like("*".concat(queryString).concat("*"))).and(adUser.mail.isNotNull());
        return adUserRepository.findAll(predicate);

    }

    public Iterable<BasicReportRow> getReport(LocalDate start, LocalDate end) {
        end = end.plusDays(1);
        return issueRepository.fetchReport(start, end);
    }

    public void uploadFilesToIssue(Integer id, List<MultipartFile> files) {
        Optional<Issue> issueOptional = issueRepository.findById(id);
        if (issueOptional.isPresent()) {
            List<AttachedFile> attachedFiles = files.stream().map(arg0 -> minioService.uploadFile(arg0)).toList();
            Issue issue = issueOptional.get();
            issue.setAttachedFiles(attachedFiles);
            issueRepository.save(issue);
        }
    }

}
