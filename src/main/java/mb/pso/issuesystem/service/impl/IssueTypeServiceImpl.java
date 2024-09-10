package mb.pso.issuesystem.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.IssueType;
import mb.pso.issuesystem.repository.IssueTypeRepository;
import mb.pso.issuesystem.service.IssueTypeService;

@Service
public class IssueTypeServiceImpl implements IssueTypeService {
    private final IssueTypeRepository issueTypeRepository;

    public IssueTypeServiceImpl(IssueTypeRepository issueTypeRepository) {
        this.issueTypeRepository = issueTypeRepository;
    }

    @Override
    public IssueType create(IssueType issueType) {

        return issueTypeRepository.save(issueType);
    }

    @Override
    public void deleteById(Integer id) {
        issueTypeRepository.deleteById(id);

    }

    @Override
    public Optional<IssueType> get(Integer id) {

        return issueTypeRepository.findById(id);
    }

    @Override
    public Iterable<IssueType> getAll() {

        return issueTypeRepository.findAll();
    }

    public Iterable<IssueType> getAll(Predicate predicate) {

        return issueTypeRepository.findAll(predicate);
    }

    @Override
    public IssueType update(IssueType issueType) {
        Optional<IssueType> i = issueTypeRepository.findById(issueType.getId());
        assert i.isPresent();
        return issueTypeRepository.save(issueType);
    }

}
