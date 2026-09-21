// File for representing a market with a collection of stocks and their prices.

import java.util.HashMap;
import java.util.Map;

public class Market {
    private Map<String, Stock> stocks = new HashMap<>();

    public Market() {
        stocks.put("AAPL", new Stock("AAPL", 182.50));
        stocks.put("MSFT", new Stock("MSFT", 415.00));
        stocks.put("GOOGL", new Stock("GOOGL", 155.20));
        stocks.put("AMZN", new Stock("AMZN", 178.00));
        stocks.put("TSLA", new Stock("TSLA", 175.40));
    }

    public Stock getStock(String symbol) {
        return stocks.get(symbol.toUpperCase());
    }

    public void displayMarket() {
        System.out.println("\n MARKET PRICES");
        System.out.printf("%-10s | %-10s%n", "Symbol", "Price");
        for (Stock stock : stocks.values()) {
            System.out.printf("%-10s | $%-10.2f%n", stock.getSymbol(), stock.getPrice());
        }
    }
}