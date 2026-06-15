import model.Category;
import model.Expense;
import service.AIRecommendationEngine;
import service.ExpenseAnalyzer;

import java.util.*;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final ExpenseAnalyzer ANALYZER = new ExpenseAnalyzer();
    private static final AIRecommendationEngine ENGINE = new AIRecommendationEngine();

    public static void main(String[] args) {
        System.out.println("====== SMART EXPENSE ANALYZER ======\n");

        var expenses = collectExpenses();

        System.out.println("\n====== CATEGORY TOTALS ======");
        var result = ANALYZER.analyze(expenses);
        result.categoryTotals().forEach((cat, total) ->
                System.out.println(cat + ": \u20B9" + total));

        System.out.println("\n====== ALERTS ======");
        if (result.alerts().isEmpty()) {
            System.out.println("No unusual spending detected.");
        } else {
            result.alerts().forEach(alert ->
                    System.out.println(alert.message()));
        }

        System.out.println("\n====== AI SUGGESTIONS ======");
        var suggestions = ENGINE.generateSuggestions(result.categoryTotals());
        suggestions.forEach(System.out::println);
    }

    private static List<Expense> collectExpenses() {
        System.out.print("How many expenses do you want to add? : ");
        var count = Integer.parseInt(SCANNER.nextLine().trim());
        var expenses = new ArrayList<Expense>(count);

        for (int i = 1; i <= count; i++) {
            System.out.println("\nEnter Expense " + i);
            System.out.print("Title: ");
            var title = SCANNER.nextLine().trim();

            System.out.print("Amount: ");
            var amount = Double.parseDouble(SCANNER.nextLine().trim());

            System.out.println("\nSelect Category:");
            var categories = Category.values();
            for (int j = 0; j < categories.length; j++) {
                System.out.println((j + 1) + ". " + categories[j]);
            }
            System.out.print("\nChoice: ");
            var choice = Integer.parseInt(SCANNER.nextLine().trim());

            expenses.add(new Expense(title, amount, categories[choice - 1]));
        }

        return expenses;
    }
}
