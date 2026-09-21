
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> holdings = new HashMap<>();
    private Map<String, Double> costBasis = new HashMap<>();
    private List<Transaction> transactions = new ArrayList<>();

    public boolean buyStock(Stock stock, int quantity, User user) {
        double totalCost = stock.getPrice() * quantity;
        if (user.getBalance() < totalCost) {
            System.out.printf("Error: Insufficient cash balance! Required: $%.2f, Available: $%.2f%n",
                    totalCost, user.getBalance());
            return false;
        }

        user.deductBalance(totalCost);
        holdings.put(stock.getSymbol(), holdings.getOrDefault(stock.getSymbol(), 0) + quantity);
        costBasis.put(stock.getSymbol(), costBasis.getOrDefault(stock.getSymbol(), 0.0) + totalCost);

        transactions.add(new Transaction(stock.getSymbol(), quantity, stock.getPrice(), "BUY"));
        System.out.printf("Successfully bought %d shares of %s for $%.2f%n", quantity, stock.getSymbol(), totalCost);
        return true;
    }

    public boolean sellStock(Stock stock, int quantity, User user) {
        String symbol = stock.getSymbol();
        int currentQty = holdings.getOrDefault(symbol, 0);

        if (currentQty < quantity) {
            System.out.println("Error: You do not own enough shares to complete this sale!");
            return false;
        }

        double totalRevenue = stock.getPrice() * quantity;
        user.addBalance(totalRevenue);

        double avgCostPerShare = costBasis.get(symbol) / currentQty;
        costBasis.put(symbol, costBasis.get(symbol) - (avgCostPerShare * quantity));

        int newQty = currentQty - quantity;
        if (newQty == 0) {
            holdings.remove(symbol);
            costBasis.remove(symbol);
        } else {
            holdings.put(symbol, newQty);
        }

        transactions.add(new Transaction(symbol, quantity, stock.getPrice(), "SELL"));
        System.out.printf("Successfully sold %d shares of %s for $%.2f%n", quantity, symbol, totalRevenue);
        return true;
    }

    public void displayPerformance(Market market, double userBalance) {
        System.out.println("\n================ PORTFOLIO PERFORMANCE ================");
        System.out.printf("Available Cash Balance: $%.2f%n", userBalance);
        System.out.println("-------------------------------------------------------");

        if (holdings.isEmpty()) {
            System.out.println("No open stock positions.");
            System.out.println("=======================================================");
            return;
        }

        double totalMarketValue = 0.0;
        double totalInvested = 0.0;

        System.out.printf("%-8s | %-6s | %-12s | %-12s | %-12s%n", "Symbol", "Qty", "Cost Basis", "Current Val", "P&L ($)");
        System.out.println("-------------------------------------------------------");

        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            String symbol = entry.getKey();
            int qty = entry.getValue();
            Stock stock = market.getStock(symbol);

            double currentPrice = (stock != null) ? stock.getPrice() : 0.0;
            double currentValue = currentPrice * qty;
            double invested = costBasis.getOrDefault(symbol, 0.0);
            double pnl = currentValue - invested;

            totalMarketValue += currentValue;
            totalInvested += invested;

            System.out.printf("%-8s | %-6d | $%-11.2f | $%-11.2f | %s$%-11.2f%n",
                    symbol, qty, invested, currentValue, (pnl >= 0 ? "+" : ""), pnl);
        }

        double totalPnL = totalMarketValue - totalInvested;
        double pnlPercentage = totalInvested > 0 ? (totalPnL / totalInvested) * 100 : 0.0;

        System.out.println("-------------------------------------------------------");
        System.out.printf("Stock Holdings Value: $%.2f%n", totalMarketValue);
        System.out.printf("Net Total Net Worth : $%.2f%n", totalMarketValue + userBalance);
        System.out.printf("Total Un-realized P&L: %s$%.2f (%.2f%%)%n", (totalPnL >= 0 ? "+" : ""), totalPnL, pnlPercentage);
        System.out.println("=======================================================");
    }

    public void displayTransactions() {
        System.out.println("\n================ TRANSACTION HISTORY ================");
        if (transactions.isEmpty()) {
            System.out.println("No transaction history recorded.");
        } else {
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
        System.out.println("=====================================================");
    }
}