package models;

import utils.ConsoleUI;

import java.util.ArrayList;

public class User {
    private String name;
    private int age;
    private final int id;

    private final ArrayList<CarbonConsumption> carbonConsumption;

    // Constructor
    public User(String name, int age, int id) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.carbonConsumption = new ArrayList<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public ArrayList<CarbonConsumption> getCarbonConsumption() {
        return carbonConsumption;
    }

    // Add a Carbon consumption to the list
    public void addCarbonConsumption(CarbonConsumption consumption) {
        this.carbonConsumption.add(consumption);
    }

    // Show User infos
    @Override
    public String toString() {
        StringBuilder carbonConsumptionSTR = new StringBuilder();
        for(CarbonConsumption c: carbonConsumption){
            carbonConsumptionSTR.append("\t#").append(c.getId())
                                .append(": ").append(ConsoleUI.BLUE).append(c.getQuantity()).append(ConsoleUI.RESET)
                                .append(" from ").append(c.getStartDate())
                                .append("  to ").append(c.getEndDate())
                                .append("\n");
        }
        return "---------------------------------\n" +
                "id          : " + id + "\n" +
                "name        : " + name + "\n" +
                "age         : " + age + "\n" +
                "Consumption : " + ConsoleUI.YELLOW + getConsumptionTotal() + ConsoleUI.RESET + "\n" +
                carbonConsumptionSTR;
    }

    // Consumption Total
    public double getConsumptionTotal() {
        return carbonConsumption.stream()
                .mapToDouble(CarbonConsumption::getQuantity)
                .reduce(0, Double::sum);
    }
}