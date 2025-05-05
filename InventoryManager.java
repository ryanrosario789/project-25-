import java.util.ArrayList;
import java.util.Scanner;

public class InventoryManager {

    // Inner class to store inventory items
    static class Drug {
        String name;
        int quantity;

        Drug(String name, int quantity) {
            this.name = name;
            this.quantity = quantity;
        }
    }

    private static final ArrayList<Drug> inventory = new ArrayList<>();

    public static void handleInventory() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Inventory Management ---");
            System.out.println("1. View Inventory");
            System.out.println("2. Add New Delivery");
            System.out.println("3. Update Drug Quantity");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    viewInventory();
                    break;
                case 2:
                    addDelivery(scanner);
                    break;
                case 3:
                    updateQuantity(scanner);
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 4);
    }

    private static void viewInventory() {
        System.out.println("\nCurrent Inventory:");
        if (inventory.isEmpty()) {
            System.out.println("No items in inventory.");
        } else {
            for (Drug drug : inventory) {
                System.out.printf("- %s: %d units%n", drug.name, drug.quantity);
            }
        }
    }

    private static void addDelivery(Scanner scanner) {
        System.out.print("Enter drug name: ");
        String name = scanner.nextLine();

        System.out.print("Enter quantity delivered: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // consume newline

        // Check if drug already exists
        for (Drug drug : inventory) {
            if (drug.name.equalsIgnoreCase(name)) {
                drug.quantity += quantity;
                System.out.println("Updated existing drug quantity.");
                return;
            }
        }

        inventory.add(new Drug(name, quantity));
        System.out.println("New drug added to inventory.");
    }

    private static void updateQuantity(Scanner scanner) {
        System.out.print("Enter drug name to update: ");
        String name = scanner.nextLine();

        for (Drug drug : inventory) {
            if (drug.name.equalsIgnoreCase(name)) {
                System.out.print("Enter new quantity: ");
                drug.quantity = scanner.nextInt();
                scanner.nextLine(); // consume newline
                System.out.println("Quantity updated.");
                return;
            }
        }

        System.out.println("Drug not found in inventory.");
    }
}
