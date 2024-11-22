package dao;

import java.util.List;
import java.util.Optional;

public interface BaseDao<K> {
    Optional<K> findById(Long id);
    K save(K entity);
    boolean update(K entity);
    boolean delete(Long id);
}
