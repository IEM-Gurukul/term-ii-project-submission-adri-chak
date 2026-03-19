
package finance.view;

import finance.model.Budget;
import finance.service.BudgetManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Panel for viewing, editing and deleting budgets
public class BudgetPanel extends JPanel {

    private BudgetManager budgetManager;
    private MainDashboard dashboard;
    private JTable table;
    private DefaultTableModel tableModel;

    public BudgetPanel(BudgetManager bm, MainDashboard dashboard) {
        this.budgetManager = bm;
        this.dashboard = dashboard;
        setLayout(new BorderLayout());

        // Title
        JLabel label = new JLabel("Manage Budgets", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(label, BorderLayout.NORTH);

        // Table
        String[] columns = {"Category", "Limit (Rs.)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Buttons panel
        JPanel bottomPanel = new JPanel(new FlowLayout());

        // Edit button
        JButton editButton = new JButton("Edit Selected");
        editButton.setBackground(new Color(70, 130, 180));
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Arial", Font.BOLD, 12));
        editButton.addActionListener(e -> editSelected());

        // Delete button
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setBackground(new Color(178, 34, 34));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
        deleteButton.addActionListener(e -> deleteSelected());

        bottomPanel.add(editButton);
        bottomPanel.add(deleteButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loadBudgets();
    }

    private void loadBudgets() {
        tableModel.setRowCount(0);
        List<Budget> budgets = budgetManager.getAllBudgets();
        for (Budget b : budgets) {
            tableModel.addRow(new Object[]{b.getCategory(), b.getLimit()});
        }
    }

    private void editSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a budget to edit!");
            return;
        }

        String category = (String) tableModel.getValueAt(selectedRow, 0);
        String newLimitStr = JOptionPane.showInputDialog(this,
                "Enter new limit for " + category + ":", "Edit Budget",
                JOptionPane.PLAIN_MESSAGE);

        if (newLimitStr == null || newLimitStr.trim().isEmpty()) {
            return;
        }

        try {
            double newLimit = Double.parseDouble(newLimitStr.trim());
            if (newLimit <= 0) {
                JOptionPane.showMessageDialog(this, "Limit must be greater than zero!");
                return;
            }
            budgetManager.setBudget(category, newLimit);
            JOptionPane.showMessageDialog(this, "Budget updated for " + category + "!");
            loadBudgets();
            dashboard.refreshAll();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a budget to delete!");
            return;
        }

        String category = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete budget for " + category + "?");

        if (confirm == JOptionPane.YES_OPTION) {
            budgetManager.deleteBudget(category);
            JOptionPane.showMessageDialog(this, "Budget deleted for " + category + "!");
            loadBudgets();
            dashboard.refreshAll();
        }
    }
}