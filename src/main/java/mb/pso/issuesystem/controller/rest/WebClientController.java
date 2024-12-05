package mb.pso.issuesystem.controller.rest;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import mb.pso.issuesystem.entity.AdUser;
import mb.pso.issuesystem.entity.AdUserDetails;
import mb.pso.issuesystem.entity.BasicReportRow;
import mb.pso.issuesystem.entity.DepartmentFeedback;
import mb.pso.issuesystem.entity.Employee;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.IssueAttribute;
import mb.pso.issuesystem.entity.Notification;
import mb.pso.issuesystem.entity.Subject;
import mb.pso.issuesystem.entity.Users;
import mb.pso.issuesystem.service.impl.core.IssueAttributeService;
import mb.pso.issuesystem.service.impl.core.NotificationService;
import mb.pso.issuesystem.service.impl.core.ReportingService;
import mb.pso.issuesystem.service.impl.core.UserService;
import mb.pso.issuesystem.service.impl.core.issue.IssueEmployeeService;
import mb.pso.issuesystem.service.impl.core.issue.IssueFeedbackService;
import mb.pso.issuesystem.service.impl.core.issue.IssueService;
import mb.pso.issuesystem.service.impl.core.issue.IssueStateService;
import mb.pso.issuesystem.service.impl.im.ChatService;
import mb.pso.issuesystem.service.impl.im.MessageService;
import mb.pso.issuesystem.service.impl.ldap.AdUserService;
import mb.pso.issuesystem.utils.JwtUtils;

//[ ] REFACTOR
@RestController
public class WebClientController {

    private final IssueService issueService;
    private final IssueEmployeeService issueEmployeeService;
    private final IssueStateService issueStateService;
    private final IssueFeedbackService issueFeedbackService;
    private final UserService userService;
    private final ChatService chatService;
    private final MessageService messageService;
    private final AdUserService adUserService;
    private final IssueAttributeService issueAttributeService;
    private final NotificationService notificationService;
    private final ReportingService reportingService;

    public WebClientController(UserService userService, IssueService issueService,
            IssueEmployeeService issueEmployeeService, IssueStateService issueStateService,
            IssueFeedbackService issueFeedbackService, AdUserService adUserService,
            NotificationService notificationService, ReportingService reportingService,
            IssueAttributeService issueAttributeService,
            MessageService messageService,
            ChatService chatService) {
        this.issueAttributeService = issueAttributeService;
        this.issueEmployeeService = issueEmployeeService;
        this.issueFeedbackService = issueFeedbackService;
        this.notificationService = notificationService;
        this.issueStateService = issueStateService;
        this.reportingService = reportingService;
        this.messageService = messageService;
        this.adUserService = adUserService;
        this.issueService = issueService;
        this.userService = userService;
        this.chatService = chatService;

    }

    // BUG: Это полная ...
    @GetMapping("/auth")
    public ResponseEntity<Users> auth(@RequestParam String username) {
        Users user = userService.getByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/issue/{id}/setclosed")
    public ResponseEntity<Issue> setClosed(@PathVariable Integer id) {
        Issue updatedIssue = issueStateService.setClosed(id);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/issue/{id}/setinprogress")
    public ResponseEntity<Issue> setInProgress(@AuthenticationPrincipal Jwt jwt, @PathVariable Integer id) {
        String displayName = JwtUtils.extractDisplayName(jwt);
        Issue updatedIssue = issueStateService.setInProgress(id, displayName);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/issue/{id}/setpending")
    public ResponseEntity<Issue> setPendingResult(@PathVariable Integer id) {
        Issue updatedIssue = issueStateService.setPending(id);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/issue/{id}/addtodepartmentfeedbacks")
    public ResponseEntity<Issue> addToDepartmentFeedbacks(@PathVariable Integer id,
            @RequestBody DepartmentFeedback departmentFeedback) {
        Issue updatedIssue = issueFeedbackService.addToDepartmentFeedbacks(id, departmentFeedback);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/issue/{id}/updatedepartmentfeedbacks")
    public ResponseEntity<Issue> updateDepartmentFeedbacks(@PathVariable Integer id,
            @RequestBody List<DepartmentFeedback> departmentFeedbacks) {
        Issue updatedIssue = issueFeedbackService.updateDepartmentFeedbacks(id, departmentFeedbacks);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/issue/{id}/addtoissuedemployees")
    public ResponseEntity<Issue> addToIssuedEmployees(@PathVariable Integer id, @RequestBody Employee employee) {
        Issue updatedIssue = issueEmployeeService.addToIssuedEmployees(id, employee);
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/issue/{id}/updateissuedemployees")
    public ResponseEntity<Issue> updateIssuedEmployees(@PathVariable Integer id,
            @RequestBody List<Employee> employees) {
        Issue updatedIssue = issueEmployeeService.updateIssuedEmployees(id, employees);
        return ResponseEntity.ok(updatedIssue);

    }

    @PostMapping("/issue/{id}/updateissueattributes")
    public ResponseEntity<Issue> updateIssueAttributes(@PathVariable Integer id,
            @RequestBody List<IssueAttribute> issueAttributes) {
        Issue updateIssue = issueService.updateIssueAttributes(id, issueAttributes);
        return ResponseEntity.ok(updateIssue);
    }

    @PostMapping("/issue/{id}/updateissuesubject")
    public ResponseEntity<Issue> updateIssueSubject(@PathVariable Integer id, @RequestBody Subject subject) {
        Issue updatedIssue = issueService.updateIssueSubject(id, subject);
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/issue/{id}/updateissueresult")
    public ResponseEntity<Issue> updateIssueResult(@PathVariable Integer id, @RequestBody String issueResult) {
        Issue updatedIssue = issueService.updateIssueResult(id, issueResult);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/registerNewIssue")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<Issue> registerNewIssue(@RequestBody Issue issue) {
        Issue createdIssue = issueService.registerNewIssue(issue);
        return ResponseEntity.ok(createdIssue);
    }

    @GetMapping("/issue")
    public ResponseEntity<Page<Issue>> getAllIssuesPageable(@AuthenticationPrincipal Jwt jwt, @RequestParam int page,
            @RequestParam int size, @RequestParam Optional<String> q, @RequestParam Optional<List<String>> sf) {
        Page<Issue> issues = issueService
                .getAll(PageRequest.of(page, size, Sort.by(Direction.DESC, "id")), jwt, q, sf);
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/issue/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Integer id, @AuthenticationPrincipal AdUserDetails user) {
        Issue issue = issueService.getOrThrow(id);
        return ResponseEntity.ok(issue);
    }

    @GetMapping("/issue/{id}/issuedemployees")
    public ResponseEntity<Set<Employee>> getIssuedEmployeesByIssueId(@PathVariable Integer id) {
        Set<Employee> issuedEmployees = issueEmployeeService.getIssuedEmployeesByIssueId(id);
        return ResponseEntity.ok(issuedEmployees);
    }

    @GetMapping("/issue/{id}/departmentfeedbacks")
    public ResponseEntity<Set<DepartmentFeedback>> getDepartmentFeedbacksByIssueId(@PathVariable Integer id) {
        Set<DepartmentFeedback> departmentFeedbacks = issueFeedbackService.getDepartmentFeedbacksByIssueId(id);
        return ResponseEntity.ok(departmentFeedbacks);
    }

    @PostMapping("/issue/{id}/uploadFile")
    public ResponseEntity<Issue> uploadFilesToIssue(@PathVariable Integer id,
            @RequestParam MultipartFile[] files) {
        Issue issue = issueService.uploadFilesToIssue(id, files);
        if (issue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(issue);
    }

    @GetMapping("/employee")
    public ResponseEntity<Iterable<AdUser>> getAllEmployees(@RequestParam String q) {
        return ResponseEntity.ok(adUserService.findAllByGivenNameSn(q));
    }

    @GetMapping("/availableissueattributes")
    public ResponseEntity<Iterable<IssueAttribute>> getAvailableIssueAttributes() {
        return ResponseEntity.ok(issueAttributeService.getAvailableIssueAttributes());
    }

    @GetMapping("/notifications/count")
    public ResponseEntity<Long> getNotificationCount(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(notificationService.count(jwt));
    }

    @GetMapping("/notifications")
    public ResponseEntity<Page<Notification>> getUserNotifications(@AuthenticationPrincipal Jwt jwt,
            @RequestParam int page,
            @RequestParam int size) {
        PageRequest pageable = PageRequest.of(page, size,
                Sort.by(Sort.Order.asc("isRead"), Sort.Order.desc("createdAt")));
        Page<Notification> notifications = notificationService.get(pageable, jwt);

        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/report")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<Iterable<BasicReportRow>> getReport(
            @RequestParam Timestamp start,
            @RequestParam Timestamp end) {

        return ResponseEntity.ok(reportingService.getReport(start.toLocalDateTime().toLocalDate(),
                end.toLocalDateTime().toLocalDate()));
    }

}
