package mb.pso.issuesystem.service;

import java.util.Optional;

import mb.pso.issuesystem.entity.Good;

public interface GoodService {
    public Good create(Good good);

    public Good update(Good good);

    public void delete(Good good);

    public Good get(String id);

    public Optional<Good> findByName(String name);

    public Iterable<Good> getAll();
}
