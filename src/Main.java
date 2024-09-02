import models.User;
import services.CarbonConsumptionService;
import services.UserService;
import utils.ConsoleUI;


public class Main {
    private static final UserService userService = new UserService();

    public static void main(String[] args) {

        // De-Serialization

        boolean running = true;

        while (running) {
            ConsoleUI.displayMenu();
            String choice = ConsoleUI.scanner.nextLine();

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
                    generateCarbonReport();
                    break;
                case "8":
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
        String name = ConsoleUI.scanner.nextLine();
        System.out.print("Enter age  : ");
        int age = ConsoleUI.scanner.nextInt();
        ConsoleUI.scanner.nextLine();
        System.out.print("Enter id   : ");
        int id = ConsoleUI.scanner.nextInt();
        ConsoleUI.scanner.nextLine();

        User user = new User(name, age, id);
        userService.addUser(user);
    }

    private static void updateUser() {
        System.out.print("\nEnter the unique ID of the user to update : ");
        int id = ConsoleUI.scanner.nextInt();
        ConsoleUI.scanner.nextLine();
        User user = userService.getUser(id);

        if (user != null) {
            userService.updateUser(user);
        } else {
            ConsoleUI.displayWarningMessage("User not found.");
        }
    }

    private static void deleteUser() {
        System.out.print("Enter the unique ID of the user to delete : ");
        int id = ConsoleUI.scanner.nextInt();
        ConsoleUI.scanner.nextLine();
        if (userService.getUser(id) != null) {
            System.out.print("Are you sur you want to delete this user (Y/N) : ");
            String conf = ConsoleUI.scanner.nextLine();
            if (conf.equalsIgnoreCase("y")) {
                boolean isDeleted = userService.deleteUser(id);
                if (isDeleted) ConsoleUI.displaySuccessMessage("User deleted successfully.");
                else ConsoleUI.displayErrorMessage("Server Error!");
            } else {
                ConsoleUI.displaySuccessMessage("Operation has been canceled successfully.");
            }
        } else {
            ConsoleUI.displayWarningMessage("User not found!");
        }
    }

    private static void viewUser() {
        System.out.print("\nEnter the unique ID of the user to view : ");
        int id = ConsoleUI.scanner.nextInt();
        ConsoleUI.scanner.nextLine();
        User user = userService.getUser(id);

        if (user != null) {
            userService.showUser(user);
        } else {
            ConsoleUI.displayWarningMessage("User not found!");
        }
    }

    // Add Carbon Consumption
    public static void addCarbonConsumption() {
        System.out.print("\nEnter the user id : ");
        int userID = ConsoleUI.scanner.nextInt();
        ConsoleUI.scanner.nextLine();
        User user = userService.getUser(userID);

        if (user != null) {
            CarbonConsumptionService.addCarbonConsumption(user);
        } else {
            ConsoleUI.displayErrorMessage("User not found!");
        }
    }

    // Generate Carbon Consumption Report
    public static void generateCarbonReport() {
        System.out.print("Enter the user id : ");
        int userID = ConsoleUI.scanner.nextInt();
        ConsoleUI.scanner.nextLine();
        User user = userService.getUser(userID);
        if (user != null) {
            ConsoleUI.displayReportMenu();
            String reportChoice = ConsoleUI.scanner.nextLine();
            switch (reportChoice) {
                case "1":
                    CarbonConsumptionService.generateDailyReport(user);
                    break;
                case "2":
                    CarbonConsumptionService.generateWeeklyReport(user);
                    break;
                case "3":
                    CarbonConsumptionService.generateMonthlyReport(user);
                    break;
                default:
                    ConsoleUI.displayWarningMessage("Invalid choice. Please try again.");
            }
        } else {
            ConsoleUI.displayErrorMessage("User not found!");
        }
    }
}
