package mb.pso.issuesystem.service.webclient.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.vertx.core.json.JsonObject;
import mb.pso.issuesystem.entity.Department;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.enums.IssueStatus;
import mb.pso.issuesystem.entity.utility.EmailNotification;
import mb.pso.issuesystem.repository.DepartmentRepository;
import mb.pso.issuesystem.repository.IssueRepository;
import mb.pso.issuesystem.repository.UserRepository;
import mb.pso.issuesystem.service.notifications.impl.EmailNotificationServiceImpl;
import mb.pso.issuesystem.service.webclient.WebClientService;

@Service
public class WebClientServiceImpl implements WebClientService {

    private final IssueRepository issueRepository;

    private final DepartmentRepository departmentRepository;
    private final EmailNotificationServiceImpl emailNotificationServiceImpl;

    public WebClientServiceImpl(IssueRepository issueRepository, DepartmentRepository departmentRepository,
            EmailNotificationServiceImpl emailNotificationServiceImpl) {
        this.issueRepository = issueRepository;
        this.departmentRepository = departmentRepository;
        this.emailNotificationServiceImpl = emailNotificationServiceImpl;
    }

    

    @Override
    public Issue registerNewIssue(Issue issue) {
        // Client client = issue.getClient();
        // Optional<Client> c = clientRepository.findOne(Example.of(client));
        // if (c.isPresent())
        // issue.setClient(c.get());
        // else
        // issue.setClient(clientRepository.save(client));

        // List<IssueAttribute> issueAttributes = issue.getIssueAttributes();
        // if (issueAttributes != null)
        // for (IssueAttribute issueAttribute : issueAttributes) {
        // Optional<IssueAttribute> i =
        // issueAttributeRepository.findOne(Example.of(issueAttribute));
        // if (i.isPresent()) {
        // issueAttribute.setId(i.get().getId());
        // issueAttribute.setArangoId(i.get().getArangoId());
        // issueAttribute.setName(i.get().getName());
        // } else
        // issueAttribute = issueAttributeRepository.save(issueAttribute);
        // }

        // issue.setStatus(IssueStatus.NEW);
        // issue.setDocDate(new Date());

        // Subject subject = issue.getSubject();
        // Optional<Subject> s = subjectRepository.findOne(Example.of(subject));
        // if (s.isPresent())
        // issue.setSubject(s.get());
        // else
        // issue.setSubject(subjectRepository.save(subject));

        // List<AdditionalAttribute> additionalAttributes =
        // issue.getAdditionalAttributes();
        // if (additionalAttributes != null)
        // for (AdditionalAttribute additionalAttribute : additionalAttributes) {
        // AdditionalAttributeType additionalAttributeType =
        // additionalAttribute.getType();
        // Optional<AdditionalAttributeType> a = additionalAttributeTypeRepository
        // .findOne(Example.of(additionalAttributeType));
        // if (a.isPresent())
        // additionalAttribute.setType(a.get());
        // else
        // additionalAttribute.setType(additionalAttributeTypeRepository.save(additionalAttributeType));
        // }
        // Issue exampleIssue = new Issue();
        // exampleIssue.setClient(issue.getClient());
        // exampleIssue.setSubject(issue.getSubject());
        // exampleIssue.setIssueDescription(issue.getIssueDescription());
        // Example<Issue> example = Example.of(exampleIssue);
        // Optional<Issue> cI = issueRepository.findOne(example);
        // Issue createdIssue;
        // if (cI.isPresent())
        // createdIssue = cI.get();
        // else {
        // createdIssue = issueRepository.save(issue);

        // EmailNotification emailNotification = new EmailNotification("bsk1c",
        // createdIssue.getClient().getEmail(),
        // "issueRegisteredForClient", "Регистрация обращения");
        // JsonObject body = new JsonObject();
        // body.put("name", createdIssue.getClient().getName());
        // emailNotification.setBody(body);
        // emailNotificationServiceImpl.sendEmail(emailNotification);
        // }

        // return createdIssue;
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
            issue.setDocDate(new Date());
            createdIssue = issueRepository.save(issue);

            EmailNotification emailNotification = new EmailNotification("bsk1c", createdIssue.getClient().getEmail(),
                    "issueRegisteredForClient", "Регистрация обращения");
            JsonObject body = new JsonObject();
            body.put("name", createdIssue.getClient().getName());
            emailNotification.setBody(body);
            emailNotificationServiceImpl.sendEmail(emailNotification);
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
    public Optional<Issue> getIssueById(String id) {
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
