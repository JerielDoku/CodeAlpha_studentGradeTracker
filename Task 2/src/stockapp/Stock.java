// File for representing a stock with its symbol and price, implementing Serializable for object serialization.
import java.io.Serializable;
// Stock class representing a stock with its symbol and price, implementing Serializable for object serialization.
public class Stock implements Serializable {
    private static final long serialVersionUID = 1L;

    private String symbol;
    private double price;
// Constructor to initialize a stock with its symbol and price
    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
// Getter and setter methods for stock attributes
    public String getSymbol() {
        return symbol;
    }
// Setter for stock symbol
    public double getPrice() {
        return price;
    }
// Setter for stock price
    public void setPrice(double price) {
        this.price = price;
    }
}