package mb.pso.issuesystem.service.impl.core.issue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.BooleanBuilder;

import mb.pso.issuesystem.entity.QIssue;
import mb.pso.issuesystem.entity.core.AttachedFile;
import mb.pso.issuesystem.entity.core.Issue;
import mb.pso.issuesystem.entity.core.IssueAttribute;
import mb.pso.issuesystem.entity.core.Subject;
import mb.pso.issuesystem.entity.enums.IssueStatus;
import mb.pso.issuesystem.entity.im.Chat;
import mb.pso.issuesystem.repository.core.CombinedRepository;
import mb.pso.issuesystem.repository.core.IssueRepository;
import mb.pso.issuesystem.service.AbstractCrudService;
import mb.pso.issuesystem.service.impl.core.AdditionalAttributeService;
import mb.pso.issuesystem.service.impl.core.ClientService;
import mb.pso.issuesystem.service.impl.core.IssueDocumentService;
import mb.pso.issuesystem.service.impl.core.SubjectService;
import mb.pso.issuesystem.service.impl.external.MinioService;
import mb.pso.issuesystem.utils.JwtUtils;


@Service
public class IssueService extends AbstractCrudService<Issue, Integer> {

    private final IssueRepository repository;

    private final IssueStateService stateService;

    private final SubjectService subjectService;

    private final ClientService clientService;

    private final AdditionalAttributeService additionalAttributeService;

    private final IssueDocumentService issueDocumentService;

    private final MinioService minioService;

    public IssueService(IssueRepository repository,
            SubjectService subjectService,
            MinioService minioService, ClientService clientService,
            AdditionalAttributeService additionalAttributeService, IssueDocumentService issueDocumentService,
            IssueStateService stateService) {
        this.repository = repository;
        this.stateService = stateService;
        this.subjectService = subjectService;
        this.minioService = minioService;
        this.clientService = clientService;
        this.additionalAttributeService = additionalAttributeService;
        this.issueDocumentService = issueDocumentService;
    }

    @Override
    protected Integer getId(Issue entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<Issue, Integer> getRepository() {
        return repository;
    }

    public Page<Issue> getAll(Pageable pageable, Jwt jwt, Optional<String> q,
            Optional<List<String>> searchFieldsOptional) {
        String roles = jwt.getClaimAsString("scope");
        QIssue issue = QIssue.issue;
        BooleanBuilder where = new BooleanBuilder();

        if (q.isPresent()) {
            List<Integer> matchingIds = issueDocumentService.searchIssueIds(q.get(), searchFieldsOptional);
            where.and(issue.id.in(matchingIds));
        }

        if (roles.contains("employee")) {
            where.and(issue.issuedEmployees.any().displayName.eq(JwtUtils.extractDisplayName(jwt))
                    .or(issue.chat.members.any().displayName.eq(JwtUtils.extractDisplayName(jwt)))
                    .and(issue.status.eq(IssueStatus.INPROGRESS)));
        }

        return repository.findAll(where, pageable);
    }

    public Issue updateIssueAttributes(Integer issueId, List<IssueAttribute> issueAttributes) {
        Issue issue = getOrThrow(issueId);

        stateService.assertStatus(issue, IssueStatus.NEW, "Issue attributes can only be set while NEW");

        issue.setIssueAttributes(issueAttributes);

        return update(issue);

    }

    public Issue updateIssueSubject(Integer issueId, Subject subject) {
        Issue issue = getOrThrow(issueId);

        stateService.assertStatus(issue, IssueStatus.NEW, "Issue subject can only be set while NEW");

        Subject newSubject = subjectService.get(Example.of(subject)).orElse(subject);
        issue.setSubject(newSubject);

        return update(issue);
    }

    public Issue updateIssueResult(Integer issueId, String issueResult) {
        Issue issue = getOrThrow(issueId);

        stateService.assertStatus(issue, IssueStatus.PENDINGRESULT,
                "Issue result can be added only while PENDINGRESULT");

        issue.setIssueResult(issueResult);

        return update(issue);
    }

    public Issue uploadFilesToIssue(Integer issueId, MultipartFile[] files) {
        Issue issue = getOrThrow(issueId);

        List<AttachedFile> attachedFiles = List.of(files).stream()
                .map(arg0 -> minioService.uploadFileToIssue(issue, arg0))
                .toList();
        issue.addAttachedFiles(attachedFiles);

        return update(issue);
    }

    public Issue registerNewIssue(Issue issue) {

        Issue createdIssue = new Issue.Builder()
                .setStatus(IssueStatus.NEW)
                .setDocDate(Optional.ofNullable(issue.getDocDate()).orElse(new Date()))
                .setClient(clientService.resolve(issue.getClient()))
                .setIssueType(issue.getType())
                .setSubject(subjectService.resolve(issue.getSubject()))
                .setAdditionalAttributes(
                        additionalAttributeService.resolveAdditionalAttributes(issue.getAdditionalAttributes()))
                .build();

        createdIssue = create(createdIssue);
        createdIssue.setChat(new Chat(createdIssue));

        return update(createdIssue);
    }

}