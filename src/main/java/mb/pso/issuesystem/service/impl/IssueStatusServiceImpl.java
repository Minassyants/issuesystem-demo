package mb.pso.issuesystem.service.impl;

import java.util.Optional;

import mb.pso.issuesystem.entity.IssueStatus;
import mb.pso.issuesystem.repository.IssueStatusRepository;
import mb.pso.issuesystem.service.IssueStatusService;

public class IssueStatusServiceImpl implements IssueStatusService {

    private final IssueStatusRepository issueStatusRepository;

    public IssueStatusServiceImpl(IssueStatusRepository issueStatusRepository) {
        this.issueStatusRepository = issueStatusRepository;
    }

    @Override
    public IssueStatus create(IssueStatus issueStatus) {

        return issueStatusRepository.save(issueStatus);
    }

    @Override
    public void delete(IssueStatus issueStatus) {
        issueStatusRepository.delete(issueStatus);

    }

    @Override
    public Optional<IssueStatus> get(String id) {

        return issueStatusRepository.findById(id);
    }

    @Override
    public Iterable<IssueStatus> getAll() {

        return issueStatusRepository.findAll();
    }

    @Override
    public IssueStatus update(IssueStatus issueStatus) {
        Optional<IssueStatus> i = issueStatusRepository.findById(issueStatus.getId());
        assert i.isPresent();
        return issueStatusRepository.save(issueStatus);
    }

}
