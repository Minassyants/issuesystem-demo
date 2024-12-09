package mb.pso.issuesystem.service.impl.core;

import java.util.Optional;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.entity.QClient;
import mb.pso.issuesystem.entity.core.Client;
import mb.pso.issuesystem.repository.core.ClientRepository;
import mb.pso.issuesystem.repository.core.CombinedRepository;
import mb.pso.issuesystem.service.AbstractCrudService;


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

    public Optional<Client> getByPhoneNumber(String phoneNumber) {
        Predicate predicate = QClient.client.phoneNumber.eq(phoneNumber);
        return repository.findOne(predicate);
    }

    public Client resolve(Client client) {
        return getByPhoneNumber(client.getPhoneNumber()).orElse(client);
    }

}
