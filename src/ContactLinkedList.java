public class ContactLinkedList {
    private Node head;
    private int size;

    public ContactLinkedList() {
        head = null;
        size = 0;
    }

    // Adds a contact at the end of the linked list.
    public void add(Contact contact) {
        Node newNode = new Node(contact);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }

        size++;
    }

    // Deletes a contact by ID.
    // Returns true if deleted, false if the ID was not found.
    public boolean deleteById(int id) {
        if (head == null) {
            return false;
        }

        if (head.data.getId() == id) {
            head = head.next;
            size--;
            return true;
        }

        Node current = head;

        while (current.next != null) {
            if (current.next.data.getId() == id) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }

        return false;
    }

    // Recursive traversal required by the project.
    public void displayRecursive() {
        if (head == null) {
            System.out.println("No contacts available.");
            return;
        }

        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-6s %-20s %-15s %-30s%n",
                "ID", "Name", "Phone", "Email");
        System.out.println("---------------------------------------------------------------");

        displayRecursive(head);

        System.out.println("---------------------------------------------------------------");
    }

    private void displayRecursive(Node current) {
        if (current == null) {
            return; // Base case
        }

        System.out.println(current.data);
        displayRecursive(current.next); // Recursive call
    }

    // Another meaningful recursive function.
    public int countRecursive() {
        return countRecursive(head);
    }

    private int countRecursive(Node current) {
        if (current == null) {
            return 0; // Base case
        }

        return 1 + countRecursive(current.next);
    }

    public int size() {
        return size;
    }
}
