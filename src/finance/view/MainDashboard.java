package finance.view;

import finance.service.TransactionManager;
import finance.service.BudgetManager;
import finance.view.BudgetPanel;

import javax.swing.*;
import java.awt.*;

// Main window of the application - entry point
// This is the VIEW layer - only handles UI, no business logic
public class MainDashboard extends JFrame {

    private TransactionManager transactionManager;
    private BudgetManager budgetManager;

    // The main tabbed panel
    private JTabbedPane tabbedPane;

    public MainDashboard() {
        // Initialize service layer
        transactionManager = new TransactionManager();
        budgetManager = new BudgetManager(transactionManager);

        // Window settings
        setTitle("Personal Finance Tracker");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // Set up the UI
        initUI();

        setVisible(true);
    }

    private void initUI() {
        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header label
        JLabel headerLabel = new JLabel("Personal Finance Tracker", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 26));
        headerLabel.setForeground(new Color(34, 85, 34));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Tabbed pane with 3 tabs
        tabbedPane = new JTabbedPane();

        // Add panels as tabs
        tabbedPane.addTab("Add Transaction", new AddTransactionPanel(transactionManager, budgetManager, this));
        tabbedPane.addTab("History", new HistoryPanel(transactionManager, this));
        tabbedPane.addTab("Report", new ReportPanel(transactionManager, budgetManager));
        tabbedPane.addTab("Budgets", new BudgetPanel(budgetManager, this));

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }

    // Refresh all tabs when new data is added
    public void refreshAll() {
    tabbedPane.setComponentAt(1, new HistoryPanel(transactionManager, this));
    tabbedPane.setComponentAt(2, new ReportPanel(transactionManager, budgetManager));
    tabbedPane.setComponentAt(3, new BudgetPanel(budgetManager, this));
    }

    // Main method - application starts here
    public static void main(String[] args) {
    // Increase default font size for all Swing components
    java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
    while (keys.hasMoreElements()) {
        Object key = keys.nextElement();
        Object value = UIManager.getDefaults().get(key);
        if (value instanceof javax.swing.plaf.FontUIResource) {
            UIManager.put(key, new javax.swing.plaf.FontUIResource("Arial", Font.PLAIN, 16));
        }
    }
    SwingUtilities.invokeLater(() -> new MainDashboard());
    }
}