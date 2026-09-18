import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class ContactManager {
    private final ContactLinkedList contactList;
    private final HashMap<Integer, Contact> contactMap;

    // Always kept sorted by name, then ID.
    private final ArrayList<Contact> sortedContacts;

    private final Comparator<Contact> nameComparator =
            Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER)
                      .thenComparingInt(Contact::getId);

    public ContactManager() {
        contactList = new ContactLinkedList();
        contactMap = new HashMap<>();
        sortedContacts = new ArrayList<>();
    }

    public boolean addContact(Contact contact) {
        if (contactMap.containsKey(contact.getId())) {
            return false;
        }

        contactList.add(contact);
        contactMap.put(contact.getId(), contact);

        // Insert into the sorted collection.
        int position = findInsertionPosition(contact);
        sortedContacts.add(position, contact);

        return true;
    }

    public boolean deleteContact(int id) {
        Contact contact = contactMap.get(id);

        if (contact == null) {
            return false;
        }

        contactList.deleteById(id);
        contactMap.remove(id);

        // Remove the same object from the sorted collection.
        sortedContacts.remove(contact);

        return true;
    }

    public Contact searchById(int id) {
        return contactMap.get(id);
    }

    /*
     * Finds all contacts with the requested name using binary search.
     *
     * First binary-search for one matching contact, then expand left/right
     * because duplicate names are allowed.
     */
    public List<Contact> searchByName(String name) {
        ArrayList<Contact> results = new ArrayList<>();

        if (sortedContacts.isEmpty()) {
            return results;
        }

        int index = binarySearchName(name);

        if (index == -1) {
            return results;
        }

        int left = index;
        while (left >= 0 &&
                sortedContacts.get(left).getName().equalsIgnoreCase(name)) {
            left--;
        }

        int right = index;
        while (right < sortedContacts.size() &&
                sortedContacts.get(right).getName().equalsIgnoreCase(name)) {
            right++;
        }

        for (int i = left + 1; i < right; i++) {
            results.add(sortedContacts.get(i));
        }

        return results;
    }

    // Binary search by name.
    private int binarySearchName(String target) {
        int low = 0;
        int high = sortedContacts.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            String currentName = sortedContacts.get(mid).getName();

            int comparison = currentName.compareToIgnoreCase(target);

            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    private int findInsertionPosition(Contact contact) {
        int low = 0;
        int high = sortedContacts.size();

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (nameComparator.compare(sortedContacts.get(mid), contact) < 0) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }

    public void displayContacts() {
        contactList.displayRecursive();
    }

    public int countContacts() {
        return contactList.countRecursive();
    }
}
