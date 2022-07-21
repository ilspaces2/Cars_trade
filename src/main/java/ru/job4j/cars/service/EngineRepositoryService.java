package ru.job4j.cars.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.persistence.EngineRepository;
import ru.job4j.cars.persistence.Store;

import java.util.List;

@ThreadSafe
@Service
public class EngineRepositoryService implements Store {

    private EngineRepository store;

    public EngineRepositoryService(EngineRepository store) {
        this.store = store;
    }

    public Engine add(Engine engine) {
        return store.add(engine);
    }

    public List<Engine> findAll() {
        return store.findAll();
    }

    public Engine findById(int id) {
        return store.findById(id);
    }
}
