package finance.model;

import java.time.LocalDate;

// Abstract class - cannot be created directly
// This is ABSTRACTION - we hide the details of what type of transaction it is
public abstract class Transaction {

    // ENCAPSULATION - all fields are private
    private String description;
    private double amount;
    private LocalDate date;
    private String category;

    // Constructor
    public Transaction(String description, double amount, LocalDate date, String category) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    // Abstract method - every subclass MUST implement this
    // This is ABSTRACTION - we define WHAT to do, not HOW
    public abstract String getType();

    // Getters - ENCAPSULATION
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }
    public String getCategory() { return category; }

    // Setters - ENCAPSULATION
    public void setDescription(String description) { this.description = description; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setCategory(String category) { this.category = category; }

    // toString for displaying transaction details
    @Override
    public String toString() {
        return getType() + " | " + date + " | " + category + " | " + description + " | Rs." + amount;
    }
}