// Market class for simulating a stock market with multiple stocks and their price fluctuations.

import java.util.*;

public class Market {
    private Map<String, Stock> stocks;

    public Market() {
        stocks = new HashMap<>();
        initializeMarket();
    }

    private void initializeMarket() {
        stocks.put("AAPL", new Stock("AAPL", "Apple Inc.", 175.50));
        stocks.put("GOOGL", new Stock("GOOGL", "Alphabet Inc.", 140.25));
        stocks.put("AMZN", new Stock("AMZN", "Amazon.com Inc.", 178.00));
        stocks.put("MSFT", new Stock("MSFT", "Microsoft Corp.", 415.10));
        stocks.put("TSLA", new Stock("TSLA", "Tesla Inc.", 175.00));
    }

    // Simulates random market price fluctuations (-3% to +3%)
    public void simulateMarketMovement() {
        Random rand = new Random();
        for (Stock stock : stocks.values()) {
            double percentChange = (rand.nextDouble() * 6.0) - 3.0; // -3.0% to +3.0%
            double newPrice = stock.getCurrentPrice() * (1 + (percentChange / 100.0));
            stock.updatePrice(Math.round(newPrice * 100.0) / 100.0);
        }
    }

    public void displayMarketBoard() {
        System.out.println("\nLIVE MARKET DATA");
        System.out.printf("%-6s %-18s %-10s %-10s %-10s %-10s\n", "Symbol", "Company", "Price", "Change", "Day High", "Day Low");
        for (Stock stock : stocks.values()) {
            System.out.println(stock);
        }
    }

    public Stock getStock(String symbol) {
        return stocks.get(symbol.toUpperCase());
    }
}