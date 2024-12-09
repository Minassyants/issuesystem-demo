package mb.pso.issuesystem.service.impl.core;

import mb.pso.issuesystem.entity.core.AdditionalAttributeType;
import mb.pso.issuesystem.repository.core.AdditionalAttributeTypeRepository;
import mb.pso.issuesystem.repository.core.CombinedRepository;
import mb.pso.issuesystem.service.AbstractCrudService;


public class AdditionalAttributeTypeService extends AbstractCrudService<AdditionalAttributeType, Integer> {

    private final AdditionalAttributeTypeRepository repository;

    public AdditionalAttributeTypeService(AdditionalAttributeTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Integer getId(AdditionalAttributeType entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<AdditionalAttributeType, Integer> getRepository() {
        return repository;
    }

}
