package mb.pso.issuesystem.service;

import mb.pso.issuesystem.entity.AdditionalAttributeType;

public interface AdditionalAttributeTypeService {
    public AdditionalAttributeType create(AdditionalAttributeType additionalAttributeType);

    public AdditionalAttributeType update(AdditionalAttributeType additionalAttributeType);

    public void delete(AdditionalAttributeType additionalAttributeType);

    public AdditionalAttributeType get(String id);

    public Iterable<AdditionalAttributeType> getAll();

}
