package mb.pso.issuesystem.service.impl;

import java.util.Optional;

import mb.pso.issuesystem.entity.AdditionalAttributeType;
import mb.pso.issuesystem.repository.AdditionalAttributeTypeRepository;
import mb.pso.issuesystem.service.AdditionalAttributeTypeService;
//[ ] REFACTOR
public class AdditionalAttributeTypeServiceImpl implements AdditionalAttributeTypeService {

    private final AdditionalAttributeTypeRepository additionalAttributeTypeRepository;

    public AdditionalAttributeTypeServiceImpl(AdditionalAttributeTypeRepository additionalAttributeTypeRepository) {
        this.additionalAttributeTypeRepository = additionalAttributeTypeRepository;
    }

    @Override
    public AdditionalAttributeType create(AdditionalAttributeType additionalAttributeType) {

        return additionalAttributeTypeRepository.save(additionalAttributeType);
    }

    @Override
    public void delete(AdditionalAttributeType additionalAttributeType) {
        additionalAttributeTypeRepository.delete(additionalAttributeType);

    }

    @Override
    public Optional<AdditionalAttributeType> get(Integer id) {

        return additionalAttributeTypeRepository.findById(id);
    }

    @Override
    public Iterable<AdditionalAttributeType> getAll() {

        return additionalAttributeTypeRepository.findAll();
    }

    @Override
    public AdditionalAttributeType update(AdditionalAttributeType additionalAttributeType) {
        Optional<AdditionalAttributeType> a = additionalAttributeTypeRepository
                .findById(additionalAttributeType.getId());
        assert a.isPresent();
        return additionalAttributeTypeRepository.save(additionalAttributeType);
    }

}
