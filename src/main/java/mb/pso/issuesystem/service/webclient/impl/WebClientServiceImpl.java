package mb.pso.issuesystem.service.webclient.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.AdUser;
import mb.pso.issuesystem.entity.AdditionalAttribute;
import mb.pso.issuesystem.entity.AttachedFile;
import mb.pso.issuesystem.entity.BasicReportRow;
import mb.pso.issuesystem.entity.Client;
import mb.pso.issuesystem.entity.Department;
import mb.pso.issuesystem.entity.DepartmentFeedback;
import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.IssueAttribute;
import mb.pso.issuesystem.entity.IssueType;
import mb.pso.issuesystem.entity.QAdUser;
import mb.pso.issuesystem.entity.QEmployee;
import mb.pso.issuesystem.entity.QIssue;
import mb.pso.issuesystem.entity.Subject;
import mb.pso.issuesystem.entity.enums.IssueStatus;
import mb.pso.issuesystem.entity.es.IssueDocument;
import mb.pso.issuesystem.entity.im.Chat;
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

        // [ ] Тут надо убрать поиск, может съедать новые заявки, должен быть дебаунс
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

            // Chat chat = new Chat();

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

            // if (issue.getIssuedEmployee() != null) {
            // Optional<Employee> iEmployee =
            // employeeRepository.findOne(Example.of(issue.getIssuedEmployee()));
            // if (iEmployee.isPresent())
            // issue.setIssuedEmployee(iEmployee.get());
            // }

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

            // FIXME это пздц
            createdIssue = issueRepository.save(issue);
            createdIssue.setChat(new Chat(createdIssue));
            createdIssue = issueRepository.save(createdIssue);

            // [ ] пока выключил оповещения
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
    public Page<Issue> getAllIssues(Pageable pageable, Jwt jwt, Optional<String> q,
            Optional<List<String>> searchFieldsOptional) {
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

            // [ ] индекс надо в переменную выводить
            SearchHits<IssueDocument> a = elasticsearchOperations.search(query, IssueDocument.class,
                    IndexCoordinates.of("pso_issue_gzk"));
            List<Integer> w = a.map(arg0 -> arg0.getContent().getId()).toList();
            where.and(issue.id.in(w));
        }

        if (roles.contains("employee")) {
            where.and(issue.issuedEmployees.any().displayName.eq(jwt.getClaimAsString("displayname"))
                    .or(issue.chat.members.any().displayName.eq(jwt.getClaimAsString("displayname")))
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

    public List<Employee> getIssuedEmployeesByIssueId(Integer issueId) {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue != null)
            return issue.getIssuedEmployees();
        return null;

    }

    public List<DepartmentFeedback> getDepartmentFeedbacksByIssueId(Integer issueId) {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue != null)
            return issue.getDepartmentFeedbacks();
        return null;
    }

    /**
     * @param issueId
     * @param employees
     * @return update Issue or <b>null</b> if issue not found.
     */
    public Issue updateIssuedEmployees(Integer issueId, List<Employee> employees) {

        List<Employee> foundEmployees = employees.stream()
                .map(t -> employeeRepository.findOne(Example.of(t)).orElse(t)).toList();
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue != null) {
            issue.setIssuedEmployees(foundEmployees);
            issue = issueRepository.save(issue);
        }
        return issue;

    }

    public Issue setInProgress(Integer issueId) {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue != null) {
            issue.setStatus(IssueStatus.INPROGRESS);
            issue = issueRepository.save(issue);
        }
        // EmailNotification emailNotification = new EmailNotification("bsk1c",
        // issue.getIssuedEmployee().getMail(),
        // "issueRegisteredForEmployee", "Новая рекламация");
        // JsonObject body = new JsonObject();
        // body.put("name",
        // issue.getIssuedEmployee().getGivenName().concat("
        // ").concat(issue.getIssuedEmployee().getSn()));
        // body.put("login",
        // issue.getIssuedEmployee().getGivenName().substring(0, 1).concat(".")
        // .concat(issue.getIssuedEmployee().getSn()));
        // emailNotification.setBody(body);
        // emailNotificationServiceImpl.sendEmail(emailNotification);
        return issue;

    }

    /**
     * @param issueId
     * @param departmentFeedbacks
     * @return updated Issue or <b>null</b> if issue not found
     */
    public Issue updateDepartmentFeedbacks(Integer issueId, List<DepartmentFeedback> departmentFeedbacks) {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue != null) {
            issue.setDepartmentFeedbacks(departmentFeedbacks);
            issue = issueRepository.save(issue);
        }
        return issue;
    }

    /**
     * @param issueId
     * @return updated Issue or <b>null</b> if issue not found.
     */
    public Issue setPending(Integer issueId) {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue != null) {
            issue.setStatus(IssueStatus.PENDINGRESULT);
            issue = issueRepository.save(issue);

        }
        return issue;
    }

    /**
     * @param issueId
     * @param issueResult
     * @return update Issue or <b>null</b> if issue not found.
     */
    public Issue updateIssueResult(Integer issueId, String issueResult) {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue != null) {
            issue.setIssueResult(issueResult);
            issue = issueRepository.save(issue);
        }
        return issue;
    }

    /**
     * @param issueId
     * @return updated Issue or <b>null</b> if issue not found.
     */
    public Issue setClosed(Integer issueId) {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue != null) {
            issue.setStatus(IssueStatus.CLOSED);
            issue = issueRepository.save(issue);
        }
        return issue;
    }

    public Iterable<AdUser> findEmployeesByGivenNameSn(String queryString) {
        QAdUser adUser = QAdUser.adUser;
        queryString = queryString.replace(" ", "*");
        Predicate predicate = adUser.mail.isNotNull().and(adUser.sn.isNotNull()).and(adUser.givenName.isNotNull())
                .and(adUser.displayName.isNotNull()).and(adUser.displayName.like("*".concat(queryString).concat("*")));
        return adUserRepository.findAll(predicate);

    }

    public Iterable<BasicReportRow> getReport(LocalDate start, LocalDate end) {
        end = end.plusDays(1);
        return issueRepository.fetchReport(start, end);
    }

    public Issue uploadFilesToIssue(Integer id, MultipartFile[] files) {
        Issue issue = issueRepository.findById(id).orElse(null);
        if (issue != null) {
            List<AttachedFile> attachedFiles = List.of(files).stream()
                    .map(arg0 -> minioService.uploadFileToIssue(issue, arg0))
                    .toList();
            issue.addAllAttachedFile(attachedFiles);
            issueRepository.save(issue);
        }
        return issue;
    }

}
