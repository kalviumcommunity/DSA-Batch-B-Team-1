# Test Cases - Smart Contact Search System

## Automated Python Tests

Run all automated tests from the project root:

```bash
python3 -m unittest discover -s tests -v
```

The suite verifies recursive traversal and counting, empty lists, deletion,
duplicate IDs, duplicate names, ID/name search, and contact validation.

The test implementation is in `tests/test_contact_manager.py`.

## TC01 - Add Contact

Input:

```text
1
101
Rahul
9876543210
rahul@example.com
```

Expected:

```text
Contact added successfully.
```

---

## TC02 - Add Multiple Contacts

Add:

```text
101 Rahul 9876543210 rahul@example.com
102 Priya 9123456780 priya@example.com
103 Aman 9988776655 aman@example.com
```

Expected:

All three contacts are stored.

---

## TC03 - Duplicate Contact ID

Add Contact ID `101` when ID `101` already exists.

Expected:

```text
Error: Contact ID already exists.
```

The second contact must not be added.

---

## TC04 - Search Existing ID

Search:

```text
3
102
```

Expected:

Priya's complete contact information is displayed.

---

## TC05 - Search Missing ID

Search an ID such as:

```text
999
```

Expected:

```text
Contact not found.
```

---

## TC06 - Search Existing Name

Search:

```text
Rahul
```

Expected:

All contacts named Rahul are displayed.

---

## TC07 - Duplicate Names

Add:

```text
104 Rahul 9000000000 rahul2@example.com
```

Search:

```text
Rahul
```

Expected:

Both Rahul contacts are displayed.

---

## TC08 - Delete Existing Contact

Delete:

```text
102
```

Expected:

```text
Contact deleted successfully.
```

Searching for `102` afterward should return:

```text
Contact not found.
```

---

## TC09 - Delete Missing Contact

Delete:

```text
999
```

Expected:

```text
Contact ID not found.
```

---

## TC10 - Display Contacts

Select:

```text
5
```

Expected:

All current contacts are displayed through recursive linked-list traversal.

---

## TC11 - Count Contacts

Select:

```text
6
```

Expected:

The program displays the number of contacts using recursive counting.

---

## TC12 - Empty List

Start the program and select:

```text
5
```

Expected:

```text
No contacts available.
```

---

## TC13 - Invalid Menu Input

Enter:

```text
99
```

Expected:

```text
Invalid choice. Please enter 1-7.
```

---

## TC14 - Invalid Numeric Input

For Contact ID, enter:

```text
abc
```

Expected:

```text
Please enter a valid integer.
```

The program should continue asking for an integer.

---

## TC15 - Empty Field

For name, press Enter without entering a value.

Expected:

```text
This field cannot be empty.
```
