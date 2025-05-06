package DrugsforLess;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class InventoryManager {
    static final String FILE_NAME = "inventory.csv";
    static ArrayList<Medicine> inventory = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void handleInventory() {
        loadInventory();

        while (true) {
            System.out.println("\n===============================");
            System.out.println("📋 Pharmacy Inventory System");
            System.out.println("===============================");
            System.out.println("1. Add Medicine");
            System.out.println("2. View Inventory");
            System.out.println("3. Update Stock");
            System.out.println("4. Search Medicine");
            System.out.println("5. Delete Medicine");
            System.out.println("6. Save & Return to Main Menu");
            System.out.print("Select an option (1–6): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    1 -> addMedicine();
                    case 2 -> viewInventory();
                    case 3 -> updateStock();
                    case 4 -> searchMedicine();
                    case 5 -> deleteMedicine();
                    case 6 -> {
                        saveInventory();
                        System.out.println("✅ Inventory saved. Returning to main menu...");
                        return;
                    }
                    default -> System.out.println("❗ Please enter a number from 1–6.");
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

    // --- Add Medicine with Date Validation ---
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

            String expirationDate;
            while (true) {
                System.out.print("Enter expiration date (YYYY-MM-DD): ");
                expirationDate = scanner.nextLine();
                if (isValidFutureDate(expirationDate)) break;
                else System.out.println("❗ Invalid or past date. Try again.");
            }

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
        System.out.println("\n--- Current Inventory ---");
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
                } catch (NumberFormatExceptio e) {
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
                System.out.println("🔍 Found " + m);
                found = true;
            }
        }
        if (!found) {
            System.out.println("❌ Medicine not found.");
        }
    }

    // --- Delete Medicine ---
    static void deleteMedicine() {
        System.out.print("Enter medicine name to delete: ");
        String name = scanner.nextLine();

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equalsIgnoreCase(name)) {
                System.out.print("⚠️ Are you sure you want to delete '" + name + "'? (yes/no): ");
                String confirm = scanner.nextLine();
                if (confirm.equalsIgnoreCase("yes")) {
                    inventory.remove(i);
                    System.out.println("🗑️ Medicine deleted.");
                } else {
                    System.out.println("❎ Deletion cancelled.");
                }
                return;
            }
        }

        System.out.println("❌ Medicine not found.");
    }

    // --- Validate Date Format and Future Date ---
    static boolean isValidFutureDate(String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr, DATE_FORMAT);
            return !date.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
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
        inventory.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME)) {
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
