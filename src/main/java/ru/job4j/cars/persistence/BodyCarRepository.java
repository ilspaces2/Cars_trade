package ru.job4j.cars.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.BodyCar;

import java.util.List;

@ThreadSafe
@Repository
public class BodyCarRepository implements Store {

    private SessionFactory sessionFactory;

    public BodyCarRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public BodyCar add(BodyCar bodyCar) {
        return tx(session -> {
            session.save(bodyCar);
            return bodyCar;
        }, sessionFactory);
    }

    public BodyCar findById(int id) {
        return (BodyCar) tx(session -> session.createQuery("from BodyCar where id=:nId")
                .setParameter("nId", id).uniqueResult(), sessionFactory);
    }

    public List<BodyCar> findAll() {
        return tx(session -> session.createQuery("from BodyCar").list(), sessionFactory);
    }
}
