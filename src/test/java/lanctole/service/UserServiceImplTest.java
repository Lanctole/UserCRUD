package lanctole.service;

import lanctole.dao.UserDao;
import lanctole.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;
    private User user;
    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setName("Alex");
        user.setEmail("alex@example.com");
        user.setAge(25);
    }

    @Test
    void create_shouldReturnCreatedUser() {
        // Arrange
        when(userDao.create(any(User.class))).thenReturn(user);

        // Act
        User result = userService.create(user);

        // Assert
        assertEquals("Alex", result.getName());
        verify(userDao).create(user);
    }

    @Test
    void getById_shouldReturnUser() {
        // Arrange
        when(userDao.getById(1L)).thenReturn(user);

        // Act
        User result = userService.getById(1L);

        // Assert
        assertEquals("Alex", result.getName());
        verify(userDao).getById(1L);
    }

    @Test
    void getAll_shouldReturnUserList() {
        // Arrange
        when(userDao.getAll()).thenReturn(Collections.singletonList(user));

        // Act
        List<User> result = userService.getAll();

        // Assert
        assertEquals(1, result.size());
        verify(userDao).getAll();
    }

    @Test
    void update_shouldReturnUpdatedUser() {
        // Arrange
        user.setName("Uname");
        when(userDao.update(any(User.class))).thenReturn(user);

        // Act
        User result = userService.update(user);

        // Assert
        assertEquals("Uname", result.getName());
        verify(userDao).update(user);
    }

    @Test
    void delete_shouldCallDao() {
        // Arrange
        doNothing().when(userDao).delete(1L);

        // Act
        userService.delete(1L);

        // Assert
        verify(userDao).delete(1L);
    }

    @AfterEach
    void cleanup() throws Exception {
        closeable.close();
    }
}