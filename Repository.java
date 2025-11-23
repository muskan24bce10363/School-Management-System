

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    void add(T item);
    void update(T item);
    void delete(int id);
    Optional<T> findById(int id);
    List<T> findAll();
    boolean exists(int id);
}