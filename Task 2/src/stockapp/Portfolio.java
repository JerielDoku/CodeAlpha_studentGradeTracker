//User-defined class representing a stock portfolio, allowing users to buy/sell stocks, view holdings, and track transaction history.

import java.io.Serializable;
import java.util.*;

public class Portfolio implements Serializable {
    // serialVersionUID for serialization compatibility
    private static final long serialVersionUID = 1L;
// Portfolio attributes: cash balance, holdings (map of stock symbols to quantities), and transaction history
    private double cashBalance;
    private Map<String, Integer> holdings; // Symbol -> Quantity
    private List<Transaction> history;
// Constructor to initialize a new portfolio with an initial cash balance
    public Portfolio(double initialBalance) {
        this.cashBalance = initialBalance;
        this.holdings = new HashMap<>();
        this.history = new ArrayList<>();
    }

    public boolean buyStock(Stock stock, int quantity) {
        if (quantity <= 0) {
            System.out.println("[Error] Quantity must be greater than 0.");
            return false;
        }

        double totalCost = stock.getCurrentPrice() * quantity;
        if (totalCost > cashBalance) {
            System.out.printf("[Error] Insufficient funds. Required: $%.2f | Available: $%.2f\n", totalCost, cashBalance);
            return false;
        }

        cashBalance -= totalCost;
        holdings.put(stock.getSymbol(), holdings.getOrDefault(stock.getSymbol(), 0) + quantity);

        Transaction tx = new Transaction(stock.getSymbol(), quantity, stock.getCurrentPrice(), "BUY");
        history.add(tx);

        System.out.printf("[Success] Purchased %d shares of %s for $%.2f\n", quantity, stock.getSymbol(), totalCost);
        return true;
    }

    public boolean sellStock(Stock stock, int quantity) {
        if (quantity <= 0) {
            System.out.println("[Error] Quantity must be greater than 0.");
            return false;
        }

        int currentHolding = holdings.getOrDefault(stock.getSymbol(), 0);
        if (quantity > currentHolding) {
            System.out.printf("[Error] You only own %d shares of %s.\n", currentHolding, stock.getSymbol());
            return false;
        }

        double totalRevenue = stock.getCurrentPrice() * quantity;
        cashBalance += totalRevenue;

        if (currentHolding == quantity) {
            holdings.remove(stock.getSymbol());
        } else {
            holdings.put(stock.getSymbol(), currentHolding - quantity);
        }

        Transaction tx = new Transaction(stock.getSymbol(), quantity, stock.getCurrentPrice(), "SELL");
        history.add(tx);

        System.out.printf("[Success] Sold %d shares of %s for $%.2f\n", quantity, stock.getSymbol(), totalRevenue);
        return true;
    }
// Method to display the current portfolio, including cash balance, stock holdings, and their market values
    public void displayPortfolio(Market market) {
        System.out.println("\nYOUR PORTFOLIO");
        System.out.printf("Cash Balance: $%.2f\n", cashBalance);
       
        if (holdings.isEmpty()) {
            System.out.println("No stock holdings.");
        } else {
            double totalPortfolioValue = cashBalance;
            System.out.printf("%-8s %-10s %-14s %-14s\n", "Symbol", "Shares", "Current Price", "Market Value");
            for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
                Stock stock = market.getStock(entry.getKey());
                double value = stock.getCurrentPrice() * entry.getValue();
                totalPortfolioValue += value;
                System.out.printf("%-8s %-10d $%-13.2f $%-13.2f\n", entry.getKey(), entry.getValue(), stock.getCurrentPrice(), value);
            }
            System.out.printf("Total Portfolio Value: $%.2f\n", totalPortfolioValue);
        }
  }
//for displaying the transaction history, including all buy/sell transactions with details like stock symbol, quantity, price per share, and timestamp
    public void displayTransactionHistory() {
        System.out.println("\nTRANSACTION HISTORY");
        if (history.isEmpty()) {
            System.out.println("No transactions recorded.");
        } else {
            for (Transaction tx : history) {
                System.out.println(tx);
            }
        }
        }

    public double getCashBalance() { return cashBalance; }
}