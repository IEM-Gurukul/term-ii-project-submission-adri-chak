package finance.view;

import finance.service.BudgetManager;
import finance.service.TransactionManager;

import javax.swing.*;
import java.awt.*;

// Panel that shows financial summary and budget status
public class ReportPanel extends JPanel {

    private TransactionManager transactionManager;
    private BudgetManager budgetManager;

    public ReportPanel(TransactionManager tm, BudgetManager bm) {
        this.transactionManager = tm;
        this.budgetManager = bm;
        setLayout(new BorderLayout());

        // Label
        JLabel label = new JLabel("Financial Report", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(label, BorderLayout.NORTH);

        // Report content panel
        JPanel reportPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        reportPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Total income
        double totalIncome = transactionManager.getTotalIncome();
        JLabel incomeLabel = new JLabel("Total Income:   Rs. " + String.format("%.2f", totalIncome));
        incomeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        incomeLabel.setForeground(new Color(34, 139, 34));
        reportPanel.add(incomeLabel);

        // Total expenses
        double totalExpenses = transactionManager.getTotalExpenses();
        JLabel expenseLabel = new JLabel("Total Expenses: Rs. " + String.format("%.2f", totalExpenses));
        expenseLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        expenseLabel.setForeground(new Color(178, 34, 34));
        reportPanel.add(expenseLabel);

        // Net balance
        double balance = totalIncome - totalExpenses;
        JLabel balanceLabel = new JLabel("Net Balance:    Rs. " + String.format("%.2f", balance));
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        balanceLabel.setForeground(balance >= 0 ? new Color(34, 139, 34) : new Color(178, 34, 34));
        reportPanel.add(balanceLabel);

        // Separator
        reportPanel.add(new JSeparator());

        // Budget status
        JLabel budgetTitle = new JLabel("Budget Status:");
        budgetTitle.setFont(new Font("Arial", Font.BOLD, 14));
        reportPanel.add(budgetTitle);

        String budgetStatus = budgetManager.getBudgetStatus();
        JTextArea budgetArea = new JTextArea(budgetStatus);
        budgetArea.setEditable(false);
        budgetArea.setFont(new Font("Arial", Font.PLAIN, 13));
        budgetArea.setBackground(getBackground());
        reportPanel.add(budgetArea);

        add(new JScrollPane(reportPanel), BorderLayout.CENTER);
    }
}