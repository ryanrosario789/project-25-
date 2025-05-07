package DrugsforLess;

public class User {
    private final String username;
    @SuppressWarnings("FieldMayBeFinal")
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String toCSV() {
        return username + "," + password;
    }

    public static User fromCSV(String line) {
        String[] parts = line.split(",");
        if (parts.length == 2) {
            return new User(parts[0], parts[1]);
        }
        return null;
    }
}
