package finance.view;

import finance.model.Transaction;
import finance.service.TransactionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// Panel that shows all transaction history in a table
public class HistoryPanel extends JPanel {

    private TransactionManager transactionManager;
    private MainDashboard dashboard;
    private JTable table;
    private DefaultTableModel tableModel;

    // Added dashboard reference so we can refresh report after delete
    public HistoryPanel(TransactionManager tm, MainDashboard dashboard) {
        this.transactionManager = tm;
        this.dashboard = dashboard;
        setLayout(new BorderLayout());

        // Label
        JLabel label = new JLabel("Transaction History", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(label, BorderLayout.NORTH);

        // Table columns
        String[] columns = {"Type", "Description", "Amount (Rs.)", "Date", "Category"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Delete button
        JButton deleteButton = new JButton("Delete Selected");
        deleteButton.setBackground(new Color(178, 34, 34));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
        deleteButton.addActionListener(e -> deleteSelected());
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(deleteButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loadTransactions();
    }

    private void loadTransactions() {
        tableModel.setRowCount(0);
        List<Transaction> transactions = transactionManager.getAllTransactions();
        for (Transaction t : transactions) {
            tableModel.addRow(new Object[]{
                t.getType(),
                t.getDescription(),
                t.getAmount(),
                t.getDate(),
                t.getCategory()
            });
        }
    }

    private void deleteSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a transaction to delete!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this transaction?");
        if (confirm == JOptionPane.YES_OPTION) {
            transactionManager.deleteTransaction(selectedRow);
            loadTransactions();
            // Refresh report panel after deletion
            dashboard.refreshAll();
        }
    }
}