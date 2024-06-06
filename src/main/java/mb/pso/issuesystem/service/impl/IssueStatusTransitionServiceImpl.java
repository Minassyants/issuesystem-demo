package mb.pso.issuesystem.service.impl;

import java.util.Optional;

import mb.pso.issuesystem.relation.IssueStatusTransition;
import mb.pso.issuesystem.repository.IssueStatusTransitionRepository;
import mb.pso.issuesystem.service.IssueStatusTransitionService;

public class IssueStatusTransitionServiceImpl implements IssueStatusTransitionService {

    private final IssueStatusTransitionRepository issueStatusTransitionRepository;

    public IssueStatusTransitionServiceImpl(IssueStatusTransitionRepository issueStatusTransitionRepository) {
        this.issueStatusTransitionRepository = issueStatusTransitionRepository;
    }

    @Override
    public IssueStatusTransition create(IssueStatusTransition issueStatusTransition) {

        return issueStatusTransitionRepository.save(issueStatusTransition);
    }

    @Override
    public void delete(IssueStatusTransition issueStatusTransition) {
        issueStatusTransitionRepository.delete(issueStatusTransition);

    }

    @Override
    public Optional<IssueStatusTransition> get(String id) {

        return issueStatusTransitionRepository.findById(id);
    }

    @Override
    public Iterable<IssueStatusTransition> getAll() {

        return issueStatusTransitionRepository.findAll();
    }

    @Override
    public IssueStatusTransition update(IssueStatusTransition issueStatusTransition) {
        Optional<IssueStatusTransition> i = issueStatusTransitionRepository.findById(issueStatusTransition.getId());
        assert i.isPresent();
        return issueStatusTransitionRepository.save(issueStatusTransition);
    }

}
