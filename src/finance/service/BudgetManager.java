package finance.service;

import finance.model.Budget;
import finance.service.TransactionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// This class manages budget limits per category
// ENCAPSULATION - all budget logic is hidden inside this class
public class BudgetManager {

    // COLLECTIONS - ArrayList to store all budgets
    private List<Budget> budgets;

    // Reference to TransactionManager to get actual spending
    private TransactionManager transactionManager;

    // Constructor
    public BudgetManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        budgets = new ArrayList<>();
    }

    // Set a budget limit for a category
    public void setBudget(String category, double limit) {
        // Check if budget already exists for this category
        for (Budget b : budgets) {
            if (b.getCategory().equalsIgnoreCase(category)) {
                b.setLimit(limit);
                return;
            }
        }
        // If not found, add a new one
        budgets.add(new Budget(category, limit));
    }

    // Get all budgets
    public List<Budget> getAllBudgets() {
        return budgets;
    }

    // Check if spending has exceeded budget for any category
    public String getBudgetStatus() {
        // Get actual spending per category from TransactionManager
        Map<String, Double> spending = transactionManager.getSpendingByCategory();
        StringBuilder status = new StringBuilder();

        for (Budget b : budgets) {
            double spent = spending.getOrDefault(b.getCategory(), 0.0);
            double limit = b.getLimit();

            if (spent > limit) {
                status.append("EXCEEDED - " + b.getCategory() +
                        ": Spent Rs." + spent + " / Limit Rs." + limit + "\n");
            } else {
                status.append("OK - " + b.getCategory() +
                        ": Spent Rs." + spent + " / Limit Rs." + limit + "\n");
            }
        }

        if (status.length() == 0) {
            return "No budgets set yet.";
        }

        return status.toString();
    }
    // Delete a budget by category
    public boolean deleteBudget(String category) {
    return budgets.removeIf(b -> b.getCategory().equalsIgnoreCase(category));
    }

    // Get budget for a specific category
    public Budget getBudget(String category) {
    for (Budget b : budgets) {
        if (b.getCategory().equalsIgnoreCase(category)) {
            return b;
        }
    }
    return null;
}
}