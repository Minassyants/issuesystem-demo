package mb.pso.issuesystem.service;

import mb.pso.issuesystem.entity.IssueStatus;

public interface IssueStatusService {
    public IssueStatus create(IssueStatus issueStatus);

    public IssueStatus update(IssueStatus issueStatus);

    public void delete(IssueStatus issueStatus);

    public IssueStatus get(String id);

    public Iterable<IssueStatus> getAll();
}
