package service;

import model.Category;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

import static java.util.Map.Entry;

public class AIRecommendationEngine {

    public List<String> generateSuggestions(Map<Category, Double> categoryTotals) {
        if (categoryTotals == null || categoryTotals.isEmpty()) {
            return List.of("No expenses to analyze. Start adding your expenses!");
        }

        var suggestions = new ConcurrentLinkedQueue<String>();
        var executor = Executors.newVirtualThreadPerTaskExecutor();

        var sorted = categoryTotals.entrySet().stream()
                .sorted(Entry.<Category, Double>comparingByValue().reversed())
                .toList();

        for (var entry : sorted) {
            executor.submit(() -> {
                var tip = generateTip(entry.getKey(), entry.getValue(), categoryTotals);
                suggestions.add(tip);
            });
        }

        executor.close();

        var result = new ArrayList<>(suggestions);
        result.add(0, generateOverallAdvice(categoryTotals));
        return result;
    }

    private String generateTip(Category category, double total, Map<Category, Double> allTotals) {
        return switch (category) {
            case FOOD -> total > 3000
                    ? "Food expenses are high (₹" + total + "). Try meal prepping to save."
                    : "Food spending is under control (₹" + total + ").";
            case SHOPPING -> total > 5000
                    ? "Shopping expenses are high this month (₹" + total + "). Consider a 48-hour rule before purchases."
                    : "Shopping is reasonable (₹" + total + ").";
            case TRAVEL -> total > 10000
                    ? "Travel costs are significant (₹" + total + "). Look for early-bird discounts."
                    : "Travel spending is moderate (₹" + total + ").";
            case BILLS -> total > 8000
                    ? "Bills are high (₹" + total + "). Check for unused subscriptions."
                    : "Bills are manageable (₹" + total + ").";
            case ENTERTAINMENT -> total > 3000
                    ? "Entertainment spending is high (₹" + total + "). Switch to free streaming options."
                    : "Entertainment is in check (₹" + total + ").";
            case HEALTH -> total > 5000
                    ? "Health expenses are high (₹" + total + "). Check insurance coverage."
                    : "Health spending is within range (₹" + total + ").";
        };
    }

    private String generateOverallAdvice(Map<Category, Double> categoryTotals) {
        var topCategory = categoryTotals.entrySet().stream()
                .max(Entry.comparingByValue())
                .map(Entry::getKey)
                .orElse(null);

        var total = categoryTotals.values().stream().mapToDouble(Double::doubleValue).sum();

        return topCategory != null
                ? "Your highest spending category is " + topCategory + " (₹" + categoryTotals.get(topCategory) + "). Total spending: ₹" + total + "."
                : "Total spending: ₹" + total + ".";
    }
}
