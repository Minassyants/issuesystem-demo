package mb.pso.issuesystem.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.service.impl.IssueServiceImpl;

@RestController
@RequestMapping("/api/issue")
public class IssueController {
    private final IssueServiceImpl issueServiceImpl;

    public IssueController(IssueServiceImpl issueServiceImpl) {
        this.issueServiceImpl = issueServiceImpl;
    }

    @PostMapping
    public ResponseEntity<Issue> create(@RequestBody Issue issue) {
        Issue i = issueServiceImpl.create(issue);
        return ResponseEntity.ok(i);
    }
}
