package lanctole;

import lanctole.dao.UserDao;
import lanctole.dao.UserDaoImpl;
import lanctole.service.UserService;
import lanctole.service.UserServiceImpl;
import lanctole.ui.ConsoleUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();
        UserService userService = new UserServiceImpl(userDao);
        Scanner scanner = new Scanner(System.in);
        ConsoleUI consoleUI = new ConsoleUI(userService, scanner);

        consoleUI.start();
    }
}