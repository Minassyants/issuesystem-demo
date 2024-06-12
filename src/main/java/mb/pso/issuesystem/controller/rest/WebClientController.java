package mb.pso.issuesystem.controller.rest;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.service.webclient.impl.WebClientServiceImpl;

@RestController

public class WebClientController {

    private final WebClientServiceImpl webClientServiceImpl;

    public WebClientController(WebClientServiceImpl webClientServiceImpl) {
        this.webClientServiceImpl = webClientServiceImpl;
    }

    @PostMapping("/registerNewIssue")
    public ResponseEntity<Issue> registerNewIssue(@RequestBody Issue issue) {
        Issue createdIssue = webClientServiceImpl.registerNewIssue(issue);
        return ResponseEntity.ok(createdIssue);
    }

    @GetMapping("/issues")
    public ResponseEntity<Page<Issue>> getAllIssuesPageable(@RequestParam int page, @RequestParam int size) {
        Page<Issue> issues = webClientServiceImpl.getAllIssues(PageRequest.of(page, size));
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/issues/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable String id) {
        Optional<Issue> issue = webClientServiceImpl.getIssueById(id);
        if (issue.isPresent())
            return ResponseEntity.ok(issue.get());
        return ResponseEntity.notFound().build();
    }

}