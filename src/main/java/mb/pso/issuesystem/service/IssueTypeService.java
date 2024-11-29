package mb.pso.issuesystem.service;

import java.util.Optional;

import mb.pso.issuesystem.entity.IssueType;
//[ ] REFACTOR
public interface IssueTypeService {
    public IssueType create(IssueType issueType);

    public IssueType update(IssueType issueType);

    public void deleteById(Integer id);

    public Optional<IssueType> get(Integer id);

    public Iterable<IssueType> getAll();
}
