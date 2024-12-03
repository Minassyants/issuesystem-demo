package mb.pso.issuesystem.service.impl.core;

import mb.pso.issuesystem.entity.AdditionalAttribute;
import mb.pso.issuesystem.repository.AdditionalAttributeRepository;
import mb.pso.issuesystem.repository.CombinedRepository;
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

}
