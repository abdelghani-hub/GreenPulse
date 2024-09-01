import models.CarbonConsumption;
import models.User;
import services.UserService;
import utils.ConsoleUI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final UserService userService = new UserService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // De-Serialization

        boolean running = true;

        while (running) {
            ConsoleUI.displayMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createUser();
                    break;
                case "2":
                    updateUser();
                    break;
                case "3":
                    deleteUser();
                    break;
                case "4":
                    viewUser();
                    break;
                case "5":
                    userService.listAllUsers();
                    break;
                case "6":
                    addCarbonConsumption();
                    break;
                case "7":
                    running = false;
                    System.out.println("Exiting the application...");
                    break;
                default:
                    ConsoleUI.displayErrorMessage("Invalid choice. Please try again.");
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
            userService.updateUser(user);
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
        scanner.nextLine();
        User user = userService.getUser(id);

        if (user != null) {
            userService.showUser(user);
        } else {
            ConsoleUI.displayWarningMessage("User not found!");
        }
    }

    private static void addCarbonConsumption(){
        System.out.print("\nEnter the user id : ");
        int userID = scanner.nextInt();
        scanner.nextLine();
        User user = userService.getUser(userID);
        if(user != null){
            System.out.print("\tEnter the Quantity : ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            System.out.print("\tEnter the Start Date (dd/mm/YYYY) : ");
            LocalDate startDate = readLocalDate();

            System.out.print("\tEnter the End Date (dd/mm/YYYY) : ");
            LocalDate endDate = readLocalDate();

            // Create the new Carbon Consumption
            CarbonConsumption carbonConsumption = new CarbonConsumption(quantity, startDate, endDate);
            user.addCarbonConsumption(carbonConsumption);
            ConsoleUI.displaySuccessMessage("The Consumption has been added successfully.");
        }else{
            ConsoleUI.displayErrorMessage("User not found!");
        }
    }

    // Read Local Date
    private static LocalDate readLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = null;
        boolean valid = false;

        while (!valid) {
            String input = scanner.nextLine();

            try {
                date = LocalDate.parse(input, formatter);
                valid = true;
            } catch (DateTimeParseException e) {
                ConsoleUI.displayWarningMessage("Invalid date format. Please try again.");
            }
        }

        return date;
    }
}
