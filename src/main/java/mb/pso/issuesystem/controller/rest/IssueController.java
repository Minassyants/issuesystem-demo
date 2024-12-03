package mb.pso.issuesystem.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.service.impl.core.IssueDocumentService;
import mb.pso.issuesystem.service.impl.core.IssueService;

//[ ] REFACTOR
@RestController
@RequestMapping("/api/issue")
public class IssueController {
    private final IssueService issueService;
    private final IssueDocumentService issueDocumentServiceImpl;

    public IssueController(IssueService issueService, IssueDocumentService issueDocumentServiceImpl) {
        this.issueService = issueService;
        this.issueDocumentServiceImpl = issueDocumentServiceImpl;
    }

    @PostMapping
    public Issue create(@RequestBody Issue issue) {
        Issue createdIssue = issueService.create(issue);

        return createdIssue;
    }

    @GetMapping("/reindex")
    public void reindexSearch() {
        Iterable<Issue> issues = issueService.getAll();
        issues.forEach(issue -> {
            issueDocumentServiceImpl.convertAndSave(issue);
        });
    }

}
