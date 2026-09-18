public class Contact {
    private final int id;
    private final String name;
    private final String phone;
    private final String email;

    public Contact(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("%-6d %-20s %-15s %-30s",
                id, name, phone, email);
    }
}
