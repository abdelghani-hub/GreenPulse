package services;

import models.CarbonConsumption;
import models.User;
import utils.ConsoleUI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;

public class CarbonConsumptionService {

    public static void addCarbonConsumption(User user) {
        System.out.print("\tEnter the Quantity : ");
        int quantity = ConsoleUI.scanner.nextInt();
        ConsoleUI.scanner.nextLine();

        System.out.print("\tEnter the Start Date (dd/mm/YYYY) : ");
        LocalDate startDate = readLocalDate();

        System.out.print("\tEnter the End Date (dd/mm/YYYY) : ");
        LocalDate endDate = readLocalDate();

        // Create the new Carbon Consumption
        CarbonConsumption carbonConsumption = new CarbonConsumption(quantity, startDate, endDate);
        user.addCarbonConsumption(carbonConsumption);
        ConsoleUI.displaySuccessMessage("The Consumption has been added successfully.");
    }

    public static void generateDailyReport(User user) {
        ArrayList<CarbonConsumption> cc = user.getCarbonConsumption();
        cc.sort(Comparator.comparing(CarbonConsumption::getStartDate));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println(ConsoleUI.YELLOW + "\n #" + user.getId() + ConsoleUI.RESET + " " + user.getName() + ", " + user.getAge() + " yo");
        for (CarbonConsumption item : cc) {
            double dayAVG = calculateDailyAverage(item);
            TreeMap<LocalDate, Double> dailyConsumptionMap = getDailyConsumptionMap(item.getStartDate(), item.getEndDate(), dayAVG);

            for (HashMap.Entry<LocalDate, Double> entry : dailyConsumptionMap.entrySet()) {
                System.out.println("\t" + entry.getKey().format(formatter) + " : " + String.format("%.2f", entry.getValue()));
            }
        }
    }

    public static void generateMonthlyReport(User user) {
        // TODO
    }

    public static void generateYearlyReport(User user) {
        // TODO
    }

    // Average carbon consumption per day
    public static double calculateDailyAverage(CarbonConsumption cc) {
        long days = ChronoUnit.DAYS.between(cc.getStartDate(), cc.getEndDate()) + 1;
        return (double) cc.getQuantity() / days;
    }

    // Map for each day
    public static TreeMap<LocalDate, Double> getDailyConsumptionMap(LocalDate startDate, LocalDate endDate, double dailyAverage) {
        TreeMap<LocalDate, Double> dailyConsumptionMap = new TreeMap<>();
        LocalDate currentDay = startDate;

        while (!currentDay.isAfter(endDate)) {
            dailyConsumptionMap.put(currentDay, dailyAverage);
            currentDay = currentDay.plusDays(1);
        }
        return dailyConsumptionMap;
    }

    // Read Local Date
    private static LocalDate readLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = null;
        boolean valid = false;

        while (!valid) {
            String input = ConsoleUI.scanner.nextLine();
            try {
                date = LocalDate.parse(input, formatter);
                valid = true;
            } catch (DateTimeParseException e) {
                ConsoleUI.displayWarningMessage("Invalid format. Please try again ( dd/mm/YYYY ) : ");
            }
        }
        return date;
    }
}
