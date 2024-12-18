package mb.pso.issuesystem.service;

import java.util.Optional;

import org.springframework.data.domain.Example;

import com.querydsl.core.types.Predicate;


public interface CrudService<T, ID> {
    T create(T entity);

    Iterable<T> saveAll(Iterable<T> collection);

    T update(T entity);

    void delete(T entity);

    void deleteById(ID id);

    Optional<T> get(ID id);

    Optional<T> get(Example<T> example);

    Optional<T> get(Predicate predicate);

    T getOrThrow(ID id);

    T getOrCreate(T entity);

    Iterable<T> getAll();

    Iterable<T> getAll(Predicate predicate);
}
