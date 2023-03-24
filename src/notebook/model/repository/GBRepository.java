package notebook.model.repository;

import notebook.model.User;

import java.util.List;
import java.util.Optional;

public interface GBRepository {
    List<String> readAll();
    List<User> findAll();
    User create(User e);
    Optional<User> findById(Long id);
    Optional<User> update(Long id, User e);
    boolean delete(Long id);
}
