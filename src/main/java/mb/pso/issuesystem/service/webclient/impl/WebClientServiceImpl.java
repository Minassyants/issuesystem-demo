package mb.pso.issuesystem.service.webclient.impl;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.vertx.core.json.JsonObject;
import jakarta.persistence.EntityManager;
import mb.pso.issuesystem.entity.AdditionalAttribute;
import mb.pso.issuesystem.entity.Client;
import mb.pso.issuesystem.entity.Department;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.IssueAttribute;
import mb.pso.issuesystem.entity.IssueType;
import mb.pso.issuesystem.entity.Subject;
import mb.pso.issuesystem.entity.enums.IssueStatus;
import mb.pso.issuesystem.entity.utility.EmailNotification;
import mb.pso.issuesystem.repository.AdditionalAttributeRepository;
import mb.pso.issuesystem.repository.ClientRepository;
import mb.pso.issuesystem.repository.DepartmentRepository;
import mb.pso.issuesystem.repository.IssueAttributeRepository;
import mb.pso.issuesystem.repository.IssueRepository;
import mb.pso.issuesystem.repository.IssueTypeRepository;
import mb.pso.issuesystem.repository.SubjectRepository;
import mb.pso.issuesystem.service.notifications.impl.EmailNotificationServiceImpl;
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
    private final EmailNotificationServiceImpl emailNotificationServiceImpl;

    public WebClientServiceImpl(IssueRepository issueRepository, DepartmentRepository departmentRepository,
            EmailNotificationServiceImpl emailNotificationServiceImpl,
            IssueAttributeRepository issueAttributeRepository, ClientRepository clientRepository,
            IssueTypeRepository issueTypeRepository, SubjectRepository subjectRepository,
            AdditionalAttributeRepository additionalAttributeRepository) {
        this.issueRepository = issueRepository;
        this.issueAttributeRepository = issueAttributeRepository;
        this.departmentRepository = departmentRepository;
        this.clientRepository = clientRepository;
        this.issueTypeRepository = issueTypeRepository;
        this.subjectRepository = subjectRepository;
        this.additionalAttributeRepository = additionalAttributeRepository;
        this.emailNotificationServiceImpl = emailNotificationServiceImpl;
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
    public Page<Issue> getAllIssues(Pageable pageable) {
        Page<Issue> issues = issueRepository.findAll(pageable);
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

        oldIssue.setIssuedEmployee(issue.getIssuedEmployee());

        issue = issueRepository.save(oldIssue);
        return issue;

    }

    public Issue setInProgress(Issue issue) {
        Optional<Issue> _oldIssue = issueRepository.findById(issue.getId());
        if (!_oldIssue.isPresent())
            return null;

        Issue oldIssue = _oldIssue.get();
        if (oldIssue.getStatus() != IssueStatus.NEW | !oldIssue.hasDepartment()
                | oldIssue.getIssuedEmployee() == null | oldIssue.getIssuedEmployee().isEmpty())
            return null;
        oldIssue.setStatus(IssueStatus.INPROGRESS);
        issue = issueRepository.save(oldIssue);
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

}
