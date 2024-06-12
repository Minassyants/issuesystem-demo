package mb.pso.issuesystem.service;

import java.util.Optional;

import mb.pso.issuesystem.entity.IssueAttribute;

public interface IssueAttributeService {
    public IssueAttribute create(IssueAttribute issueAttribute);

    public IssueAttribute update(IssueAttribute issueAttribute);

    public void delete(IssueAttribute issueAttribute);

    public Optional<IssueAttribute> get(String id);

    public Iterable<IssueAttribute> getAll();
}