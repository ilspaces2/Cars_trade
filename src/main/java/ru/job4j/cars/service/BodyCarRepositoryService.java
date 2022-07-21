package ru.job4j.cars.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.BodyCar;
import ru.job4j.cars.persistence.BodyCarRepository;

import java.util.List;

@ThreadSafe
@Service
public class BodyCarRepositoryService {

    private BodyCarRepository store;

    public BodyCarRepositoryService(BodyCarRepository store) {
        this.store = store;
    }

    public BodyCar add(BodyCar bodyCar) {
        return store.add(bodyCar);
    }

    public List<BodyCar> findAll() {
        return store.findAll();
    }

    public BodyCar findById(int id) {
        return store.findById(id);
    }
}
