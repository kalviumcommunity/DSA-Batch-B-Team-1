import unittest

from src.contact_manager import Contact, ContactLinkedList, ContactManager


class ContactManagerTestCase(unittest.TestCase):
    def setUp(self):
        self.manager = ContactManager()
        self.rahul = Contact(101, "Rahul", "9876543210", "rahul@example.com")
        self.priya = Contact(102, "Priya", "9123456780", "priya@example.com")
        self.second_rahul = Contact(104, "rahul", "9000000000", "rahul2@example.com")

    def test_count_recursive_empty_and_populated(self):
        self.assertEqual(self.manager.count_contacts(), 0)
        self.manager.add_contact(self.rahul)
        self.manager.add_contact(self.priya)
        self.assertEqual(self.manager.count_contacts(), 2)

    def test_recursive_traversal_preserves_insertion_order(self):
        self.manager.add_contact(self.rahul)
        self.manager.add_contact(self.priya)
        self.assertEqual(self.manager.display_contacts(), [self.rahul, self.priya])

    def test_recursive_list_count_matches_after_delete(self):
        linked_list = ContactLinkedList()
        linked_list.add(self.rahul)
        linked_list.add(self.priya)
        self.assertTrue(linked_list.delete_by_id(101))
        self.assertEqual(linked_list.count_recursive(), 1)
        self.assertEqual(linked_list.display_recursive(), [self.priya])

    def test_duplicate_ids_are_rejected(self):
        self.assertTrue(self.manager.add_contact(self.rahul))
        self.assertFalse(self.manager.add_contact(Contact(101, "Other", "111", "other@example.com")))
        self.assertEqual(self.manager.count_contacts(), 1)

    def test_name_search_is_case_insensitive_and_supports_duplicates(self):
        for contact in (self.rahul, self.priya, self.second_rahul):
            self.assertTrue(self.manager.add_contact(contact))
        self.assertEqual(self.manager.search_by_name("RAHUL"), [self.rahul, self.second_rahul])

    def test_delete_updates_recursive_count_and_id_search(self):
        self.manager.add_contact(self.rahul)
        self.manager.add_contact(self.priya)
        self.assertTrue(self.manager.delete_contact(101))
        self.assertIsNone(self.manager.search_by_id(101))
        self.assertEqual(self.manager.count_contacts(), 1)
        self.assertFalse(self.manager.delete_contact(999))

    def test_contact_validation_rejects_empty_fields(self):
        with self.assertRaises(ValueError):
            Contact(1, "", "123", "person@example.com")


if __name__ == "__main__":
    unittest.main()
