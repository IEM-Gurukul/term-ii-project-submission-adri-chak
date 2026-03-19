package finance.service;

import finance.model.Transaction;
import finance.model.Income;
import finance.model.Expense;
import finance.exception.InvalidAmountException;
import finance.exception.InvalidDateException;
import finance.persistence.FileHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// This class manages all transaction operations
// ENCAPSULATION - all business logic is hidden inside this class
public class TransactionManager {

    // COLLECTIONS - ArrayList to store all transactions
    private List<Transaction> transactions;

    // Constructor - loads existing transactions from file on startup
    public TransactionManager() {
        transactions = FileHandler.loadTransactions();
    }

    // Add a new Income transaction
    // EXCEPTION HANDLING - throws custom exceptions for bad input
    public void addIncome(String description, double amount, LocalDate date, String category)
            throws InvalidAmountException, InvalidDateException {

        validateAmount(amount);
        validateDate(date);

        // POLYMORPHISM - adding an Income object to a List<Transaction>
        transactions.add(new Income(description, amount, date, category));
        FileHandler.saveTransactions(transactions);
    }

    // Add a new Expense transaction
    public void addExpense(String description, double amount, LocalDate date, String category)
            throws InvalidAmountException, InvalidDateException {

        validateAmount(amount);
        validateDate(date);

        // POLYMORPHISM - adding an Expense object to a List<Transaction>
        transactions.add(new Expense(description, amount, date, category));
        FileHandler.saveTransactions(transactions);
    }

    // Delete a transaction by index
    public void deleteTransaction(int index) {
        if (index >= 0 && index < transactions.size()) {
            transactions.remove(index);
            FileHandler.saveTransactions(transactions);
        }
    }

    // Get all transactions
    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    // Get transactions filtered by category
    public List<Transaction> getByCategory(String category) {
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getCategory().equalsIgnoreCase(category)) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    // Calculate total income
    public double getTotalIncome() {
        double total = 0;
        for (Transaction t : transactions) {
            // POLYMORPHISM - getType() resolves correctly at runtime
            if (t.getType().equals("INCOME")) {
                total += t.getAmount();
            }
        }
        return total;
    }

    // Calculate total expenses
    public double getTotalExpenses() {
        double total = 0;
        for (Transaction t : transactions) {
            if (t.getType().equals("EXPENSE")) {
                total += t.getAmount();
            }
        }
        return total;
    }

    // Get spending per category using HashMap
    // COLLECTIONS - HashMap to store category wise spending
    public Map<String, Double> getSpendingByCategory() {
        Map<String, Double> spending = new HashMap<>();
        for (Transaction t : transactions) {
            if (t.getType().equals("EXPENSE")) {
                spending.put(t.getCategory(),
                    spending.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
            }
        }
        return spending;
    }

    // Validate amount - throws custom exception if invalid
    private void validateAmount(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero!");
        }
    }

    // Validate date - throws custom exception if future date
    private void validateDate(LocalDate date) throws InvalidDateException {
        if (date.isAfter(LocalDate.now())) {
            throw new InvalidDateException("Date cannot be in the future!");
        }
    }
}