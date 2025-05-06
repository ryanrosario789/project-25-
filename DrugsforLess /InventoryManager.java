package DrugsforLess;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class InventoryManager {
    static final String FILE_NAME = "inventory.csv";
    static ArrayList<Medicine> inventory = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void handleInventory() {
        loadInventory();

        while (true) {
            System.out.println("\n📋 Pharmacy Inventory System");
            System.out.println("1. Add Medicine");
            System.out.println("2. View Inventory");
            System.out.println("3. Update Stock");
            System.out.println("4. Search Medicine");
            System.out.println("5. Save & Return to Main Menu");
            System.out.print("Select an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addMedicine();
                    case 2 -> viewInventory();
                    case 3 -> updateStock();
                    case 4 -> searchMedicine();
                    case 5 -> {
                        saveInventory();
                        System.out.println("✅ Inventory saved. Returning to main menu...");
                        return;
                    }
                    default -> System.out.println("❗ Please enter a number from 1–5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❗ Invalid input. Please enter a number.");
            }
        }
    }

    // --- Medicine Class ---
    static class Medicine {
        String name;
        int quantity;
        String expirationDate;
        double price;

        public Medicine(String name, int quantity, String expirationDate, double price) {
            this.name = name;
            this.quantity = quantity;
            this.expirationDate = expirationDate;
            this.price = price;
        }

        public String toCSV() {
            return name + "," + quantity + "," + expirationDate + "," + price;
        }

        public static Medicine fromCSV(String line) {
            String[] parts = line.split(",");
            return new Medicine(parts[0], Integer.parseInt(parts[1]), parts[2], Double.parseDouble(parts[3]));
        }

        public String toString() {
            return String.format("Name: %s | Quantity: %d | Expires: %s | Price: $%.2f",
                    name, quantity, expirationDate, price);
        }
    }

    // --- Add Medicine ---
    static void addMedicine() {
        try {
            System.out.print("Enter medicine name: ");
            String name = scanner.nextLine();

            for (Medicine m : inventory) {
                if (m.name.equalsIgnoreCase(name)) {
                    System.out.println("⚠️ Medicine already exists. Use 'Update Stock' instead.");
                    return;
                }
            }

            System.out.print("Enter quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter expiration date (YYYY-MM-DD): ");
            String expirationDate = scanner.nextLine();

            System.out.print("Enter price: ");
            double price = Double.parseDouble(scanner.nextLine());

            inventory.add(new Medicine(name, quantity, expirationDate, price));
            System.out.println("✅ Medicine added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("❗ Invalid number format. Try again.");
        }
    }

    // --- View All Medicines ---
    static void viewInventory() {
        if (inventory.isEmpty()) {
            System.out.println("📦 Inventory is currently empty.");
            return;
        }
        System.out.println("\nCurrent Inventory:");
        for (Medicine m : inventory) {
            System.out.println("- " + m);
        }
    }

    // --- Update Stock ---
    static void updateStock() {
        System.out.print("Enter medicine name to update: ");
        String name = scanner.nextLine();
        for (Medicine m : inventory) {
            if (m.name.equalsIgnoreCase(name)) {
                try {
                    System.out.print("Enter quantity to add: ");
                    int addQty = Integer.parseInt(scanner.nextLine());
                    m.quantity += addQty;
                    System.out.println("✅ Stock updated.");
                } catch (NumberFormatException e) {
                    System.out.println("❗ Invalid quantity.");
                }
                return;
            }
        }
        System.out.println("❌ Medicine not found.");
    }

    // --- Search for Medicine ---
    static void searchMedicine() {
        System.out.print("Enter medicine name to search: ");
        String name = scanner.nextLine();
        boolean found = false;
        for (Medicine m : inventory) {
            if (m.name.equalsIgnoreCase(name)) {
                System.out.println("🔍 Found: " + m);
                found = true;
            }
        }
        if (!found) {
            System.out.println("❌ Medicine not found.");
        }
    }

    // --- Save Inventory to CSV ---
    static void saveInventory() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Medicine m : inventory) {
                writer.println(m.toCSV());
            }
        } catch (IOException e) {
            System.out.println("❗ Error saving inventory: " + e.getMessage());
        }
    }

    // --- Load Inventory from CSV ---
    static void loadInventory() {
        inventory.clear(); // Avoid duplication if called multiple times
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                inventory.add(Medicine.fromCSV(line));
            }
        } catch (FileNotFoundException e) {
            System.out.println("📁 No saved inventory found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("❗ Error reading inventory: " + e.getMessage());
        }
    }
}
