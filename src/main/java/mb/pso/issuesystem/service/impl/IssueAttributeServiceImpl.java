package mb.pso.issuesystem.service.impl;

import java.util.Optional;

import mb.pso.issuesystem.entity.IssueAttribute;
import mb.pso.issuesystem.repository.IssueAttributeRepository;
import mb.pso.issuesystem.service.IssueAttributeService;

public class IssueAttributeServiceImpl implements IssueAttributeService {
    private final IssueAttributeRepository issueAttributeRepository;

    public IssueAttributeServiceImpl(IssueAttributeRepository issueAttributeRepository) {
        this.issueAttributeRepository = issueAttributeRepository;
    }

    @Override
    public IssueAttribute create(IssueAttribute issueAttribute) {
        
        return issueAttributeRepository.save(issueAttribute);
    }

    @Override
    public void delete(IssueAttribute issueAttribute) {
        issueAttributeRepository.delete(issueAttribute);

    }

    @Override
    public Optional<IssueAttribute> get(String id) {
        
        return issueAttributeRepository.findById(id);
    }

    @Override
    public Iterable<IssueAttribute> getAll() {
        
        return issueAttributeRepository.findAll();
    }

    @Override
    public IssueAttribute update(IssueAttribute issueAttribute) {
        Optional<IssueAttribute> i = issueAttributeRepository.findById(issueAttribute.getId());
        assert i.isPresent();
        return issueAttributeRepository.save(issueAttribute);
    }

}
