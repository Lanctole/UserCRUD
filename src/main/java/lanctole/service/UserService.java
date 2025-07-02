package lanctole.service;

import lanctole.model.User;

import java.util.List;

public interface UserService {
    User create(User user);

    User getById(long id);

    List<User> getAll();

    User update(User user);

    void delete(long id);
}
