package lanctole.dao;

import lanctole.exception.DaoException;
import lanctole.model.User;
import lanctole.util.SessionFactoryProvider;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@Slf4j
public class UserDaoImpl implements UserDao {
    @Override
    public User create(User user) {
        Transaction transaction = null;
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            log.debug("User created: {}", user.getId());
            return user;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error creating user", e);
            throw new DaoException("Error creating user", e);
        }
    }

    @Override
    public User getById(long id) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            return session.find(User.class, id);
        } catch (Exception e) {
            log.error("Error getting user by id: {}", id, e);
            throw new DaoException("Error getting user by id", e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            log.error("Error getting all users", e);
            throw new DaoException("Error getting all users", e);
        }
    }

    @Override
    public User update(User user) {
        Transaction transaction = null;
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            log.debug("User updated: {}", user.getId());
            return user;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error updating user", e);
            throw new DaoException("Error updating user", e);
        }
    }

    @Override
    public void delete(long id) {
        Transaction transaction = null;
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.find(User.class, id);
            if (user != null) {
                session.remove(user);
                log.debug("User deleted: {}", id);
            } else {
                log.warn("User not found for deletion: {}", id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error deleting user", e);
            throw new DaoException("Error deleting user", e);
        }
    }
}
