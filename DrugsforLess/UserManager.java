package DrugsforLess;

import java.io.*;
import java.util.*;

public class UserManager {
    private static final String USER_FILE = "users.csv";
    private static final Map<String, String> users = new HashMap<>();

    // Load users from file when class is first used
    static {
        loadUsers();
    }

    private static void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("📁 No existing users found. Starting fresh.");
        }
    }

    private static void saveUser(String username, String password) throws IOException {
        try (FileWriter fw = new FileWriter(USER_FILE, true)) {
            fw.write(username + "," + password + "\n");
        }
    }

    public static boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    public static boolean registerUser(String username, String password) throws IOException {
        if (users.containsKey(username)) return false;
        users.put(username, password);
        saveUser(username, password);
        return true;
    }
}

