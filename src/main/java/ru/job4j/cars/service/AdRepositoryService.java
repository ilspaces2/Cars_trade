package ru.job4j.cars.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Item;
import ru.job4j.cars.persistence.AdRepository;

import java.util.List;

@ThreadSafe
@Service
public class AdRepositoryService {

    private AdRepository store;

    public AdRepositoryService(AdRepository store) {
        this.store = store;
    }

    public List<Item> findAll() {
        return store.findAll();
    }

    public List<Item> findWithPhoto() {
        return store.findWithPhoto();
    }

    public List<Item> findAddToday() {
        return store.findAddToday();
    }

    public List<Item> findByCarName(String carName) {
        return store.findByCarName(carName);
    }
}
