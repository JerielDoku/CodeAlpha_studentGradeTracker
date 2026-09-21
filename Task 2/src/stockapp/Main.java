import java.io.*;
import java.util.Scanner;

public class Main{
    private static final String DATA_FILE = "user_data.dat";

    public static void main(String[] args) {
        Market market = new Market();
        Scanner scanner = new Scanner(System.in);

        User user = loadUserData();
        if (user == null) {
            System.out.print("Enter your username to create account: ");
            String name = scanner.nextLine();
            System.out.print("Enter initial cash deposit amount ($): ");
            double deposit = scanner.nextDouble();
            scanner.nextLine();
            user = new User(name, deposit);
            System.out.println("Welcome, " + name + "! Account created successfully.");
        } else {
            System.out.println("Welcome back, " + user.getUsername() + "!");
        }

        while (true) {
            System.out.println("\n--- STOCK TRADING MENU ---");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio Performance & P&L");
            System.out.println("5. View Transaction History");
            System.out.println("6. Save & Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> market.displayMarket();
                case 2 -> {
                    System.out.print("Enter stock symbol to buy (e.g., AAPL): ");
                    String buySymbol = scanner.nextLine().toUpperCase();
                    Stock buyStock = market.getStock(buySymbol);
                    if (buyStock != null) {
                        System.out.print("Enter quantity to buy: ");
                        int qty = Integer.parseInt(scanner.nextLine());
                        user.getPortfolio().buyStock(buyStock, qty, user);
                    } else {
                        System.out.println("Stock symbol not found in market!");
                    }
                }
                case 3 -> {
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.nextLine().toUpperCase();
                    Stock sellStock = market.getStock(sellSymbol);
                    if (sellStock != null) {
                        System.out.print("Enter quantity to sell: ");
                        int qty = Integer.parseInt(scanner.nextLine());
                        user.getPortfolio().sellStock(sellStock, qty, user);
                    } else {
                        System.out.println("Stock symbol not found in market!");
                    }
                }
                case 4 -> user.getPortfolio().displayPerformance(market, user.getBalance());
                case 5 -> user.getPortfolio().displayTransactions();
                case 6 -> {
                    saveUserData(user);
                    System.out.println("Session data saved successfully. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option. Please choose between 1 and 6.");
            }
        }
    }

    private static void saveUserData(User user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(user);
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }

    private static User loadUserData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (User) ois.readObject();
        } catch (Exception e) {
            System.out.println("Could not load previous data. Starting fresh session.");
            return null;
        }
    }
}