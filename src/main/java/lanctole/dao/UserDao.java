package lanctole.dao;

import lanctole.model.User;

import java.util.List;

public interface UserDao {
    User create(User user);
    User getById(long id);
    List<User> getAll();
    User update(User user);
    void delete(long id);
}
