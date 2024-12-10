package mb.pso.issuesystem.service;

import java.util.Optional;

import org.springframework.data.domain.Example;

import com.querydsl.core.types.Predicate;

import mb.pso.issuesystem.repository.core.CombinedRepository;


/**
 * Abstract service for basic CRUD operations.
 * 
 * {@link #create(Object)} prevents persisting entities with a provided
 * {@code id}.
 * <p>
 * <strong>
 * If the entity does not auto-generate IDs, this behavior should be
 * overridden.
 * </strong>
 * </p>
 */
public abstract class AbstractCrudService<T, ID> implements CrudService<T, ID> {

    protected abstract CombinedRepository<T, ID> getRepository();

    protected abstract ID getId(T entity);

    /**
     * Prevents persisting entities with a provided {@code id}. If the entity does
     * not auto-generates IDs, this behavior should be overridden.
     */
    @Override
    public T create(T entity) {
        ID id = getId(entity);
        if (id != null) {
            throw new IllegalArgumentException("Cannot create entity with an existing ID");
        }
        return getRepository().save(entity);
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> collection) {

        return getRepository().saveAll(collection);
    }

    /**
     * Prevents persisting entities with non-existing {@code id}.
     */
    @Override
    public T update(T entity) {
        ID id = getId(entity);
        if (id == null) {
            throw new IllegalArgumentException("Cannot update entity without an ID");
        }
        if (!getRepository().existsById(id)) {
            throw new IllegalArgumentException("Cannot update entity with a non-existing ID");
        }
        return getRepository().save(entity);
    }

    @Override
    public void delete(T entity) {
        getRepository().delete(entity);
    }

    @Override
    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    @Override
    public Optional<T> get(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return getRepository().findById(id);
    }

    @Override
    public Optional<T> get(Example<T> example) {
        return getRepository().findOne(example);
    }

    @Override
    public Optional<T> get(Predicate predicate) {

        return getRepository().findOne(predicate);
    }

    @Override
    public T getOrThrow(ID id) {

        return getRepository()
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "%s: entity with ID %d not found".formatted(getRepository().getClass().toString(), id)));
    }

    @Override
    public T getOrCreate(T entity) {
        ID id = getId(entity);
        return getRepository().findById(id).orElse(getRepository().save(entity));
    }

    @Override
    public Iterable<T> getAll() {
        return getRepository().findAll();
    }

    @Override
    public Iterable<T> getAll(Predicate predicate) {
        return getRepository().findAll(predicate);
    }

}
