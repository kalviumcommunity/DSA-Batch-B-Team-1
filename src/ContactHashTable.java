/**
 * Member 3 - Hashing Implementation
 *
 * ContactHashTable provides a manual Hash Table implementation for fast Contact lookup by ID.
 * It uses Separate Chaining (a singly linked list per bucket) to handle hash collisions.
 *
 * Data Structure Design:
 * - Buckets: Array of HashNode references.
 * - Hash Function: index = Math.abs(id) % TABLE_SIZE.
 * - Collision Handling: Separate chaining using internal HashNode linked list.
 */
public class ContactHashTable {
    private static final int DEFAULT_TABLE_SIZE = 10;

    /**
     * Internal node structure representing a node in a bucket's collision chain.
     */
    private static class HashNode {
        Contact contact;
        HashNode next;

        public HashNode(Contact contact) {
            this.contact = contact;
            this.next = null;
        }
    }

    private final HashNode[] buckets;
    private final int tableSize;
    private int size;

    /**
     * Constructs a ContactHashTable with default table size (10).
     */
    public ContactHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    /**
     * Constructs a ContactHashTable with a custom table size.
     * @param tableSize Number of buckets in the hash table.
     */
    public ContactHashTable(int tableSize) {
        this.tableSize = tableSize > 0 ? tableSize : DEFAULT_TABLE_SIZE;
        this.buckets = new HashNode[this.tableSize];
        this.size = 0;
    }

    /**
     * Hash Function: Maps a Contact ID to a bucket index [0, tableSize - 1].
     *
     * Why this hash function?
     * The modulo operator (%) distributes Contact IDs uniformly across tableSize buckets.
     * Math.abs() ensures negative IDs (if provided) map to non-negative array indices.
     *
     * @param id The Contact ID.
     * @return The bucket index for the given ID.
     */
    private int hash(int id) {
        return Math.abs(id) % tableSize;
    }

    /**
     * Inserts a Contact into the Hash Table.
     *
     * Collision Handling:
     * If multiple contacts map to the same bucket index (e.g., 101 % 10 = 1 and 111 % 10 = 1),
     * the new contact is inserted at the head of that bucket's linked chain.
     *
     * Duplicate Handling:
     * Scans the bucket chain for an existing contact with the same ID. If found, returns false
     * without inserting a duplicate.
     *
     * @param contact The Contact object to insert.
     * @return true if inserted successfully; false if a duplicate ID exists.
     */
    public boolean insert(Contact contact) {
        if (contact == null) {
            return false;
        }

        int index = hash(contact.getId());
        HashNode current = buckets[index];

        // Check for duplicate ID in the collision chain
        while (current != null) {
            if (current.contact.getId() == contact.getId()) {
                return false; // Duplicate ID found
            }
            current = current.next;
        }

        // Insert at the head of the bucket list (O(1) insertion)
        HashNode newNode = new HashNode(contact);
        newNode.next = buckets[index];
        buckets[index] = newNode;
        size++;

        return true;
    }

    /**
     * Searches for a Contact by Contact ID.
     *
     * Average Time Complexity: O(1)
     * Worst Case Time Complexity: O(n) (when all n keys collide into a single bucket)
     *
     * @param id The Contact ID to search for.
     * @return The matching Contact object if found; null if not found.
     */
    public Contact searchById(int id) {
        int index = hash(id);
        HashNode current = buckets[index];

        // Traverse collision chain in bucket[index]
        while (current != null) {
            if (current.contact.getId() == id) {
                return current.contact; // Found
            }
            current = current.next;
        }

        return null; // Missing Contact ID
    }

    /**
     * Removes a Contact from the Hash Table by ID.
     *
     * Keeps the Hash Table synchronized when contacts are deleted from the system.
     * Unlinks the corresponding HashNode from the collision chain.
     *
     * @param id The Contact ID to remove.
     * @return true if found and removed; false if missing.
     */
    public boolean remove(int id) {
        int index = hash(id);
        HashNode current = buckets[index];
        HashNode prev = null;

        while (current != null) {
            if (current.contact.getId() == id) {
                if (prev == null) {
                    // Node to remove is head of the bucket chain
                    buckets[index] = current.next;
                } else {
                    // Node to remove is in middle/end of the chain
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }

        return false; // Contact ID not found
    }

    /**
     * Checks if a Contact ID exists in the Hash Table.
     *
     * @param id The Contact ID.
     * @return true if present; false otherwise.
     */
    public boolean containsKey(int id) {
        return searchById(id) != null;
    }

    /**
     * Returns the total number of contacts stored in the Hash Table.
     *
     * @return Current size of the Hash Table.
     */
    public int size() {
        return size;
    }

    /**
     * Returns the table size (number of buckets).
     *
     * @return Table size.
     */
    public int getTableSize() {
        return tableSize;
    }
}
