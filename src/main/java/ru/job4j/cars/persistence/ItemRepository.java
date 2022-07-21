package ru.job4j.cars.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Item;

import java.util.Date;
import java.util.List;

@ThreadSafe
@Repository
public class ItemRepository implements Store {
    private SessionFactory sessionFactory;

    public ItemRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Item add(Item item) {
        return tx(session -> {
            item.setCreated(new Date());
            session.save(item);
            return item;
        }, sessionFactory);
    }

    public List<Item> findAll() {
        return tx(session -> session.createQuery("from Item i "
                        + "join fetch i.user join fetch i.car c join fetch c.engine join fetch c.bodyCar "
                        + "join fetch c.nameCar nc join fetch nc.brandName where i.sale=false")
                .list(), sessionFactory);
    }

    public List<Item> findAddToday() {
        return tx(session -> session.createQuery("from Item i "
                        + "join fetch i.user join fetch i.car c join fetch c.engine join fetch c.bodyCar "
                        + "join fetch c.nameCar nc join fetch nc.brandName "
                        + "where i.created=current_date and i.sale=false")
                .list(), sessionFactory);
    }

    public List<Item> findWithPhoto() {
        return tx(session -> session.createQuery("from Item i "
                        + "join fetch i.user join fetch i.car c join fetch c.engine join fetch c.bodyCar "
                        + "join fetch c.nameCar nc join fetch nc.brandName "
                        + "where c.photoCar!=null and i.sale=false")
                .list(), sessionFactory);
    }

    public List<Item> findByCarName(final String carName) {
        return tx(session -> session.createQuery("from Item i "
                        + "join fetch i.user join fetch i.car c join fetch c.engine join fetch c.bodyCar "
                        + "join fetch c.nameCar nc join fetch nc.brandName bn "
                        + "where bn.brandName=:name and i.sale=false")
                .setParameter("name", carName)
                .list(), sessionFactory);
    }

    public List<Item> findItemsByUserId(final int id) {
        return tx(session -> session.createQuery("from Item i "
                        + "join fetch i.user u join fetch i.car c join fetch c.engine join fetch c.bodyCar "
                        + "join fetch c.nameCar nc join fetch nc.brandName where u.id=:nId")
                .setParameter("nId", id)
                .list(), sessionFactory);
    }

    public Item findItemById(final int id) {
        return (Item) tx(session -> session.createQuery("from Item i "
                        + "join fetch i.user u join fetch i.car c join fetch c.engine join fetch c.bodyCar "
                        + "join fetch c.nameCar nc join fetch nc.brandName where i.id=:nId")
                .setParameter("nId", id)
                .uniqueResult(), sessionFactory);
    }

    public boolean sellItem(final int id) {
        return tx(session -> session.createQuery("update Item  set sale=:nSale, closed=:nClosed where id=:nId")
                .setParameter("nSale", true)
                .setParameter("nClosed", new Date())
                .setParameter("nId", id)
                .executeUpdate() > 0, sessionFactory);
    }

    public List<Item> findArchiveItems() {
        return tx(session -> session.createQuery("from Item i "
                        + "join fetch i.user join fetch i.car c join fetch c.engine join fetch c.bodyCar "
                        + "join fetch c.nameCar nc join fetch nc.brandName where i.sale=true")
                .list(), sessionFactory);
    }

    public boolean update(Item item) {
        return tx(session -> session.createQuery("update Item set description=:nDesc where id=:nId")
                .setParameter("nDesc", item.getDescription())
                .setParameter("nId", item.getId())
                .executeUpdate() > 0, sessionFactory);
    }
}
