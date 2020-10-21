package net.class101.homework1.domain.db;

import java.util.List;
import java.util.Optional;

public interface DAO<T, ID> {
    <S extends T> S save(S entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
    void delete(T entity);
}
