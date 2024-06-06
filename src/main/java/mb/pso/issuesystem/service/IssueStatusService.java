package mb.pso.issuesystem.service;

import java.util.Optional;

import mb.pso.issuesystem.entity.IssueStatus;

public interface IssueStatusService {
    public IssueStatus create(IssueStatus issueStatus);

    public IssueStatus update(IssueStatus issueStatus);

    public void delete(IssueStatus issueStatus);

    public IssueStatus get(String id);

    public Optional<IssueStatus> findByName(String name);

    public Iterable<IssueStatus> getAll();
}
