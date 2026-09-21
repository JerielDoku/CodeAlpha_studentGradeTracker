//Stock for stating details in the stock market.

import java.io.Serializable;

public class Stock implements Serializable {
    // serialVersionUID for serialization compatibility
    private static final long serialVersionUID = 1L;
// Stock attributes: symbol, name, current price, previous price, day's high and low prices
    private String symbol;
    private String name;
    private double currentPrice;
    private double previousPrice;
    private double dayHigh;
    private double dayLow;

    public Stock(String symbol, String name, double initialPrice) {
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = initialPrice;
        this.previousPrice = initialPrice;
        this.dayHigh = initialPrice;
        this.dayLow = initialPrice;
    }

    public void updatePrice(double newPrice) {
        this.previousPrice = this.currentPrice;
        this.currentPrice = newPrice;
        if (newPrice > dayHigh) dayHigh = newPrice;
        if (newPrice < dayLow) dayLow = newPrice;
    }

    public double getChangePercent() {
        if (previousPrice == 0) return 0.0;
        return ((currentPrice - previousPrice) / previousPrice) * 100;
    }

    // Getter methods for stock attributes
    public String getSymbol() { return symbol; }
    public String getName() { return name; }
    public double getCurrentPrice() { return currentPrice; }
    public double getDayHigh() { return dayHigh; }
    public double getDayLow() { return dayLow; }
// Method to return a string representation of the stock, including symbol, name, current price, change percentage, and day's high/low prices
    @Override
    public String toString() {
        double change = getChangePercent();
        String direction = change >= 0 ? "+" : "";
        return String.format("%-6s %-18s $%8.2f  [%s%.2f%%] (High: $%.2f | Low: $%.2f)",
                symbol, name, currentPrice, direction, change, dayHigh, dayLow);
    }
}