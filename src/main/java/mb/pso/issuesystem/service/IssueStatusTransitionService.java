package mb.pso.issuesystem.service;

import java.util.Optional;

import mb.pso.issuesystem.relation.IssueStatusTransition;

public interface IssueStatusTransitionService {
    public IssueStatusTransition create(IssueStatusTransition issueStatusTransition);

    public IssueStatusTransition update(IssueStatusTransition issueStatusTransition);

    public void delete(IssueStatusTransition issueStatusTransition);

    public Optional<IssueStatusTransition> get(String id);

    public Iterable<IssueStatusTransition> getAll();
}
