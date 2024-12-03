package mb.pso.issuesystem.service.impl.core;

import mb.pso.issuesystem.entity.Client;
import mb.pso.issuesystem.repository.ClientRepository;
import mb.pso.issuesystem.repository.CombinedRepository;
import mb.pso.issuesystem.service.AbstractCrudService;

//[x] REFACTOR
public class ClientService extends AbstractCrudService<Client, Integer> {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Integer getId(Client entity) {
        return entity.getId();
    }

    @Override
    protected CombinedRepository<Client, Integer> getRepository() {
        return repository;
    }

}
