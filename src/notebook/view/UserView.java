package notebook.view;

import notebook.controller.UserController;
import notebook.model.User;
import notebook.util.Commands;

import java.util.List;
import java.util.Scanner;

public class UserView {
    private final UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;
    }

    public void run() {
        Commands com;
        System.out.println("*****************************");
        System.out.println("Приветствую Вас, я ваш ежедневник!!!\nНачнем!");
        System.out.println("*****************************");
        while (true) {
            String command = prompt("Введите команду(CREATE/READ/LIST/DELETE/EXIT): ");
            com = Commands.valueOf(command);
            if (com == Commands.EXIT) return;
            switch (com) {
                case CREATE:
                    User u = createUser();
                    userController.saveUser(u);
                    break;
                case READ:
                    String id = prompt("Введите ID заметки: ");
                    try {
                        User user = userController.readUser(Long.parseLong(id));
                        System.out.println(user);
                        System.out.println();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case LIST:
                    List<User> users = userController.getListsUsers();
                    System.out.println();
                    for (User user :
                            users) {
                        System.out.println(user);
                        System.out.println("*****************************");
                    }
                    break;
                case DELETE:
                    String userID = prompt("Введите ID заметки: ");
                    userController.deleteUser(userID);
                    break;
            }
        }
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

    private User createUser() {
        String firstName = prompt("Укажите дату заметки: ");
        String lastName = prompt("Укажите заголовок: ");
        String phone = prompt("Оставте комментарий к заметки: ");
        return new User(firstName, lastName, phone);
    }
}
