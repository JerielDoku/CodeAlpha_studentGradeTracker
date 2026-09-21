
import java.io.*;
import java.util.Scanner;

/**
 * Main application entry point for the Stock Trading Platform.
 * Handles the menu interface, market updates, and serialization/deserialization.
 */
public class Main {
    private static final String DATA_FILE = "user_data.dat";

    public static void main(String[] args) {
        Market market = new Market();
        Scanner scanner = new Scanner(System.in);

        User user = loadUserData();
        if (user == null) {
            System.out.println("WELCOME TO THE STOCK TRADING PLATFORM");
            System.out.print("Enter your username to create account: ");
            String name = scanner.nextLine().trim();

            double deposit = 0;
            while (deposit <= 0) {
                System.out.print("Enter initial cash deposit amount ($): ");
                try {
                    deposit = Double.parseDouble(scanner.nextLine());
                    if (deposit <= 0) {
                        System.out.println("Deposit amount must be greater than $0.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid numerical value.");
                }
            }

            user = new User(name, deposit);
            System.out.println("\nWelcome, " + name + "! Account created successfully.");
        } else {
            System.out.println("\nWelcome back, " + user.getUsername() + "!");
        }

        while (true) {
            //For simulating market price fluctuations before displaying the menu
            market.simulateMarketMovement();

            System.out.println("\n--- STOCK TRADING MENU ---");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio & Balance");
            System.out.println("5. View Transaction History");
            System.out.println("6. Save & Exit");
            System.out.print("Choose an option (1-6): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[Error] Invalid input! Please enter a number between 1 and 6.");
                continue;
            }

            switch (choice) {
                case 1 -> market.displayMarketBoard();
                
                case 2 -> {
                    market.displayMarketBoard();
                    System.out.print("Enter stock symbol to buy (e.g., AAPL): ");
                    String buySymbol = scanner.nextLine().toUpperCase().trim();
                    Stock buyStock = market.getStock(buySymbol);
                    
                    if (buyStock != null) {
                        try {
                            System.out.print("Enter quantity to buy: ");
                            int qty = Integer.parseInt(scanner.nextLine());
                            user.getPortfolio().buyStock(buyStock, qty);
                        } catch (NumberFormatException e) {
                            System.out.println("[Error] Invalid quantity. Please enter an integer.");
                        }
                    } else {
                        System.out.println("[Error] Stock symbol not found in market!");
                    }
                }

                case 3 -> {
                    user.getPortfolio().displayPortfolio(market);
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.nextLine().toUpperCase().trim();
                    Stock sellStock = market.getStock(sellSymbol);

                    if (sellStock != null) {
                        try {
                            System.out.print("Enter quantity to sell: ");
                            int qty = Integer.parseInt(scanner.nextLine());
                            user.getPortfolio().sellStock(sellStock, qty);
                        } catch (NumberFormatException e) {
                            System.out.println("[Error] Invalid quantity. Please enter an integer.");
                        }
                    } else {
                        System.out.println("[Error] Stock symbol not found in market!");
                    }
                }

                case 4 -> user.getPortfolio().displayPortfolio(market);

                case 5 -> user.getPortfolio().displayTransactionHistory();

                case 6 -> {
                    saveUserData(user);
                    System.out.println("Session data saved successfully. Goodbye!");
                    scanner.close();
                    return;
                }

                default -> System.out.println("[Error] Invalid choice. Please choose between 1 and 6.");
            }
        }
    }

    private static void saveUserData(User user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(user);
            System.out.println("Data saved to file: " + DATA_FILE);
        } catch (IOException e) {
            System.out.println("[Error] Failed to save user data: " + e.getMessage());
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
            System.out.println("[Warning] Could not load previous data. Starting fresh session.");
            return null;
        }
    }
}