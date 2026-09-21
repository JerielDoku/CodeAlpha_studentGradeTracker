import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private double balance;
    private Portfolio portfolio;

    public User(String username, double initialBalance) {
        this.username = username;
        this.balance = initialBalance;
        this.portfolio = new Portfolio();
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