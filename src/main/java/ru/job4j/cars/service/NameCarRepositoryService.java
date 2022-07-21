package ru.job4j.cars.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.NameCar;
import ru.job4j.cars.persistence.NameCarRepository;

import java.util.List;

@ThreadSafe
@Service
public class NameCarRepositoryService {

    private NameCarRepository store;

    public NameCarRepositoryService(NameCarRepository store) {
        this.store = store;
    }

    public List<NameCar> findAll() {
        return store.findAll();
    }

    public NameCar findById(int id) {
        return store.findById(id);
    }
}
