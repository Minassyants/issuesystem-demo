package mb.pso.issuesystem.service;

import java.util.Optional;

import com.querydsl.core.types.Predicate;

//[ ] REFACTOR
public interface CrudService<T, ID> {
    T create(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteById(ID id);

    Optional<T> get(ID id);

    T getOrThrow(ID id);

    T getOrCreate(T entity);

    Iterable<T> getAll();

    Iterable<T> getAll(Predicate predicate);
}
