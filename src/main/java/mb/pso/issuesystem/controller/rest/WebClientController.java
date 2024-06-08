package mb.pso.issuesystem.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

}
