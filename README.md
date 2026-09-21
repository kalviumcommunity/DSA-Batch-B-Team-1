# Smart Contact Search System

A simple Java console-based contact manager demonstrating multiple Data Structures and Algorithms (DSA):

- Hashing using `HashMap`
- Binary Search
- Custom Singly Linked List
- Recursion

## Features

1. Add a contact
2. Delete a contact using Contact ID
3. Search by Contact ID
4. Search by Name
5. Display all contacts
6. Recursively count contacts
7. Handle duplicate IDs and missing contacts

Each contact contains:

- Contact ID
- Name
- Phone Number
- Email

## Project Structure

```text
SmartContactSearchSystem/
│
├── src/
│   ├── Contact.java
│   ├── Node.java
│   ├── ContactLinkedList.java
│   ├── ContactManager.java
│   └── Main.java
│
├── README.md
└── TEST_CASES.md
```

## DSA Design

### 1. Hashing (Member 3 - Custom Hash Table)

`ContactHashTable` (custom separate-chaining Hash Table) stores:

```text
Contact ID -> Contact
```

This provides average-case O(1) ID lookup using `index = Math.abs(id) % TABLE_SIZE` and separate chaining for collision handling.

### 2. Linked List

A custom singly linked list stores all contacts.

```text
Head
 |
 v
[101] -> [102] -> [103] -> null
```

The linked list is used for storage and recursive traversal.

### 3. Binary Search

A separate `ArrayList<Contact>` is maintained in sorted order by:

```text
Name -> Contact ID
```

Binary search is performed on this sorted collection for name lookup.

Duplicate names are supported. After finding one matching name using binary search, the program expands left and right to collect all matching contacts.

### 4. Recursion

The linked list has two recursive operations:

```java
displayRecursive()
countRecursive()
```

Base case:

```java
if (current == null) {
    return;
}
```

Recursive step:

```java
displayRecursive(current.next);
```

## Complexity Analysis

Let `n` be the number of contacts.

| Operation | Data Structure | Time Complexity |
|---|---|---:|
| Add contact | Linked List + HashMap + sorted ArrayList | O(n) |
| Delete by ID | Linked List + HashMap + sorted ArrayList | O(n) |
| Search by ID | HashMap | O(1) average |
| Search by name | Binary Search + output | O(log n + k) |
| Display contacts | Linked List + recursion | O(n) |
| Count contacts | Recursion | O(n) |

`k` = number of contacts having the searched name.

### Why is Add O(n)?

The linked list implementation adds at the end, so it traverses the list to find the last node. The sorted list insertion also shifts elements, which is O(n).

### Why is Delete O(n)?

The linked list may need to be traversed to find the node, and removing an item from the ArrayList may also require shifting elements.

### Why is ID Search O(1) average?

`ContactHashTable` uses a custom hash function (`Math.abs(id) % TABLE_SIZE`) to map the Contact ID to a specific bucket index, achieving O(1) average time complexity. Separate chaining handles any collisions.

### Why is Name Search O(log n)?

Binary search repeatedly divides the sorted search range approximately in half.

## How to Run

### Prerequisite

Install Java JDK 17 or newer.

Check Java:

```bash
java -version
```

Check the compiler:

```bash
javac -version
```

### Compile

Open a terminal in the project root:

```bash
javac -d out src/*.java
```

### Run

```bash
java -cp out Main
```

## Example

```text
1. Add Contact
2. Delete Contact
3. Search by Contact ID
4. Search by Name
5. Display Contacts
6. Count Contacts
7. Exit
```

Example contacts:

```text
101 Rahul 9876543210 rahul@example.com
102 Priya 9123456780 priya@example.com
103 Aman 9988776655 aman@example.com
```

## Edge Cases Covered

- Empty contact list
- Duplicate Contact ID
- Search for missing ID
- Delete missing ID
- One contact
- Duplicate names
- Multiple contacts with the same name
- Invalid numeric input
- Empty name/phone/email

## Viva Explanation

### Why use HashMap?

Because the requirement is fast lookup by Contact ID. HashMap provides average O(1) lookup.

### Why use Binary Search?

Because the contacts are maintained in sorted order by name. Binary search provides O(log n) searching.

### Why not binary-search the linked list?

Binary search needs efficient random access to the middle element. A linked list requires traversal to reach an index, so it is not suitable for efficient binary search.

### Why use a linked list?

The project specifically requires a linked-list-based contact storage structure. It also demonstrates node-based storage and traversal.

### Where is recursion used?

Recursive traversal is used to display all contacts and count the contacts.

### What is the base case?

When the current node becomes `null`, recursion stops.

## Important Design Note

There are intentionally three representations:

1. Linked List -> required contact storage
2. HashMap -> fast ID lookup
3. Sorted ArrayList -> binary-searchable name lookup

The `ContactManager` keeps all three synchronized whenever a contact is added or deleted.

Member 1 explanation (Hamshaverthini)- https://drive.google.com/file/d/1xuHKloB7A3PSUxYtJGp5O4KsfUdgzCYe/view?usp=sharing

Member 2
Linked List + Delete
Linked List, delete by ID  - (Explanation)[https://drive.google.com/file/d/1JtocCHYdIXiRw3saM6NrHuXilKlF1RdS/view?usp=sharing]
