package mb.pso.issuesystem.service.webclient.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.json.JsonObject;
import mb.pso.issuesystem.controller.rabbitmq.RmProducer;
import mb.pso.issuesystem.entity.AdditionalAttribute;
import mb.pso.issuesystem.entity.AdditionalAttributeType;
import mb.pso.issuesystem.entity.Client;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.IssueAttribute;
import mb.pso.issuesystem.entity.Subject;
import mb.pso.issuesystem.entity.utility.EmailNotification;
import mb.pso.issuesystem.repository.AdditionalAttributeTypeRepository;
import mb.pso.issuesystem.repository.ClientRepository;
import mb.pso.issuesystem.repository.IssueAttributeRepository;
import mb.pso.issuesystem.repository.IssueRepository;
import mb.pso.issuesystem.repository.SubjectRepository;
import mb.pso.issuesystem.service.notifications.impl.EmailNotificationServiceImpl;
import mb.pso.issuesystem.service.webclient.WebClientService;

@Service
public class WebClientServiceImpl implements WebClientService {

    private final ClientRepository clientRepository;
    private final IssueRepository issueRepository;
    private final SubjectRepository subjectRepository;
    private final AdditionalAttributeTypeRepository additionalAttributeTypeRepository;
    private final IssueAttributeRepository issueAttributeRepository;
    private final EmailNotificationServiceImpl emailNotificationServiceImpl;

    public WebClientServiceImpl(ClientRepository clientRepository, IssueRepository issueRepository,
            SubjectRepository subjectRepository, AdditionalAttributeTypeRepository additionalAttributeTypeRepository,
            IssueAttributeRepository issueAttributeRepository,
            EmailNotificationServiceImpl emailNotificationServiceImpl) {
        this.clientRepository = clientRepository;
        this.issueRepository = issueRepository;
        this.subjectRepository = subjectRepository;
        this.additionalAttributeTypeRepository = additionalAttributeTypeRepository;
        this.issueAttributeRepository = issueAttributeRepository;
        this.emailNotificationServiceImpl = emailNotificationServiceImpl;

    }

    @Override
    public Issue registerNewIssue(Issue issue) {
        Client client = issue.getClient();
        Optional<Client> c = clientRepository.findOne(Example.of(client));
        if (c.isPresent())
            issue.setClient(c.get());
        else
            issue.setClient(clientRepository.save(client));

        List<IssueAttribute> issueAttributes = issue.getIssueAttributes();
        if (issueAttributes != null)
            for (IssueAttribute issueAttribute : issueAttributes) {
                Optional<IssueAttribute> i = issueAttributeRepository.findOne(Example.of(issueAttribute));
                if (i.isPresent()) {
                    issueAttribute.setId(i.get().getId());
                    issueAttribute.setArangoId(i.get().getArangoId());
                    issueAttribute.setName(i.get().getName());
                } else
                    issueAttribute = issueAttributeRepository.save(issueAttribute);
            }

        Subject subject = issue.getSubject();
        Optional<Subject> s = subjectRepository.findOne(Example.of(subject));
        if (s.isPresent())
            issue.setSubject(s.get());
        else
            issue.setSubject(subjectRepository.save(subject));

        List<AdditionalAttribute> additionalAttributes = issue.getAdditionalAttributes();
        if (additionalAttributes != null)
            for (AdditionalAttribute additionalAttribute : additionalAttributes) {
                AdditionalAttributeType additionalAttributeType = additionalAttribute.getType();
                Optional<AdditionalAttributeType> a = additionalAttributeTypeRepository
                        .findOne(Example.of(additionalAttributeType));
                if (a.isPresent())
                    additionalAttribute.setType(a.get());
                else
                    additionalAttribute.setType(additionalAttributeTypeRepository.save(additionalAttributeType));
            }
        Issue exampleIssue = new Issue();
        exampleIssue.setClient(issue.getClient());
        exampleIssue.setSubject(issue.getSubject());
        exampleIssue.setIssueDescription(issue.getIssueDescription());
        Example<Issue> example = Example.of(exampleIssue);
        Optional<Issue> cI = issueRepository.findOne(example);
        Issue createdIssue;
        if (cI.isPresent())
            createdIssue = cI.get();
        else
            createdIssue = issueRepository.save(issue);

        EmailNotification emailNotification = new EmailNotification("bsk1c",createdIssue.getClient().getEmail(),"issueRegisteredForClient","Регистрация обращения");
        JsonObject body = new JsonObject();
        body.put("name", createdIssue.getClient().getName());
        emailNotification.setBody(body);
        emailNotificationServiceImpl.sendEmail(emailNotification);


        return createdIssue;
    }

}
