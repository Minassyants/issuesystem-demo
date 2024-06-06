package mb.pso.issuesystem.service;

import mb.pso.issuesystem.entity.Good;

public interface GoodService {
    public Good create(Good good);

    public Good update(Good good);

    public void delete(Good good);

    public Good get(String id);

    public Iterable<Good> getAll();
}
