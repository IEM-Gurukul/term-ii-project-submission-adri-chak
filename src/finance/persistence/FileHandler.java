package finance.persistence;

import finance.model.Transaction;
import finance.model.Income;
import finance.model.Expense;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// This class handles all file operations - reading and writing CSV
// ENCAPSULATION - all file logic is hidden inside this class
public class FileHandler {

    // The file where all transactions will be saved
    private static final String FILE_PATH = "transactions.csv";

    // Save all transactions to CSV file
    public static void saveTransactions(List<Transaction> transactions) {
        try {
            FileWriter writer = new FileWriter(FILE_PATH);
            for (Transaction t : transactions) {
                // Format: type,description,amount,date,category
                writer.write(t.getType() + "," +
                        t.getDescription() + "," +
                        t.getAmount() + "," +
                        t.getDate() + "," +
                        t.getCategory() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            // EXCEPTION HANDLING - if file cannot be written
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    // Load all transactions from CSV file
    public static List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(FILE_PATH);

        // If file doesn't exist yet, return empty list
        if (!file.exists()) {
            return transactions;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while ((line = reader.readLine()) != null) {
                // Split each line by comma
                String[] parts = line.split(",");

                // Check for malformed data - improvement suggested by sir
                if (parts.length != 5) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }

                String type = parts[0];
                String description = parts[1];
                double amount = Double.parseDouble(parts[2]);
                LocalDate date = LocalDate.parse(parts[3]);
                String category = parts[4];

                // POLYMORPHISM - creating Income or Expense based on type
                if (type.equals("INCOME")) {
                    transactions.add(new Income(description, amount, date, category));
                } else if (type.equals("EXPENSE")) {
                    transactions.add(new Expense(description, amount, date, category));
                }
            }
            reader.close();
        } catch (IOException e) {
            // EXCEPTION HANDLING - if file cannot be read
            System.out.println("Error loading transactions: " + e.getMessage());
        }
        return transactions;
    }
}