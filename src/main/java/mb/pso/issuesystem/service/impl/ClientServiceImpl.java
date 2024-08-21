package mb.pso.issuesystem.service.impl;

import java.util.Optional;

import mb.pso.issuesystem.entity.Client;
import mb.pso.issuesystem.repository.ClientRepository;
import mb.pso.issuesystem.service.ClientService;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client create(Client client) {

        return clientRepository.save(client);
    }

    @Override
    public void delete(Client client) {
        clientRepository.delete(client);

    }

    @Override
    public Optional<Client> get(Integer id) {

        return clientRepository.findById(id);
    }

    @Override
    public Iterable<Client> getAll() {

        return clientRepository.findAll();
    }

    @Override
    public Client update(Client client) {
        Optional<Client> c = clientRepository.findById(client.getId());
        assert c.isPresent();
        return clientRepository.save(client);
    }

}
