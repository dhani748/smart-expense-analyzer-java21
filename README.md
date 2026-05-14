# Smart Expense Analyzer - Java 21

A modern Java 21 console application that helps users track expenses, analyze spending patterns, detect unusual expenses, and generate smart AI-based financial suggestions.

---

# Features

- Add expenses dynamically
- Categorize expenses
- Analyze category-wise spending
- Detect high/unusual expenses
- AI-based savings recommendations
- Java 21 Virtual Threads
- Clean modular architecture

---

# Tech Stack

- Java 21
- Maven
- Virtual Threads
- Records
- Stream API
- Concurrent Programming

---

# Project Structure

```text
smart-expense-analyzer/
│
├── src/
│   ├── Model/
│   │   ├── Expense.java
│   │   ├── Category.java
│   │   └── Alert.java
│   │
│   ├── Service/
│   │   ├── ExpenseAnalyzer.java
│   │   └── AIRecommendationEngine.java
│   │
│   └── Main.java
```

---

# How It Works

1. User enters expenses
2. Expenses are categorized
3. System calculates category totals
4. High spending patterns are detected
5. AI recommendation engine generates saving tips

---

# Example Run

```bash
====== SMART EXPENSE ANALYZER ======

How many expenses do you want to add? : 2

Enter Expense 1
Title: Swiggy
Amount: 1200

Select Category:
1. FOOD
2. TRAVEL
3. SHOPPING
4. BILLS
5. ENTERTAINMENT
6. HEALTH

Choice: 1

Enter Expense 2
Title: Amazon
Amount: 9000

Choice: 3

====== CATEGORY TOTALS ======
FOOD: ₹1200.0
SHOPPING: ₹9000.0

====== ALERTS ======
High spending detected: Amazon ₹9000.0

====== AI SUGGESTIONS ======
Shopping expenses are high this month
```

---

# Compile and Run

## Compile

```bash
javac src/Main.java
```

## Run

```bash
java src/Main
```

---

# Future Improvements

- Spring Boot REST API
- MySQL/PostgreSQL integration
- JWT Authentication
- Expense history storage
- Monthly PDF reports
- React dashboard
- Docker deployment
- AI forecasting system

---

# Author

Developed using Java 21 for backend/product engineering practice.