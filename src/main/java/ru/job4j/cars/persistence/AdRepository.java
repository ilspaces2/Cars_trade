package ru.job4j.cars.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Item;

import java.util.List;

@ThreadSafe
@Repository
public class AdRepository implements Store {
    private SessionFactory sessionFactory;

    public AdRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Item> findAll() {
        return tx(session -> session.createQuery("from Item i "
                        + "join fetch i.user join fetch i.car")
                .list(), sessionFactory);
    }

    public List<Item> findAddToday() {
        return tx(session -> session.createQuery("from Item i "
                        + "join fetch i.user join fetch i.car c "
                        + "where i.created=current_date")
                .list(), sessionFactory);
    }

    public List<Item> findWithPhoto() {
        return tx(session -> session.createQuery("from Item i "
                        + "join fetch i.user join fetch i.car c "
                        + "where c.photoCar!=null")
                .list(), sessionFactory);
    }

    public List<Item> findByCarName(final String carName) {
        return tx(session -> session.createQuery("from Item i "
                        + "join fetch i.user join fetch i.car c "
                        + "where c.carName=:name")
                .setParameter("name", carName)
                .list(), sessionFactory);
    }
}
