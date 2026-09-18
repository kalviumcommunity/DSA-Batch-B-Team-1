import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ContactManager manager = new ContactManager();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("       SMART CONTACT SEARCH SYSTEM");
        System.out.println("========================================");

        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    deleteContact();
                    break;
                case 3:
                    searchById();
                    break;
                case 4:
                    searchByName();
                    break;
                case 5:
                    manager.displayContacts();
                    break;
                case 6:
                    System.out.println("Total contacts: " + manager.countContacts());
                    break;
                case 7:
                    running = false;
                    System.out.println("Thank you for using Smart Contact Search System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1-7.");
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("========================================");
        System.out.println("1. Add Contact");
        System.out.println("2. Delete Contact");
        System.out.println("3. Search by Contact ID");
        System.out.println("4. Search by Name");
        System.out.println("5. Display Contacts");
        System.out.println("6. Count Contacts");
        System.out.println("7. Exit");
        System.out.println("========================================");
    }

    private static void addContact() {
        System.out.println("\n--- Add Contact ---");

        int id = readInt("Contact ID: ");

        if (manager.searchById(id) != null) {
            System.out.println("Error: Contact ID already exists.");
            return;
        }

        String name = readNonEmpty("Name: ");
        String phone = readNonEmpty("Phone Number: ");
        String email = readNonEmpty("Email: ");

        Contact contact = new Contact(id, name, phone, email);

        if (manager.addContact(contact)) {
            System.out.println("Contact added successfully.");
        } else {
            System.out.println("Could not add contact.");
        }
    }

    private static void deleteContact() {
        System.out.println("\n--- Delete Contact ---");

        int id = readInt("Enter Contact ID to delete: ");

        if (manager.deleteContact(id)) {
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("Contact ID not found.");
        }
    }

    private static void searchById() {
        System.out.println("\n--- Search by Contact ID ---");

        int id = readInt("Enter Contact ID: ");
        Contact contact = manager.searchById(id);

        if (contact == null) {
            System.out.println("Contact not found.");
        } else {
            System.out.println("Contact found:");
            System.out.println("---------------------------------------------------------------");
            System.out.printf("%-6s %-20s %-15s %-30s%n",
                    "ID", "Name", "Phone", "Email");
            System.out.println("---------------------------------------------------------------");
            System.out.println(contact);
            System.out.println("---------------------------------------------------------------");
        }
    }

    private static void searchByName() {
        System.out.println("\n--- Search by Name ---");

        String name = readNonEmpty("Enter name: ");
        List<Contact> results = manager.searchByName(name);

        if (results.isEmpty()) {
            System.out.println("No contacts found with that name.");
            return;
        }

        System.out.println("Matching contacts:");
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-6s %-20s %-15s %-30s%n",
                "ID", "Name", "Phone", "Email");
        System.out.println("---------------------------------------------------------------");

        for (Contact contact : results) {
            System.out.println(contact);
        }

        System.out.println("---------------------------------------------------------------");
        System.out.println("Matches found: " + results.size());
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);

            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static String readNonEmpty(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("This field cannot be empty.");
        }
    }
}
