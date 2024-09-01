package models;

import utils.ConsoleUI;

import java.time.LocalDate;
import java.util.ArrayList;

public class User {
    private String name;
    private int age;
    private int id;

    private ArrayList<CarbonConsumption> carbonConsumption;

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

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<CarbonConsumption> getCarbonConsumption() {
        return carbonConsumption;
    }

    public void setCarbonConsumption(ArrayList<CarbonConsumption> carbonConsumption) {
        this.carbonConsumption = carbonConsumption;
    }

    // Add a Carbon consumption to the list
    public void addCarbonConsumption(CarbonConsumption consumption) {
        this.carbonConsumption.add(consumption);
    }

    // Update a carbon consumption in the list
    public void updateCarbonConsumption(int id, int newQuantity, LocalDate newStartDate, LocalDate newEndDate) {
        for (CarbonConsumption consumption : this.carbonConsumption) {
            if (consumption.getId() == id) {
                consumption.setQuantity(newQuantity);
                consumption.setStartDate(newStartDate);
                consumption.setEndDate(newEndDate);
                break;
            }
        }
    }

    // Show User infos
    @Override
    public String toString() {
        StringBuilder carbonConsumptionSTR = new StringBuilder();
        for(CarbonConsumption c: carbonConsumption){
            carbonConsumptionSTR.append("\t#").append(c.getId())
                                .append(": ").append(ConsoleUI.YELLOW).append(c.getQuantity()).append(ConsoleUI.RESET)
                                .append(" from ").append(c.getStartDate())
                                .append("  to ").append(c.getEndDate())
                                .append("\n");
        }
        return "---------------------------------\n" +
                "id          : " + id + "\n" +
                "name        : " + name + "\n" +
                "age         : " + age + "\n" +
                "Consumption : \n" +
                carbonConsumptionSTR;
    }
}
