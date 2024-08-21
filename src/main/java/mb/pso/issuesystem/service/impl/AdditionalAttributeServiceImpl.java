package mb.pso.issuesystem.service.impl;

import java.util.Optional;

import mb.pso.issuesystem.entity.AdditionalAttribute;
import mb.pso.issuesystem.repository.AdditionalAttributeRepository;
import mb.pso.issuesystem.service.AdditionalAttributeService;

public class AdditionalAttributeServiceImpl implements AdditionalAttributeService {

    private final AdditionalAttributeRepository additionalAttributeRepository;

    public AdditionalAttributeServiceImpl(AdditionalAttributeRepository additionalAttributeRepository) {
        this.additionalAttributeRepository = additionalAttributeRepository;
    }

    @Override
    public AdditionalAttribute create(AdditionalAttribute additionalAttribute) {

        return additionalAttributeRepository.save(additionalAttribute);
    }

    @Override
    public void delete(AdditionalAttribute aditionalAttribute) {
        additionalAttributeRepository.delete(aditionalAttribute);

    }

    @Override
    public Optional<AdditionalAttribute> get(Integer id) {

        return additionalAttributeRepository.findById(id);
    }

    @Override
    public Iterable<AdditionalAttribute> getAll() {
        return additionalAttributeRepository.findAll();
    }

    @Override
    public AdditionalAttribute update(AdditionalAttribute additionalAttribute) {
        Optional<AdditionalAttribute> a = additionalAttributeRepository.findById(additionalAttribute.getId());
        assert a.isPresent();
        return additionalAttributeRepository.save(additionalAttribute);
    }

}
