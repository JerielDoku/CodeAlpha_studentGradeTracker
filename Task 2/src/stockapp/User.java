
import java.io.Serializable;

/**
 * User class representing a trader in the stock trading platform.
 * Manages cash balance, user credentials, and holds the user's Portfolio instance.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private double balance;
    private Portfolio portfolio;

    public User(String username, double initialBalance) {
        this.username = username;
        this.balance = initialBalance;
        // For each user, we create a new Portfolio instance initialized with the user's initial balance.
        this.portfolio = new Portfolio(initialBalance);
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void deductBalance(double amount) {
        this.balance -= amount;
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }
}