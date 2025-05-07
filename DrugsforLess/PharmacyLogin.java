package DrugsforLess;

import java.util.Scanner;

public class PharmacyLogin {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Welcome to Drugs for Less Pharmacy ===");

        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Choose option: ");
        int option = Integer.parseInt(scanner.nextLine());

        if (option == 1) {
            // Login flow
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (UserManager.authenticate(username, password)) {
                System.out.println("\n✅ Login successful!\n");
                displayMenu(scanner, username);
            } else {
                System.out.println("❌ Invalid credentials. Access denied.");
            }

        } else if (option == 2) {
            // Registration flow
            System.out.print("Choose a username: ");
            String newUser = scanner.nextLine();

            System.out.print("Choose a password: ");
            String newPass = scanner.nextLine();

            try {
                if (UserManager.registerUser(newUser, newPass)) {
                    System.out.println("✅ Registration successful! You can now log in.");
                } else {
                    System.out.println("⚠️ Username already exists. Try again.");
                }
            } catch (Exception e) {
                System.out.println("❗ Error registering user: " + e.getMessage());
            }
        } else {
            System.out.println("❗ Invalid choice.");
        }

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
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.println("Opening Inventory Management...\n");
                    InventoryManager.handleInventory(loggedInUser);  // ✅ Pass username
                }
                case 2 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid choice. Please try again.\n");
            }

        } while (choice != 2);
    }
}
