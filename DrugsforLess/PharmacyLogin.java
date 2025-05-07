package DrugsforLess;

import java.io.Console;
import java.util.Scanner;

public class PharmacyLogin {

    @SuppressWarnings({"ConvertToTryWithResources", "UseSpecificCatch"})
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String loggedInUser = null;

        System.out.println("=== Welcome to Drugs for Less Pharmacy ===");

        while (loggedInUser == null) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.print("Choose option: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String password = readPasswordMasked(scanner);

                    if (UserManager.authenticate(username, password)) {
                        System.out.println("\n✅ Login successful!\n");
                        loggedInUser = username;
                    } else {
                        System.out.println("❌ Invalid credentials. Try again.");
                    }
                }
                case "2" -> {
                    System.out.print("Choose a username: ");
                    String newUser = scanner.nextLine();

                    System.out.print("Choose a password: ");
                    String newPass = readPasswordMasked(scanner);

                    try {
                        if (UserManager.registerUser(newUser, newPass)) {
                            System.out.println("✅ Registration successful! Logging you in...");
                            loggedInUser = newUser;
                        } else {
                            System.out.println("⚠️ Username already exists. Try another one.");
                        }
                    } catch (Exception e) {
                        System.out.println("❗ Error registering user: " + e.getMessage());
                    }
                }
                default -> System.out.println("❗ Invalid option. Please enter 1 or 2.");
            }
        }

        displayMenu(scanner, loggedInUser);
        scanner.close();
    }

    private static void displayMenu(Scanner scanner, String loggedInUser) {
        int choice;

        do {
            System.out.println("\n----- Main Menu -----");
            System.out.println("1. Inventory Management");
            System.out.println("2. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.println("Opening Inventory Management...\n");
                    InventoryManager.handleInventory(loggedInUser);
                }
                case 2 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 2);
    }

    // 🔐 Password masking support
    private static String readPasswordMasked(Scanner fallbackScanner) {
        Console console = System.console();
        if (console != null) {
            char[] passwordChars = console.readPassword();
            return new String(passwordChars);
        } else {
            // Fallback for IDEs (e.g., VS Code)
            System.out.print("(⚠️ visible input in IDE): ");
            return fallbackScanner.nextLine();
        }
    }
}
