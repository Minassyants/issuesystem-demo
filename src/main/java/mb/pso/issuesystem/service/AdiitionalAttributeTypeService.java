package mb.pso.issuesystem.service;

import java.util.Optional;

import mb.pso.issuesystem.entity.AdditionalAttributeType;

public interface AdiitionalAttributeTypeService {
    public AdditionalAttributeType create(AdditionalAttributeType additionalAttributeType);

    public AdditionalAttributeType update(AdditionalAttributeType additionalAttributeType);

    public void delete(AdditionalAttributeType additionalAttributeType);

    public AdditionalAttributeType get(String id);

    public Optional<AdditionalAttributeType> findByName(String name);

    public Iterable<AdditionalAttributeType> getAll();
}
