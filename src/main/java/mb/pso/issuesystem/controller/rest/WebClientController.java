package mb.pso.issuesystem.controller.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mb.pso.issuesystem.entity.AdUser;
import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.entity.Users;
import mb.pso.issuesystem.service.impl.UserServiceImpl;
import mb.pso.issuesystem.service.webclient.impl.WebClientServiceImpl;

@RestController
@CrossOrigin(origins = "*")
public class WebClientController {

    private final WebClientServiceImpl webClientServiceImpl;
    private final UserServiceImpl userServiceImpl;

    public WebClientController(WebClientServiceImpl webClientServiceImpl, UserServiceImpl userServiceImpl) {
        this.webClientServiceImpl = webClientServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    // TODO: Это полная ...
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
    public ResponseEntity<Issue> registerNewIssue(@RequestBody Issue issue) {
        Issue createdIssue = webClientServiceImpl.registerNewIssue(issue);
        return ResponseEntity.ok(createdIssue);
    }

    @GetMapping("/issues")
    public ResponseEntity<Page<Issue>> getAllIssuesPageable(Authentication authentication, @RequestParam int page,
            @RequestParam int size) {
        System.out.println(authentication.getAuthorities().toString());
        Page<Issue> issues = webClientServiceImpl
                .getAllIssues(PageRequest.of(page, size, Sort.by(Direction.DESC, "id")));
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/issues/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Integer id) {
        Optional<Issue> issue = webClientServiceImpl.getIssueById(id);
        if (issue.isPresent())
            return ResponseEntity.ok(issue.get());
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/employee")
    public ResponseEntity<Iterable<AdUser>> getAllEmployees(@RequestParam String q) {
        return ResponseEntity.ok(webClientServiceImpl.findEmployeesByGivenNameSn(q));
    }

}
