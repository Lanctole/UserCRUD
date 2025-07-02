package lanctole.service;

import lanctole.dao.UserDao;
import lanctole.exception.UserServiceException;
import lanctole.model.User;
import lanctole.validation.UserValidator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User create(User user) {
        UserValidator.validate(user);
        try {
            User created = userDao.create(user);
            log.debug("Created user with ID {}", created.getId());
            return created;
        } catch (Exception e) {
            log.error("Failed to create user", e);
            throw new UserServiceException("Unable to create user", e);
        }
    }

    @Override
    public User getById(long id) {
        try {
            return userDao.getById(id);
        } catch (Exception e) {
            log.error("Failed to retrieve user by ID {}", id, e);
            throw new UserServiceException("Unable to get user by ID", e);
        }
    }

    @Override
    public List<User> getAll() {
        try {
            return userDao.getAll();
        } catch (Exception e) {
            log.error("Failed to retrieve all users", e);
            throw new UserServiceException("Unable to get all users", e);
        }
    }

    @Override
    public User update(User user) {
        UserValidator.validate(user);
        try {
            User updated = userDao.update(user);
            log.debug("Updated user with ID {}", updated.getId());
            return updated;
        } catch (Exception e) {
            log.error("Failed to update user", e);
            throw new UserServiceException("Unable to update user", e);
        }
    }

    @Override
    public void delete(long id) {
        try {
            userDao.delete(id);
            log.debug("Deleted user with ID {}", id);
        } catch (Exception e) {
            log.error("Failed to delete user with ID {}", id, e);
            throw new UserServiceException("Unable to delete user", e);
        }
    }
}
