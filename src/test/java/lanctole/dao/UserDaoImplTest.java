package lanctole.dao;

import lanctole.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDaoImplTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    private UserDaoImpl userDao;

    @BeforeAll
    void setup() {
        postgres.start();

        System.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgres.getUsername());
        System.setProperty("hibernate.connection.password", postgres.getPassword());
        System.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        userDao = new UserDaoImpl();
    }

    @AfterAll
    void teardown() {
        postgres.stop();
    }

    private User buildTestUser() {
        User user = new User();
        user.setName("Ulya");
        user.setEmail("ulya_" + UUID.randomUUID() + "@example.com");
        user.setAge(30);
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    @Test
    void createAndGetById_shouldWorkCorrectly() {
        // Arrange
        User user = buildTestUser();

        // Act
        User created = userDao.create(user);
        User fetched = userDao.getById(created.getId());

        // Assert
        assertEquals(user.getName(), fetched.getName());
    }

    @Test
    void getAll_shouldReturnNonEmptyList() {
        // Arrange
        userDao.create(buildTestUser());

        // Act
        List<User> users = userDao.getAll();

        // Assert
        assertFalse(users.isEmpty());
    }

    @Test
    void update_shouldChangeFields() {
        // Arrange
        User user = userDao.create(buildTestUser());
        user.setName("Upname");

        // Act
        User updated = userDao.update(user);

        // Assert
        assertEquals("Upname", updated.getName());
    }

    @Test
    void delete_shouldRemoveUser() {
        // Arrange
        User user = userDao.create(buildTestUser());

        // Act
        userDao.delete(user.getId());

        // Assert
        assertNull(userDao.getById(user.getId()));
    }
}