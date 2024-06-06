package mb.pso.issuesystem.service;

import java.util.Optional;

import mb.pso.issuesystem.entity.AdditionalAttribute;

public interface AdditionalAttributeService {
    public AdditionalAttribute create(AdditionalAttribute additionalAttribute);

    public AdditionalAttribute update(AdditionalAttribute additionalAttribute);

    public void delete(AdditionalAttribute additionalAttribute);

    public AdditionalAttribute get(String id);

    public Optional<AdditionalAttribute> findByName(String name);

    public Iterable<AdditionalAttribute> getAll();
}
