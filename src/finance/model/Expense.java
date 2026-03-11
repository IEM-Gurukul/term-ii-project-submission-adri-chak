package finance.model;

import java.time.LocalDate;

// INHERITANCE - Expense extends Transaction
// Expense IS-A Transaction
public class Expense extends Transaction {

    // Constructor - calls parent constructor using super()
    public Expense(String description, double amount, LocalDate date, String category) {
        super(description, amount, date, category);
    }

    // POLYMORPHISM - overriding the abstract method from Transaction
    // Same method name as Income but different behaviour
    @Override
    public String getType() {
        return "EXPENSE";
    }
}