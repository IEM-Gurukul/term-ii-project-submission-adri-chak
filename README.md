[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/pG3gvzt-)
# PCCCS495 – Term II Project

## Project Title
Personal Finance Tracker

---

## Problem Statement (max 150 words)
Managing personal finances is a common challenge for students and working individuals. Most people lack a structured way to record income, track expenses across categories, and monitor budgets over time. Existing tools are either too complex or require internet access. This application provides a simple, offline desktop solution built in Java that allows users to log financial transactions, categorise spending, set monthly budgets, and view summarised reports. The goal is to promote better financial awareness through a clean and intuitive interface, without relying on any external frameworks or databases.

---

## Target User
Students, young professionals, and individuals who want a simple offline tool to track their daily income and expenses without using cloud-based services.

---

## Core Features
- Add, edit and delete income and expense transactions
- Categorise transactions (Food, Travel, Rent, Salary, Shopping, Health, Other)
- Set and monitor monthly budget limits per category
- View full transaction history with delete option
- Generate monthly summary report (total income vs total expenses vs net balance)
- Budget status report showing OK or EXCEEDED per category
- Persist all data to a local CSV file for session continuity
- Input validation with user-friendly error messages

---

## OOP Concepts Used
- **Abstraction:** Abstract class `Transaction` defines common fields and abstract method `getType()`, hiding implementation details from the UI layer
- **Inheritance:** `Income` and `Expense` extend `Transaction`, each overriding `getType()` with their own implementation. Custom exceptions `InvalidAmountException` and `InvalidDateException` extend Java's `Exception` class
- **Polymorphism:** A `List<Transaction>` stores both `Income` and `Expense` objects. `getType()` resolves correctly at runtime via dynamic dispatch
- **Exception Handling:** Custom exceptions handle invalid amount and invalid date input. `IOException` is caught during CSV file operations. Malformed CSV lines are skipped gracefully
- **Collections:** `ArrayList<Transaction>` stores transaction history. `HashMap<String, Double>` tracks total spending per category for budget comparison

---

## Proposed Architecture Description
The application follows a three-layer architecture. The **model** layer contains the abstract `Transaction` class and its subclasses `Income` and `Expense`, along with a `Budget` class. The **service** layer contains `TransactionManager` which handles adding, deleting, filtering and summarising transactions, and `BudgetManager` which manages budget limits and compares them against actual spending. The **persistence** layer contains `FileHandler` which reads and writes transaction data to a local CSV file. The **view** layer consists of Java Swing panels: `MainDashboard` (entry point), `AddTransactionPanel`, `HistoryPanel`, `ReportPanel` and `BudgetPanel`.

---

## How to Run
1. Make sure JDK 8 or above is installed
2. Clone this repository
3. Open terminal and navigate to the `src/` folder
4. Compile all files:
```
javac finance/model/*.java finance/exception/*.java finance/persistence/*.java finance/service/*.java finance/view/*.java
```
5. Run the application:
```
java finance.view.MainDashboard
```
6. No external libraries required — runs on any machine with JDK installed

---

## Git Discipline Notes

This project follows incremental development practices as required by the course guidelines.

- **Total commits:** 13 meaningful commits + A few modifications as per needs
- **Development span:** Multiple days (March 2026)
- **Commit strategy:** One commit per feature or class addition — no bulk uploads

### Commit Summary
| Commit | Description |
|--------|-------------|
| 1 | Add abstract Transaction class with Income and Expense subclasses |
| 2 | Add Budget model and custom exception classes |
| 3 | Add FileHandler for CSV persistence with malformed data handling |
| 4 | Add TransactionManager with business logic and input validation |
| 5 | Add BudgetManager for category wise budget tracking |
| 6 | Add deleteBudget and getBudget methods to BudgetManager |
| 7 | Fix HistoryPanel to refresh ReportPanel after transaction delete |
| 8 | Add BudgetPanel tab for viewing editing and deleting budgets |
| 9 | Fix dashboard refresh after adding transaction and setting budget |
| 10 | Complete README with project details architecture and run instructions |
| 11 | Add UML class diagram showing package structure and OOP relationships |
| 12 | Add project report covering OOP design architecture and test cases |
| 13 | Add presentation slides covering architecture OOP and test results |
