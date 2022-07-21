package ru.job4j.cars.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;

@ThreadSafe
@Repository
public class EngineRepository implements Store {

    private SessionFactory sessionFactory;

    public EngineRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Engine add(Engine engine) {
        return tx(session -> {
            session.save(engine);
            return engine;
        }, sessionFactory);
    }

    public Engine findById(int id) {
        return (Engine) tx(session -> session.createQuery("from Engine where id=:nId")
                .setParameter("nId", id).uniqueResult(), sessionFactory);
    }

    public List<Engine> findAll() {
        return tx(session -> session.createQuery("from Engine").list(), sessionFactory);
    }
}
