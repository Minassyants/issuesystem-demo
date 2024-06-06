package mb.pso.issuesystem.service;

import mb.pso.issuesystem.entity.Client;

public interface ClientService {
    public Client create(Client client);

    public Client update(Client client);

    public void delete(Client client);

    public Client get(String id);

    public Iterable<Client> getAll();
}
