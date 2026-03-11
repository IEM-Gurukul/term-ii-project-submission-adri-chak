package finance.model;

import java.time.LocalDate;

// INHERITANCE - Income extends Transaction
// Income IS-A Transaction
public class Income extends Transaction {

    // Constructor - calls parent constructor using super()
    public Income(String description, double amount, LocalDate date, String category) {
        super(description, amount, date, category);
    }

    // POLYMORPHISM - overriding the abstract method from Transaction
    // This is our own implementation of getType()
    @Override
    public String getType() {
        return "INCOME";
    }
}