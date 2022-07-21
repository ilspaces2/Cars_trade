package ru.job4j.cars.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.BrandCar;
import ru.job4j.cars.persistence.BrandCarRepository;

import java.util.List;

@ThreadSafe
@Service
public class BrandCarRepositoryService {

    private BrandCarRepository store;

    public BrandCarRepositoryService(BrandCarRepository store) {
        this.store = store;
    }

    public BrandCar add(BrandCar brandCar) {
        return store.add(brandCar);
    }

    public List<BrandCar> findAll() {
        return store.findAll();
    }
}
