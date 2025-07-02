package lanctole.ui;

import lanctole.model.User;
import lanctole.service.UserService;
import lanctole.util.SessionFactoryProvider;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class ConsoleUI {
    private static final String ERROR_PREFIX = "Error: ";
    private static final String USER_NOT_FOUND_MESSAGE = "User not found!";
    private final UserService userService;
    private final Scanner scanner;
    private boolean running;

    public ConsoleUI(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
        this.running = true;
    }

    public void start() {
        while (running) {
            printMenu();
            handleUserChoice();
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\nUser Management System");
        System.out.println("1. Create User");
        System.out.println("2. Read User");
        System.out.println("3. Update User");
        System.out.println("4. Delete User");
        System.out.println("5. List All Users");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private void handleUserChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createUser();
                case 2 -> readUser();
                case 3 -> updateUser();
                case 4 -> deleteUser();
                case 5 -> listUsers();
                case 6 -> exit();
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + e.getMessage());
            scanner.nextLine();
        }
    }

    private void createUser() {
        executeUserOperation("Create User", userInput -> {
            User user = new User();
            user.setName(userInput.get("name"));
            user.setEmail(userInput.get("email"));
            user.setAge(Integer.parseInt(userInput.get("age")));
            user.setCreatedAt(LocalDateTime.now());

            User createdUser = userService.create(user);
            System.out.println("User created successfully! ID: " + createdUser.getId());
        });
    }

    private void readUser() {
        executeUserOperation("Read User", userInput -> {
            long id = Long.parseLong(userInput.get("id"));
            User user = userService.getById(id);

            if (user != null) {
                printUserDetails(user);
            } else {
                System.out.println(USER_NOT_FOUND_MESSAGE);
            }
        });
    }

    private void updateUser() {
        executeUserOperation("Update User", userInput -> {
            long id = Long.parseLong(userInput.get("id"));
            User user = userService.getById(id);

            if (user != null) {
                updateFieldIfNotEmpty(userInput, "name", user::setName);
                updateFieldIfNotEmpty(userInput, "email", user::setEmail);
                updateFieldIfNotEmpty(userInput, "age", value -> user.setAge(Integer.parseInt(value)));

                userService.update(user);
                System.out.println("User updated successfully!");
                printUserDetails(user);
            } else {
                System.out.println(USER_NOT_FOUND_MESSAGE);
            }
        });
    }

    private void deleteUser() {
        executeUserOperation("Delete User", userInput -> {
            long id = Long.parseLong(userInput.get("id"));
            User user = userService.getById(id);
            if (user != null) {
                userService.delete(id);
                System.out.println("User deleted successfully!");
            } else {
                System.out.println(USER_NOT_FOUND_MESSAGE);
            }

        });
    }

    private void listUsers() {
        try {
            System.out.println("\nAll Users:");
            List<User> users = userService.getAll();
            users.forEach(this::printUserDetails);
        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + e.getMessage());
        }
    }

    private void exit() {
        running = false;
        SessionFactoryProvider.shutdown();
        System.out.println("Goodbye!");
    }

    private void executeUserOperation(String operationName, Consumer<UserInput> operation) {
        try {
            System.out.println("\n" + operationName);
            UserInput userInput = new UserInput(scanner);
            operation.accept(userInput);
        } catch (Exception e) {
            System.out.println(ERROR_PREFIX + e.getMessage());
        }
    }

    private void printUserDetails(User user) {
        System.out.println("\nUser details:");
        System.out.println("ID: " + user.getId());
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Age: " + user.getAge());
        System.out.println("Created at: " + user.getCreatedAt());
    }

    private void updateFieldIfNotEmpty(UserInput input, String fieldName, Consumer<String> setter) {
        String value = input.get(fieldName);
        if (!value.trim().isEmpty()) {
            setter.accept(value);
        }
    }

    private record UserInput(Scanner scanner) {

        public String get(String fieldName) {
            System.out.printf("Enter %s: ", fieldName);
            return scanner.nextLine();
        }
    }
}