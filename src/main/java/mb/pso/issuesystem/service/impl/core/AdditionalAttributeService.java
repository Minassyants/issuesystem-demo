package mb.pso.issuesystem.service.impl.core;

import java.util.List;

import org.springframework.data.domain.Example;

import mb.pso.issuesystem.entity.AdditionalAttribute;
import mb.pso.issuesystem.repository.core.AdditionalAttributeRepository;
import mb.pso.issuesystem.repository.core.CombinedRepository;
import mb.pso.issuesystem.service.AbstractCrudService;

//[x] REFACTOR
public class AdditionalAttributeService extends AbstractCrudService<AdditionalAttribute, Integer> {

    private final AdditionalAttributeRepository repository;

    public AdditionalAttributeService(AdditionalAttributeRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Integer getId(AdditionalAttribute entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<AdditionalAttribute, Integer> getRepository() {
        return repository;
    }

    public List<AdditionalAttribute> resolveAdditionalAttributes(List<AdditionalAttribute> attributes) {
        return attributes.stream()
                .map(attr -> get(Example.of(attr)).orElse(attr))
                .toList();
    }

}
