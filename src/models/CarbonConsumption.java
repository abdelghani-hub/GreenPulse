package models;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class CarbonConsumption {
    private final int id;
    private int quantity;
    private LocalDate startDate;
    private LocalDate endDate;
    private static final AtomicInteger idCounter = new AtomicInteger();

    private static int generateUniqueID() {
        return idCounter.incrementAndGet();
    }

    public CarbonConsumption(int quantity, LocalDate startDate, LocalDate endDate) {
        this.id = generateUniqueID();
        this.quantity = quantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    // Setters
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
