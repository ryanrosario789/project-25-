package DrugsforLess;

import java.util.Scanner;

public class PharmacyLogin {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "pharma123";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Welcome to Drugs for Less Pharmacy ===");
        System.out.print("Enter username: ");
        String inputUsername = scanner.nextLine();

        System.out.print("Enter password: ");
        String inputPassword = scanner.nextLine();

        if (authenticate(inputUsername, inputPassword)) {
            System.out.println("\nLogin successful!\n");
            displayMenu(scanner);
        } else {
            System.out.println("Invalid credentials. Access denied.");
        }

        scanner.close();
    }

    private static boolean authenticate(String username, String password) {
        return USERNAME.equals(username) && PASSWORD.equals(password);
    }

    private static void displayMenu(Scanner scanner) {
        int choice;

        do {
            System.out.println("----- Main Menu -----");
            System.out.println("1. Inventory Management");
            System.out.println("2. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Opening Inventory Management...\n");
                    InventoryManager.handleInventory();
                    break;
                case 2:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
            }

        } while (choice != 2);
    }
}
