package ru.job4j.cars.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

@ThreadSafe
@Repository
public class CarRepository implements Store {
    private SessionFactory sessionFactory;

    public CarRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Car add(Car car) {
        return tx(session -> {
            session.save(car);
            return car;
        }, sessionFactory);
    }

    public Car findPhotoById(final int id) {
        return (Car) tx(session -> session.createQuery("from Car where id=:nId")
                .setParameter("nId", id).uniqueResult(), sessionFactory);
    }

    public Car findById(final int id) {
        return (Car) tx(session -> session.createQuery("from Car c join fetch c.engine "
                        + "join fetch c.nameCar nc join fetch nc.brandName where c.id=:nId")
                .setParameter("nId", id).uniqueResult(), sessionFactory);
    }

    public boolean update(Car car) {
        return tx(session -> session.createQuery("update Car set photoCar=:nPhoto,"
                        + "nameCar.id=:nNameCar, bodyCar.id=:nBodyCar , engine.id=:nEngine where id=:nId")
                .setParameter("nPhoto", car.getPhotoCar())
                .setParameter("nNameCar", car.getNameCar().getId())
                .setParameter("nBodyCar", car.getBodyCar().getId())
                .setParameter("nEngine", car.getEngine().getId())
                .setParameter("nId", car.getId())
                .executeUpdate() > 0, sessionFactory);
    }
}
