

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private String stockSymbol;
    private int quantity;
    private double pricePerShare;
    private String type; // "BUY" or "SELL"
    private Date timestamp;

    public Transaction(String stockSymbol, int quantity, double pricePerShare, String type) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
        this.type = type;
        this.timestamp = new Date();
    }

    public double getTotalAmount() {
        return quantity * pricePerShare;
    }

    @Override
    public String toString() {
        return String.format("[%s] %-4s %d shares of %-5s @ $%.2f (Total: $%.2f)",
                timestamp.toString(), type, quantity, stockSymbol, pricePerShare, getTotalAmount());
    }
}