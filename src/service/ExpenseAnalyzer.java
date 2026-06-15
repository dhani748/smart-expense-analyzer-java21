package service;

import model.Alert;
import model.Category;
import model.Expense;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ExpenseAnalyzer {
    private static final double HIGH_SPENDING_THRESHOLD = 5000.0;

    public Map<Category, List<Expense>> categorizeExpenses(List<Expense> expenses) {
        return expenses.stream()
                .collect(Collectors.groupingBy(Expense::category));
    }

    public Map<Category, Double> calculateCategoryTotals(List<Expense> expenses) {
        return expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::category,
                        Collectors.summingDouble(Expense::amount)
                ));
    }

    public List<Alert> detectHighExpenses(List<Expense> expenses) {
        return expenses.stream()
                .filter(e -> e.amount() > HIGH_SPENDING_THRESHOLD)
                .map(e -> Alert.highSpending(e.title(), e.amount()))
                .toList();
    }

    public Map<Category, Double> calculateCategoryTotalsConcurrent(List<Expense> expenses)
            throws InterruptedException, ExecutionException {
        var categoryGroups = categorizeExpenses(expenses);
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var futures = new ArrayList<Future<Map.Entry<Category, Double>>>();

        for (var entry : categoryGroups.entrySet()) {
            futures.add(executor.submit(() -> {
                double total = entry.getValue().stream()
                        .mapToDouble(Expense::amount)
                        .sum();
                return Map.entry(entry.getKey(), total);
            }));
        }

        var result = new HashMap<Category, Double>();
        for (var future : futures) {
            var entry = future.get();
            result.put(entry.getKey(), entry.getValue());
        }
        executor.close();
        return result;
    }

    public AnalysisResult analyze(List<Expense> expenses) {
        var categoryTotals = calculateCategoryTotals(expenses);
        var alerts = detectHighExpenses(expenses);
        return new AnalysisResult(categoryTotals, alerts);
    }

    public record AnalysisResult(Map<Category, Double> categoryTotals, List<Alert> alerts) {}
}
