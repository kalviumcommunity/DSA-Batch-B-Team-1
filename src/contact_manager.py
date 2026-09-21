"""Smart Contact Search System implemented with Python data structures."""

from dataclasses import dataclass


@dataclass(frozen=True)
class Contact:
    id: int
    name: str
    phone: str
    email: str

    def __post_init__(self):
        if not isinstance(self.id, int):
            raise TypeError("id must be an integer")
        for field_name in ("name", "phone", "email"):
            if not isinstance(getattr(self, field_name), str):
                raise TypeError(f"{field_name} must be a string")
            if not getattr(self, field_name).strip():
                raise ValueError(f"{field_name} cannot be empty")


@dataclass
class Node:
    data: Contact
    next: "Node | None" = None


class ContactLinkedList:
    def __init__(self):
        self.head = None
        self._size = 0

    def add(self, contact):
        new_node = Node(contact)
        if self.head is None:
            self.head = new_node
        else:
            current = self.head
            while current.next is not None:
                current = current.next
            current.next = new_node
        self._size += 1

    def delete_by_id(self, contact_id):
        if self.head is None:
            return False
        if self.head.data.id == contact_id:
            self.head = self.head.next
            self._size -= 1
            return True

        current = self.head
        while current.next is not None:
            if current.next.data.id == contact_id:
                current.next = current.next.next
                self._size -= 1
                return True
            current = current.next
        return False

    # Base case: no node means there are no contacts left to collect.
    # Recursive step: collect this node, then continue with the next node.
    def _to_list_recursive(self, current):
        if current is None:
            return []
        return [current.data] + self._to_list_recursive(current.next)

    def display_recursive(self):
        """Return contacts in linked-list order using recursive traversal."""
        # Start at the first node; the helper follows every next reference.
        return self._to_list_recursive(self.head)

    def count_recursive(self):
        # An empty list has zero contacts.
        if self.head is None:
            return 0

        def count(current):
            # Base case: the end of the list contributes zero.
            if current is None:
                return 0
            # Count this node, then recursively count the remaining nodes.
            return 1 + count(current.next)

        return count(self.head)

    def __len__(self):
        # Return the maintained size without traversing the list.
        return self._size


class ContactManager:
    def __init__(self):
        self.contact_list = ContactLinkedList()
        self._contacts_by_id = {}
        self._sorted_contacts = []

    def add_contact(self, contact):
        if contact.id in self._contacts_by_id:
            return False
        self.contact_list.add(contact)
        self._contacts_by_id[contact.id] = contact
        self._sorted_contacts.append(contact)
        self._sorted_contacts.sort(key=lambda item: (item.name.casefold(), item.id))
        return True

    def delete_contact(self, contact_id):
        contact = self._contacts_by_id.get(contact_id)
        if contact is None:
            return False
        self.contact_list.delete_by_id(contact_id)
        del self._contacts_by_id[contact_id]
        self._sorted_contacts.remove(contact)
        return True

    def search_by_id(self, contact_id):
        return self._contacts_by_id.get(contact_id)

    def search_by_name(self, name):
        target = name.casefold()
        low, high = 0, len(self._sorted_contacts) - 1
        match_index = -1
        while low <= high:
            middle = (low + high) // 2
            current_name = self._sorted_contacts[middle].name.casefold()
            if current_name == target:
                match_index = middle
                break
            if current_name < target:
                low = middle + 1
            else:
                high = middle - 1

        if match_index == -1:
            return []
        first = match_index
        while first > 0 and self._sorted_contacts[first - 1].name.casefold() == target:
            first -= 1
        last = match_index
        while last + 1 < len(self._sorted_contacts) and self._sorted_contacts[last + 1].name.casefold() == target:
            last += 1
        return self._sorted_contacts[first:last + 1]

    def display_contacts(self):
        return self.contact_list.display_recursive()

    def count_contacts(self):
        return self.contact_list.count_recursive()
