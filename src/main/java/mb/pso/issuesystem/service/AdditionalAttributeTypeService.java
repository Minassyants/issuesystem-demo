package mb.pso.issuesystem.service;

import java.util.Optional;

import mb.pso.issuesystem.entity.AdditionalAttributeType;

public interface AdditionalAttributeTypeService {
    public AdditionalAttributeType create(AdditionalAttributeType additionalAttributeType);

    public AdditionalAttributeType update(AdditionalAttributeType additionalAttributeType);

    public void delete(AdditionalAttributeType additionalAttributeType);

    public Optional<AdditionalAttributeType> get(Integer id);

    public Iterable<AdditionalAttributeType> getAll();

}
