package finance.model;

// Budget class - stores the spending limit for a category
// This is a regular class demonstrating ENCAPSULATION
public class Budget {

    // ENCAPSULATION - private fields
    private String category;
    private double limit;

    // Constructor
    public Budget(String category, double limit) {
        this.category = category;
        this.limit = limit;
    }

    // Getters
    public String getCategory() { return category; }
    public double getLimit() { return limit; }

    // Setters
    public void setLimit(double limit) { this.limit = limit; }

    @Override
    public String toString() {
        return "Budget | " + category + " | Limit: Rs." + limit;
    }
}