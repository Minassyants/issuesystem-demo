package mb.pso.issuesystem.service;

import java.util.Optional;

import mb.pso.issuesystem.entity.Issue;

public interface IssueService {
    public Issue create(Issue issue);

    public Issue update(Issue issue);

    public void delete(Issue issue);

    public Issue get(String id);

    public Optional<Issue> findByName(String name);

    public Iterable<Issue> getAll();
}
