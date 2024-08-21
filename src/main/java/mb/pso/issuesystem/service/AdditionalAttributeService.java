package mb.pso.issuesystem.service;

import java.util.Optional;

import mb.pso.issuesystem.entity.AdditionalAttribute;

public interface AdditionalAttributeService {
    public AdditionalAttribute create(AdditionalAttribute additionalAttribute);

    public AdditionalAttribute update(AdditionalAttribute additionalAttribute);

    public void delete(AdditionalAttribute aditionalAttribute);

    public Optional<AdditionalAttribute> get(Integer id);

    public Iterable<AdditionalAttribute> getAll();
}
