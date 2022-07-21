package ru.job4j.cars.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.BrandCar;

import java.util.List;

@ThreadSafe
@Repository
public class BrandCarRepository implements Store {

    private SessionFactory sessionFactory;

    public BrandCarRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public BrandCar add(BrandCar brandCar) {
        return tx(session -> {
            session.save(brandCar);
            return brandCar;
        }, sessionFactory);
    }

    public List<BrandCar> findAll() {
        return tx(session -> session.createQuery("from BrandCar").list(), sessionFactory);
    }
}
