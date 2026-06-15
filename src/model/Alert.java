package model;

public record Alert(String message, String severity) {
    public static Alert highSpending(String title, double amount) {
        return new Alert("High spending detected: " + title + " \u20B9" + amount, "WARNING");
    }
}
