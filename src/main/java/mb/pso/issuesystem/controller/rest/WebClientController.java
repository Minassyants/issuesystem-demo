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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.Users;
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

    @GetMapping("/chat/{id}")
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

    @PostMapping("/setClosed")
    public ResponseEntity<Issue> setClosed(@RequestBody Issue issue) {
        Issue updatedIssue = webClientServiceImpl.setClosed(issue);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/updateIssueResult")
    public ResponseEntity<Issue> updateIssueResult(@RequestBody Issue issue) {
        Issue updatedIssue = webClientServiceImpl.updateIssueResult(issue);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/updateDepartmentFeedback")
    public ResponseEntity<Issue> updateDepartmentFeedback(@RequestBody Issue issue) {
        Issue updatedIssue = webClientServiceImpl.updateDepartmentFeedback(issue);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);

    }

    @PostMapping("/setPendingResult")
    public ResponseEntity<Issue> setPendingResult(@RequestBody Issue issue) {

        Issue updatedIssue = webClientServiceImpl.setPending(issue);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/setInProgress")
    public ResponseEntity<Issue> setInProgress(@RequestBody Issue issue) {
        Issue updatedIssue = webClientServiceImpl.setInProgress(issue);
        if (updatedIssue == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(updatedIssue);
    }

    @PostMapping("/updateInternalInfo")
    public ResponseEntity<Issue> updateInternalInfo(@RequestBody Issue issue) {
        Issue updatedIssue = webClientServiceImpl.updateInternalInfo(issue);
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

    @GetMapping("/issues")
    public ResponseEntity<Page<Issue>> getAllIssuesPageable(Authentication authentication, @RequestParam int page,
            @RequestParam int size, @RequestParam Optional<String> q, @RequestParam Optional<List<String>> sf) {
        Page<Issue> issues = webClientServiceImpl
                .getAllIssues(PageRequest.of(page, size, Sort.by(Direction.DESC, "id")), authentication, q, sf);
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/issues/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Integer id, @AuthenticationPrincipal AdUserDetails user) {
        Optional<Issue> issue = webClientServiceImpl.getIssueById(id);
        if (issue.isPresent())
            return ResponseEntity.ok(issue.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/issues/{id}/uploadFile")
    public ResponseEntity<String> uploadFilesToIssue(@PathVariable Integer id,
            @RequestParam("files") MultipartFile[] files) {
        webClientServiceImpl.uploadFilesToIssue(id, files);

        return ResponseEntity.ok("ok");
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
