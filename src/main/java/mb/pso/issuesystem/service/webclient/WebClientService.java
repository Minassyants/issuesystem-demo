package mb.pso.issuesystem.service.webclient;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mb.pso.issuesystem.entity.Issue;

public interface WebClientService {

    public Issue registerNewIssue(Issue issue);

    public List<Issue> getAllIssues();

    public Page<Issue> getAllIssues(Pageable pageable);

    public Optional<Issue> getIssueById(String id);
}
