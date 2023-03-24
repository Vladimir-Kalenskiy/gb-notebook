package notebook.controller;

import notebook.model.User;
import notebook.model.repository.GBRepository;

import java.util.List;
import java.util.Optional;

public class UserController {
    private final GBRepository repository;

    public UserController(GBRepository repository) {
        this.repository = repository;
    }

    public void saveUser(User user) {
        repository.create(user);
    }

    public User readUser(Long userId) throws Exception {
        Optional<User> users = repository.findById(userId);
        if (users.isPresent()) return users.get();

        throw new RuntimeException("User not found");
    }

    public void deleteUser(String userId) {
        repository.delete(Long.parseLong(userId));
    }

    public List<User> getListsUsers() {
        return repository.findAll();
    }
}
