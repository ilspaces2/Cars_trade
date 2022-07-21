package ru.job4j.cars.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.Optional;

@ThreadSafe
@Repository
public class UserRepository implements Store {
    private final SessionFactory sessionFactory;

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<User> add(User user) {
        try {
            return tx(session -> {
                session.save(user);
                return Optional.of(user);
            }, sessionFactory);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<User> findById(int id) {
        return tx(session -> session.createQuery("from User where id=:nId")
                .setParameter("nId", id)
                .uniqueResultOptional(), sessionFactory);
    }

    public boolean deleteAll() {
        return tx(session -> session.createQuery("delete from User")
                .executeUpdate() > 0, sessionFactory);
    }

    public Optional<User> findUserByEmailAndPass(String email, String pass) {
        return tx(session -> session.createQuery("from User where email=:nEmail and pass=:nPass")
                .setParameter("nPass", pass)
                .setParameter("nEmail", email)
                .uniqueResultOptional(), sessionFactory);
    }
}
