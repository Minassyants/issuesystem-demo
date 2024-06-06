package mb.pso.issuesystem.service;

import mb.pso.issuesystem.entity.AdditionalAttribute;

public interface AdditionalAttributeService {
    public AdditionalAttribute create(AdditionalAttribute additionalAttribute);

    public AdditionalAttribute update(AdditionalAttribute additionalAttribute);

    public void delete(AdditionalAttribute aditionalAttribute);

    public AdditionalAttribute get(String id);

    public Iterable<AdditionalAttribute> getAll();
}
