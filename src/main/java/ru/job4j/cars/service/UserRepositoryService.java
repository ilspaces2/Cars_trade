package ru.job4j.cars.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.User;
import ru.job4j.cars.persistence.UserRepository;

import java.util.Optional;

@ThreadSafe
@Service
public class UserRepositoryService {

    private final UserRepository store;

    public UserRepositoryService(UserRepository store) {
        this.store = store;
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findById(int id) {
        return store.findById(id);
    }

    public boolean deleteAll() {
        return store.deleteAll();
    }

    public Optional<User> findUserByEmailAndPass(String userName, String pass) {
        return store.findUserByEmailAndPass(userName, pass);
    }
}
