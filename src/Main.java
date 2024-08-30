import models.User;
import services.UserService;
import utils.ConsoleUI;

import java.util.Scanner;

public class Main {
    private static final UserService userService = new UserService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // De-Serialization

        boolean running = true;

        while (running) {
            ConsoleUI.displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    updateUser();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    viewUser();
                    break;
                case 5:
                    viewAllUsers();
                    break;
                case 6:
                    running = false;
                    System.out.println("Exiting the application...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createUser() {
        System.out.print("\nEnter name : ");
        String name = scanner.nextLine();
        System.out.print("Enter age  : ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter id   : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        User user = new User(name, age, id);
        userService.addUser(user);
    }

    private static void updateUser() {
        System.out.print("\nEnter the unique ID of the user to update : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        User user = userService.getUser(id);

        if (user != null) {
            System.out.print("Enter new name (leave blank to keep current) : ");
            String name = scanner.nextLine();
            if (!name.isEmpty()) {
                user.setName(name);
            }

            System.out.print("Enter new age (leave blank to keep current) : ");
            String ageInput = scanner.nextLine();
            if (!ageInput.isEmpty()) {
                int age = Integer.parseInt(ageInput);
                user.setAge(age);
            }

            ConsoleUI.displaySuccessMessage("User updated successfully!");
        } else {
            ConsoleUI.displayWarningMessage("User not found.");
        }
    }

    private static void deleteUser() {
        System.out.print("Enter the unique ID of the user to delete : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        if (userService.getUser(id) != null) {
            System.out.print("Are you sur you want to delete this user (Y/N) : ");
            String conf = scanner.nextLine();
            if (conf.equalsIgnoreCase("y")) {
                boolean isDeleted = userService.deleteUser(id);
                if (isDeleted) ConsoleUI.displaySuccessMessage("User deleted successfully.");
                else ConsoleUI.displayErrorMessage("Server Error!");
            }else{
                ConsoleUI.displaySuccessMessage("Operation has been canceled successfully.");
            }
        }else {
            ConsoleUI.displayWarningMessage("User not found!");
        }
    }

    private static void viewUser() {
        System.out.print("\nEnter the unique ID of the user to view : ");
        int id = scanner.nextInt();
        User user = userService.getUser(id);

        if (user != null) {
            System.out.println(user);
        } else {
            ConsoleUI.displayWarningMessage("User not found!");
        }
    }

    private static void viewAllUsers() {
        userService.listAllUsers();
    }
}
