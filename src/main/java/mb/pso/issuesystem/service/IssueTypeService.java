package mb.pso.issuesystem.service;

import mb.pso.issuesystem.entity.IssueType;

public interface IssueTypeService {
    public IssueType create(IssueType issueType);

    public IssueType update(IssueType issueType);

    public void delete(IssueType issueType);

    public IssueType get(String id);

    public Iterable<IssueType> getAll();
}
