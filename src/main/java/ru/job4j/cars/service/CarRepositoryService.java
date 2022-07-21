package ru.job4j.cars.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.persistence.CarRepository;
import ru.job4j.cars.persistence.Store;

@ThreadSafe
@Service
public class CarRepositoryService implements Store {
    private CarRepository store;

    public CarRepositoryService(CarRepository store) {
        this.store = store;
    }

    public Car add(Car car) {
        return store.add(car);
    }

    public Car findPhotoById(int id) {
        return store.findPhotoById(id);
    }

    public Car findById(int id) {
        return store.findById(id);
    }

    public boolean update(Car car) {
        return store.update(car);
    }
}
