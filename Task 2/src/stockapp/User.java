// User class representing a user in the stock trading application
import java.io.Serializable;


// User class representing a user in the stock trading application
public class User implements Serializable {
    // serialVersionUID for serialization compatibility
    private static final long serialVersionUID = 1L;
// User attributes: username, balance, and portfolio
    private String username;
    private double balance;
    private Portfolio portfolio;
// Constructor to initialize a new user with a username and initial balance
    public User(String username, double initialBalance) {
        this.username = username;
        this.balance = initialBalance;
        this.portfolio = new Portfolio();
    }
// Getter methods for user attributes and methods to modify balance
    public String getUsername() {
        return username;
    }
// Getter for user balance
    public double getBalance() {
        return balance;
    }
// Getter for user portfolio
    public Portfolio getPortfolio() {
        return portfolio;
    }
// Method to deduct balance when buying stocks
    public void deductBalance(double amount) {
        this.balance -= amount;
    }
// Method to add balance when selling stocks
    public void addBalance(double amount) {
        this.balance += amount;
    }
}