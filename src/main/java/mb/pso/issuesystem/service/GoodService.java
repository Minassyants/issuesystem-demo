package mb.pso.issuesystem.service;

import mb.pso.issuesystem.entity.Good;
import mb.pso.issuesystem.entity.Subject;

public interface GoodService {
    public Good create(Good good);

    public Good update(Good good);

    public void delete(Good good);

    public Subject get(Integer id);

    public Iterable<Subject> getAll();
}
