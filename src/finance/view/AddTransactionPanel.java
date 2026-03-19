package finance.view;

import finance.exception.InvalidAmountException;
import finance.exception.InvalidDateException;
import finance.service.BudgetManager;
import finance.service.TransactionManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

// Panel for adding income and expense transactions
public class AddTransactionPanel extends JPanel {

    private TransactionManager transactionManager;
    private BudgetManager budgetManager;
    private MainDashboard dashboard;

    // Input fields
    private JTextField descriptionField;
    private JTextField amountField;
    private JTextField dateField;
    private JComboBox<String> categoryBox;
    private JRadioButton incomeRadio;
    private JRadioButton expenseRadio;

    // Budget fields
    private JTextField budgetCategoryField;
    private JTextField budgetLimitField;

    public AddTransactionPanel(TransactionManager tm, BudgetManager bm, MainDashboard dashboard) {
        this.transactionManager = tm;
        this.budgetManager = bm;
        this.dashboard = dashboard;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ── Transaction Section ──
        JLabel transLabel = new JLabel("Add New Transaction");
        transLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(transLabel, gbc);
        gbc.gridwidth = 1;

        // Type selection
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Type:"), gbc);
        incomeRadio = new JRadioButton("Income", true);
        expenseRadio = new JRadioButton("Expense");
        ButtonGroup group = new ButtonGroup();
        group.add(incomeRadio);
        group.add(expenseRadio);
        JPanel typePanel = new JPanel();
        typePanel.add(incomeRadio);
        typePanel.add(expenseRadio);
        gbc.gridx = 1; gbc.gridy = 1;
        add(typePanel, gbc);

        // Description
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Description:"), gbc);
        descriptionField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2;
        add(descriptionField, gbc);

        // Amount
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Amount (Rs.):"), gbc);
        amountField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 3;
        add(amountField, gbc);

        // Date
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Date (YYYY-MM-DD):"), gbc);
        dateField = new JTextField(LocalDate.now().toString(), 20);
        gbc.gridx = 1; gbc.gridy = 4;
        add(dateField, gbc);

        // Category dropdown
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Category:"), gbc);
        String[] categories = {"Food", "Travel", "Rent", "Salary", "Shopping", "Health", "Other"};
        categoryBox = new JComboBox<>(categories);
        gbc.gridx = 1; gbc.gridy = 5;
        add(categoryBox, gbc);

        // Add button
        JButton addButton = new JButton("Add Transaction");
        addButton.setBackground(new Color(34, 139, 34));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        add(addButton, gbc);
        gbc.gridwidth = 1;

        // ── Budget Section ──
        JSeparator sep = new JSeparator();
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        add(sep, gbc);
        gbc.gridwidth = 1;

        JLabel budgetLabel = new JLabel("Set Budget Limit");
        budgetLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        add(budgetLabel, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 9;
        add(new JLabel("Category:"), gbc);
        budgetCategoryField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 9;
        add(budgetCategoryField, gbc);

        gbc.gridx = 0; gbc.gridy = 10;
        add(new JLabel("Limit (Rs.):"), gbc);
        budgetLimitField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 10;
        add(budgetLimitField, gbc);

        JButton budgetButton = new JButton("Set Budget");
        budgetButton.setBackground(new Color(70, 130, 180));
        budgetButton.setForeground(Color.WHITE);
        budgetButton.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 11; gbc.gridwidth = 2;
        add(budgetButton, gbc);

        // ── Button Actions ──
        addButton.addActionListener(e -> addTransaction());
        budgetButton.addActionListener(e -> setBudget());
    }

    private void addTransaction() {
        try {
            String description = descriptionField.getText().trim();
            double amount = Double.parseDouble(amountField.getText().trim());
            LocalDate date = LocalDate.parse(dateField.getText().trim());
            String category = (String) categoryBox.getSelectedItem();

            if (description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Description cannot be empty!");
                return;
            }

            // EXCEPTION HANDLING - catches custom exceptions
            if (incomeRadio.isSelected()) {
                transactionManager.addIncome(description, amount, date, category);
            } else {
                transactionManager.addExpense(description, amount, date, category);
            }

            JOptionPane.showMessageDialog(this, "Transaction added successfully!");
            descriptionField.setText("");
            amountField.setText("");
            dateField.setText(LocalDate.now().toString());
            dashboard.refreshAll();

        } catch (InvalidAmountException | InvalidDateException ex) {
            // EXCEPTION HANDLING - shows user friendly error
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for amount!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Please enter date in YYYY-MM-DD format!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setBudget() {
        try {
            String category = budgetCategoryField.getText().trim();
            double limit = Double.parseDouble(budgetLimitField.getText().trim());

            if (category.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a category!");
                return;
            }

            budgetManager.setBudget(category, limit);
            JOptionPane.showMessageDialog(this, "Budget set for " + category + "!");
            budgetCategoryField.setText("");
            budgetLimitField.setText("");
            dashboard.refreshAll();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for limit!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}