# Store all contacts
contacts = []


# Contact structure
class Contact:
    def __init__(self, contact_id, name, phone, email):
        self.contact_id = contact_id
        self.name = name
        self.phone = phone
        self.email = email

    # Display contact details
    def display(self):
        print(
            f"{self.contact_id} | "
            f"{self.name} | "
            f"{self.phone} | "
            f"{self.email}"
        )


# Check whether Contact ID already exists
def id_exists(contact_id):
    for contact in contacts:
        if contact.contact_id == contact_id:
            return True
    return False


# Add a new contact
def add_contact():
    # Contact ID validation
    try:
        contact_id = int(input("Enter Contact ID: "))
    except ValueError:
        print("Contact ID must be a number!")
        return

    # Check duplicate ID
    if id_exists(contact_id):
        print("Contact ID already exists!")
        return

    # Name validation
    name = input("Enter Name: ").strip()

    if not name:
        print("Name cannot be empty!")
        return

    # Phone validation
    phone = input("Enter Phone Number: ").strip()

    if not phone.isdigit() or len(phone) != 10:
        print("Phone number must contain exactly 10 digits!")
        return

    # Email validation
    email = input("Enter Email: ").strip()

    if "@" not in email or "." not in email:
        print("Enter a valid email address!")
        return

    # Create contact object
    contact = Contact(
        contact_id,
        name,
        phone,
        email
    )

    # Store contact
    contacts.append(contact)

    print("\nContact added successfully!")
    contact.display()

    return contact