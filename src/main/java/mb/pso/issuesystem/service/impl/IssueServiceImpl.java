package mb.pso.issuesystem.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import mb.pso.issuesystem.entity.Issue;
import mb.pso.issuesystem.repository.IssueRepository;
import mb.pso.issuesystem.service.IssueService;

@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;

    public IssueServiceImpl(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
    public Issue create(Issue issue) {

        return issueRepository.save(issue);
    }

    @Override
    public void delete(Issue issue) {
        issueRepository.delete(issue);
    }

    @Override
    public Optional<Issue> get(String id) {

        return issueRepository.findById(id);
    }

    @Override
    public Iterable<Issue> getAll() {

        return issueRepository.findAll();
    }

    @Override
    public Issue update(Issue issue) {
        Optional<Issue> i = issueRepository.findById(issue.getId());
        assert i.isPresent();
        return issueRepository.save(issue);
    }

}
