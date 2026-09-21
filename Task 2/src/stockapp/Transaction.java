// File for handling stock transactions, including buying and selling stocks, and recording transaction history.

import java.io.Serializable;
import java.util.Date;

// Transaction class representing a stock transaction (buy/sell) with details like stock symbol, quantity, price per share, type of transaction, and timestamp.
public class Transaction implements Serializable {
    // serialVersionUID for serialization compatibility
    private static final long serialVersionUID = 1L;

    // Transaction attributes: stock symbol, quantity, price per share, type of transaction (BUY/SELL), and timestamp
    private String stockSymbol;
    private int quantity;
    private double pricePerShare;
    private String type;
    private Date timestamp;
// Constructor to initialize a new transaction with the provided details
    public Transaction(String stockSymbol, int quantity, double pricePerShare, String type) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.type = type;
        this.timestamp = new Date();
    }
// Getter methods for transaction attributes
    public double getTotalAmount() {
        return quantity * pricePerShare;
    }
// MeTHOD to return a string representation of the transaction, including timestamp, type, quantity, stock symbol, price per share, and total amount
    @Override
    public String toString() {
        return String.format("[%s] %-4s %d shares of %-5s @ $%.2f (Total: $%.2f)",
                timestamp.toString(), type, quantity, stockSymbol, pricePerShare, getTotalAmount());
    }
}