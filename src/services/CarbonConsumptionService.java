package services;

import models.CarbonConsumption;
import models.User;
import utils.ConsoleUI;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
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
        LocalDate startDate = ConsoleUI.readLocalDate();

        System.out.print("\tEnter the End Date (dd/mm/YYYY) : ");
        LocalDate endDate = ConsoleUI.readLocalDate();

        // Create the new Carbon Consumption
        CarbonConsumption carbonConsumption = new CarbonConsumption(quantity, startDate, endDate);
        user.addCarbonConsumption(carbonConsumption);
        ConsoleUI.displaySuccessMessage("The Consumption has been added successfully.");
    }

    public static void generateDailyReport(User user) {
        ArrayList<CarbonConsumption> cc = user.getCarbonConsumption();
        cc.sort(Comparator.comparing(CarbonConsumption::getStartDate));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        System.out.println(ConsoleUI.YELLOW + "\n #" + user.getId() + ConsoleUI.RESET + " " + user.getName() + ", " + user.getAge() + " yo");
        for (CarbonConsumption item : cc) {
            double dayAVG = calculateDailyAverage(item);
            TreeMap<LocalDate, Double> dailyConsumptionMap = getDailyConsumptionMap(item.getStartDate(), item.getEndDate(), dayAVG);

            for (HashMap.Entry<LocalDate, Double> entry : dailyConsumptionMap.entrySet()) { // use entry for ASC order
                System.out.println("\t" + entry.getKey().format(formatter) + " : " + String.format("%.2f", entry.getValue()));
            }
        }
    }

    public static void generateWeeklyReport(User user) {
        ArrayList<CarbonConsumption> cc = user.getCarbonConsumption();
        cc.sort(Comparator.comparing(CarbonConsumption::getStartDate));
        DateTimeFormatter formFormatter = DateTimeFormatter.ofPattern("d MMM");
        DateTimeFormatter toFormatter = DateTimeFormatter.ofPattern("d MMM yyyy");

        System.out.println(ConsoleUI.YELLOW + "\n #" + user.getId() + ConsoleUI.RESET + " " + user.getName() + ", " + user.getAge() + " yo");
        TreeMap<LocalDate, Double> weeklyConsumptionMap = new TreeMap<>();

        for (CarbonConsumption item : cc) {
            double dayAVG = calculateDailyAverage(item);
            TreeMap<LocalDate, Double> dailyConsumptionMap = getDailyConsumptionMap(item.getStartDate(), item.getEndDate(), dayAVG);

            for (HashMap.Entry<LocalDate, Double> entry : dailyConsumptionMap.entrySet()) {
                LocalDate currentDay = entry.getKey();
                // Find the Monday of the current week
                LocalDate weekStart = currentDay.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

                // Weekly total
                weeklyConsumptionMap.put(weekStart,
                        weeklyConsumptionMap.getOrDefault(weekStart, 0.0) + entry.getValue());
            }
        }
        // Print the report
        for (HashMap.Entry<LocalDate, Double> entry : weeklyConsumptionMap.entrySet()) {
            LocalDate weekEnd = entry.getKey().plusDays(6);
            System.out.println(
                "\t" + entry.getKey().format(formFormatter) + " to " + weekEnd.format(toFormatter) + " : "
                + String.format("%.2f", entry.getValue())
            );
        }
    }

    public static void generateMonthlyReport(User user) {
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
}
