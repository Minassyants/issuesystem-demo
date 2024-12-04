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
import org.springframework.web.bind.annotation.CrossOrigin;
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
import mb.pso.issuesystem.entity.im.Chat;
import mb.pso.issuesystem.entity.im.Message;
import mb.pso.issuesystem.service.impl.core.UserService;
import mb.pso.issuesystem.service.impl.im.ChatService;
import mb.pso.issuesystem.service.impl.im.MessageService;
import mb.pso.issuesystem.service.webclient.impl.WebClientServiceImpl;

//[ ] REFACTOR
@RestController
@CrossOrigin(originPatterns = "*", origins = "*")
public class WebClientController {

    private final WebClientServiceImpl webClientServiceImpl;
    private final UserService userServiceImpl;
    private final ChatService chatService;
    private final MessageService messageService;

    public WebClientController(WebClientServiceImpl webClientServiceImpl, UserService userServiceImpl,
            MessageService messageService,
            ChatService chatService) {
        this.webClientServiceImpl = webClientServiceImpl;
        this.userServiceImpl = userServiceImpl;
        this.chatService = chatService;
        this.messageService = messageService;

    }

    @GetMapping("/chat/{id}")
    public ResponseEntity<Chat> getChatById(@PathVariable Integer id) {
        return ResponseEntity.ok(chatService.getOrThrow(id));
    }

    @GetMapping("/chat/messages/{id}")
    public ResponseEntity<Page<Message>> getMessagesPageable(@PathVariable Integer id,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<Integer> page) {
        Integer _page = page.orElse(0);
        Integer _size = size.orElse(10);

        return ResponseEntity.ok(messageService.getAllByChatIdPageable(id,
                PageRequest.of(_page, _size, Sort.by(Direction.DESC, "createdAt"))));

    }

    // BUG: Это полная ...
    @GetMapping("/auth")
    public ResponseEntity<Users> auth(@RequestParam String username) {
        Users user = userServiceImpl.getByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/issue/{id}/setclosed")
    public ResponseEntity<Issue> setClosed(@PathVariable Integer id) {
        Issue updatedIssue = webClientServiceImpl.setClosed(id);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/issue/{id}/setinprogress")
    public ResponseEntity<Issue> setInProgress(@AuthenticationPrincipal Jwt jwt, @PathVariable Integer id) {
        String displayName = jwt.getClaimAsString("displayname");
        Issue updatedIssue = webClientServiceImpl.setInProgress(id, displayName);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/issue/{id}/setpending")
    public ResponseEntity<Issue> setPendingResult(@PathVariable Integer id) {
        Issue updatedIssue = webClientServiceImpl.setPending(id);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/issue/{id}/addtodepartmentfeedbacks")
    public ResponseEntity<Issue> addToDepartmentFeedbacks(@PathVariable Integer id,
            @RequestBody DepartmentFeedback departmentFeedback) {
        Issue updatedIssue = webClientServiceImpl.addToDepartmentFeedbacks(id, departmentFeedback);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/issue/{id}/updatedepartmentfeedbacks")
    public ResponseEntity<Issue> updateDepartmentFeedbacks(@PathVariable Integer id,
            @RequestBody List<DepartmentFeedback> departmentFeedbacks) {
        Issue updatedIssue = webClientServiceImpl.updateDepartmentFeedbacks(id, departmentFeedbacks);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/issue/{id}/addtoissuedemployees")
    public ResponseEntity<Issue> addToIssuedEmployees(@PathVariable Integer id, @RequestBody Employee employee) {
        Issue updatedIssue = webClientServiceImpl.addToIssuedEmployees(id, employee);
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/issue/{id}/updateissuedemployees")
    public ResponseEntity<Issue> updateIssuedEmployees(@PathVariable Integer id,
            @RequestBody List<Employee> employees) {
        Issue updatedIssue = webClientServiceImpl.updateIssuedEmployees(id, employees);
        return ResponseEntity.ok(updatedIssue);

    }

    @PostMapping("/issue/{id}/updateissueattributes")
    public ResponseEntity<Issue> updateIssueAttributes(@PathVariable Integer id,
            @RequestBody List<IssueAttribute> issueAttributes) {
        Issue updateIssue = webClientServiceImpl.updateIssueAttributes(id, issueAttributes);
        return ResponseEntity.ok(updateIssue);
    }

    @PostMapping("/issue/{id}/updateissuesubject")
    public ResponseEntity<Issue> updateIssueSubject(@PathVariable Integer id, @RequestBody Subject subject) {
        Issue updatedIssue = webClientServiceImpl.updateIssueSubject(id, subject);
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/issue/{id}/updateissueresult")
    public ResponseEntity<Issue> updateIssueResult(@PathVariable Integer id, @RequestBody String issueResult) {
        Issue updatedIssue = webClientServiceImpl.updateIssueResult(id, issueResult);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/registerNewIssue")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<Issue> registerNewIssue(@RequestBody Issue issue) {
        Issue createdIssue = webClientServiceImpl.registerNewIssue(issue);
        return ResponseEntity.ok(createdIssue);
    }

    @GetMapping("/issue")
    public ResponseEntity<Page<Issue>> getAllIssuesPageable(@AuthenticationPrincipal Jwt jwt, @RequestParam int page,
            @RequestParam int size, @RequestParam Optional<String> q, @RequestParam Optional<List<String>> sf) {
        Page<Issue> issues = webClientServiceImpl
                .getAllIssues(PageRequest.of(page, size, Sort.by(Direction.DESC, "id")), jwt, q, sf);
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/issue/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Integer id, @AuthenticationPrincipal AdUserDetails user) {
        Issue issue = webClientServiceImpl.getIssueById(id);
        return ResponseEntity.ok(issue);
    }

    @GetMapping("/issue/{id}/issuedemployees")
    public ResponseEntity<Set<Employee>> getIssuedEmployeesByIssueId(@PathVariable Integer id) {
        Set<Employee> issuedEmployees = webClientServiceImpl.getIssuedEmployeesByIssueId(id);
        return ResponseEntity.ok(issuedEmployees);
    }

    @GetMapping("/issue/{id}/departmentfeedbacks")
    public ResponseEntity<Set<DepartmentFeedback>> getDepartmentFeedbacksByIssueId(@PathVariable Integer id) {
        Set<DepartmentFeedback> departmentFeedbacks = webClientServiceImpl.getDepartmentFeedbacksByIssueId(id);
        return ResponseEntity.ok(departmentFeedbacks);
    }

    @PostMapping("/issue/{id}/uploadFile")
    public ResponseEntity<Issue> uploadFilesToIssue(@PathVariable Integer id,
            @RequestParam MultipartFile[] files) {
        Issue issue = webClientServiceImpl.uploadFilesToIssue(id, files);
        if (issue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(issue);
    }

    @GetMapping("/employee")
    public ResponseEntity<Iterable<AdUser>> getAllEmployees(@RequestParam String q) {
        return ResponseEntity.ok(webClientServiceImpl.findEmployeesByGivenNameSn(q));
    }

    @GetMapping("/availableissueattributes")
    public ResponseEntity<Iterable<IssueAttribute>> getAvailableIssueAttributes() {
        return ResponseEntity.ok(webClientServiceImpl.getAvailableIssueAttributes());
    }

    @GetMapping("/notifications/count")
    public ResponseEntity<Long> getNotificationCount(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(webClientServiceImpl.getNotificationCount(jwt));
    }

    @GetMapping("/notifications")
    public ResponseEntity<Page<Notification>> getUserNotifications(@AuthenticationPrincipal Jwt jwt,
            @RequestParam int page,
            @RequestParam int size) {
        PageRequest pageable = PageRequest.of(page, size,
                Sort.by(Sort.Order.asc("isRead"), Sort.Order.desc("createdAt")));
        Page<Notification> notifications = webClientServiceImpl.getUserNotifications(pageable, jwt);

        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/report")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<Iterable<BasicReportRow>> getReport(
            @RequestParam Timestamp start,
            @RequestParam Timestamp end) {

        return ResponseEntity.ok(webClientServiceImpl.getReport(start.toLocalDateTime().toLocalDate(),
                end.toLocalDateTime().toLocalDate()));
    }

}
