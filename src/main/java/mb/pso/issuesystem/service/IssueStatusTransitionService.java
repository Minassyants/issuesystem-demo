package mb.pso.issuesystem.service;

import mb.pso.issuesystem.relation.IssueStatusTransition;

public interface IssueStatusTransitionService {
    public IssueStatusTransition create(IssueStatusTransition issueStatusTransition);

    public IssueStatusTransition update(IssueStatusTransition issueStatusTransition);

    public void delete(IssueStatusTransition issueStatusTransition);

    public IssueStatusTransition get(String id);

    public Iterable<IssueStatusTransition> getAll();
}
