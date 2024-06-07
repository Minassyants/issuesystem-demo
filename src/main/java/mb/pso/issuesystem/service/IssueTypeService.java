package mb.pso.issuesystem.service;

import java.util.Optional;

import mb.pso.issuesystem.entity.IssueType;

public interface IssueTypeService {
    public IssueType create(IssueType issueType);

    public IssueType update(IssueType issueType);

    public void delete(IssueType issueType);

    public Optional<IssueType> get(String id);

    public Iterable<IssueType> getAll();
}
