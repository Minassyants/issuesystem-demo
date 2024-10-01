package mb.pso.issuesystem.controller.rest;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import mb.pso.issuesystem.entity.Users;
import mb.pso.issuesystem.entity.im.Chat;
import mb.pso.issuesystem.entity.im.Message;
import mb.pso.issuesystem.service.impl.ImServiceImpl;
import mb.pso.issuesystem.service.impl.UserServiceImpl;
import mb.pso.issuesystem.service.webclient.impl.WebClientServiceImpl;

@RestController
@CrossOrigin(originPatterns = "*", origins = "*")
public class WebClientController {

    private final WebClientServiceImpl webClientServiceImpl;
    private final UserServiceImpl userServiceImpl;
    private final ImServiceImpl imServiceImpl;

    public WebClientController(WebClientServiceImpl webClientServiceImpl, UserServiceImpl userServiceImpl,
            ImServiceImpl imServiceImpl) {
        this.webClientServiceImpl = webClientServiceImpl;
        this.userServiceImpl = userServiceImpl;
        this.imServiceImpl = imServiceImpl;
    }

    // FIXME раскидать по сервисам????
    @PostMapping("/chat/{chatId}/deleteMember")
    public ResponseEntity<Chat> deleteMemberFromChat(@PathVariable Integer chatId, @RequestBody Employee employee) {
        Chat chat = imServiceImpl.deleteMemberFromChat(chatId, employee);
        return ResponseEntity.ok(chat);
    }

    @PostMapping("/chat/{chatId}/addmember")
    public ResponseEntity<Chat> addMemberToChat(@PathVariable Integer chatId, @RequestBody Employee employee) {

        Chat chat = imServiceImpl.addMemberToChat(chatId, employee);
        return ResponseEntity.ok(chat);
    }

    @GetMapping("/chat/{id}")
    public ResponseEntity<Chat> getChatById(@PathVariable Integer id) {
        return ResponseEntity.ok(imServiceImpl.getChatById(id));
    }

    @GetMapping("/chat/messages/{id}")
    public ResponseEntity<Page<Message>> getMessagesPageable(@PathVariable Integer id,
            @RequestParam Optional<Integer> size,
            @RequestParam Optional<Integer> page) {
        Integer _page = page.orElse(0);
        Integer _size = size.orElse(10);

        return ResponseEntity.ok(imServiceImpl.getMessagesByChatIdPageable(id,
                PageRequest.of(_page, _size, Sort.by(Direction.DESC, "createdAt"))));

    }

    @PostMapping("/chat/send")
    public void sendMessage(@RequestBody Message message) {
        imServiceImpl.sendMessage(message);
    }

    // BUG: Это полная ...
    @GetMapping("/auth")
    public ResponseEntity<Users> auth(@RequestParam String username) {
        Optional<Users> user = userServiceImpl.getByName(username);
        assert user.isPresent();
        return ResponseEntity.ok(user.get());
    }

    @PostMapping("/issue/{id}/setclosed")
    public ResponseEntity<Issue> setClosed(@PathVariable Integer id) {
        Issue updatedIssue = webClientServiceImpl.setClosed(id);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/issue/{id}/setinprogress")
    public ResponseEntity<Issue> setInProgress(@PathVariable Integer id) {
        Issue updatedIssue = webClientServiceImpl.setInProgress(id);
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

    @PostMapping("/issue/{id}/updatedepartmentfeedbacks")
    public ResponseEntity<Issue> updateDepartmentFeedbacks(@PathVariable Integer id,
            @RequestBody List<DepartmentFeedback> departmentFeedbacks) {
        Issue updatedIssue = webClientServiceImpl.updateDepartmentFeedbacks(id, departmentFeedbacks);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/issue/{id}/updateissuedemployees")
    public ResponseEntity<Issue> updateIssuedEmployees(@PathVariable Integer id,
            @RequestBody List<Employee> employees) {
        Issue updatedIssue = webClientServiceImpl.updateIssuedEmployees(id, employees);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
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
        Optional<Issue> issue = webClientServiceImpl.getIssueById(id);
        if (issue.isPresent())
            return ResponseEntity.ok(issue.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/issue/{id}/issuedemployees")
    public ResponseEntity<List<Employee>> getIssuedEmployeesByIssueId(@PathVariable Integer id) {
        List<Employee> issuedEmployees = webClientServiceImpl.getIssuedEmployeesByIssueId(id);
        if (issuedEmployees == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(issuedEmployees);
    }

    @GetMapping("/issue/{id}/departmentfeedbacks")
    public ResponseEntity<List<DepartmentFeedback>> getDepartmentFeedbacksByIssueId(@PathVariable Integer id) {
        List<DepartmentFeedback> departmentFeedbacks = webClientServiceImpl.getDepartmentFeedbacksByIssueId(id);
        if (departmentFeedbacks == null)
            return ResponseEntity.badRequest().build();
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

    @GetMapping("/report")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<Iterable<BasicReportRow>> getReport(
            @RequestParam Timestamp start,
            @RequestParam Timestamp end) {

        return ResponseEntity.ok(webClientServiceImpl.getReport(start.toLocalDateTime().toLocalDate(),
                end.toLocalDateTime().toLocalDate()));
    }

}
