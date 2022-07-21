package ru.job4j.cars.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.NameCar;

import java.util.List;

@ThreadSafe
@Repository
public class NameCarRepository implements Store {

    private SessionFactory sessionFactory;

    public NameCarRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<NameCar> findAll() {
        return tx(session -> session.createQuery("from NameCar n join fetch n.brandName ")
                .list(), sessionFactory);
    }

    public NameCar findById(int id) {
        return (NameCar) tx(session -> session.createQuery(
                        "from NameCar n join fetch n.brandName where n.id=:nId")
                .setParameter("nId", id)
                .uniqueResult(), sessionFactory);
    }
}
