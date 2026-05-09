[![Java CI](https://github.com/Ansuperope/Java-Address-Book/actions/workflows/java.yml/badge.svg)](https://github.com/Ansuperope/Java-Address-Book/actions/workflows/java.yml)
[![Documentation](https://img.shields.io/badge/Documentation-Doxygen-blue?logo=doxygen)](https://ansuperope.github.io/Java-Address-Book/)

Build a console-based **Address Book application** that lets users
manage different types of contacts.  
Users can add, edit, delete, search, and list contacts, as well as save
and load them from a file.

**Core Features**

**Object-oriented approach. AddressBook is the main class that contains
all the functions that will act on the contact objects, while the
contact class just contains basic functions that allow for adding,
searching, or getting features. A group struct is also used for groups
of contact objects.**

### **1. Contact Management**

- Add a new contact (choose type: **Person**, **Business**, **Vendor**,
  **Emergency**)\\

  - Use a giant vector of Contact objects to control the contact list

- Edit an existing contact

  - Every contact should have the following attributes:

    - int id (represents the order in the list)

    - string type (person, business, vendor, emergency)

    - string name (the name of the person, business, vendor, or
      emergency)

    - string city (see *Searching and Filtering* for why we would need
      this)

    - string email (the email of the person)

    - string phone (the phone number of the person)

- Delete a contact

  - Should call a helper function that displays all current contacts,
    and then request the user to input the ID of the contact they want
    to delete

  - The ID of everyone else in the contact list after the deleted
    contact should decrease by 1 (i.e., if you delete contact \#2, then
    contact \#3 becomes \#2, \#4 becomes \#3, etc).

- View a single contact’s details

  - Should call a helper function that displays all current contacts,
    and then request the user to input the ID of the contact they want
    to display

  - Should then call another helper function of the Contact class that
    displays the type, name, email, and phone of the contact chosen

- List all contacts

  - Should call a helper function that displays all current contacts

### **2. Searching and Filtering (I also did a bit of documentation to make the code look more presentable as if it were given to a customer)**

- Search contacts by name, email, or phone number

  - Use a for loop that goes through the entire contact list, calling a
    contact.getName(), contact.getEmail(), or contact.getPhone()
    function, looking for the requested name, email, or phone number

  - Might involve calling the size() function on the vector to determine
    the number of contacts currently in the contact vector

- Filter by contact type, city, or tag

  - Same process as searching for a name, email, or phone number

### **3. Grouping and Tagging**

- Assign contacts to groups (e.g., "Family", "Work", "Vendors")

  - Would involve possibly creating new vectors that add Contacts from
    the main vector into this secondary vector

- Add or remove tags (e.g., "Important", "Client", "Friend")

  - Each contact could have a bool variable for “Important,” “Client,”
    and “Friend,” that is turned on or off by a setter function, kind of
    like how we can “Favorite” contacts on our phone

  - This may also lead to three additional vectors that only contain
    contacts tagged as “Important,” “Client,” and “Friend.”

### **4. Import/Export**

- Save all contacts to a text or CSV file

  - Instead of having the print function for the entire contact list go
    to the console, have the print function open a text file, print to
    the text file, and then close the file

- Load contacts from a text or CSV file at startup

  - Will involve reading from a file (assuming the file is preloaded
    with contacts) and possibly a lot of cin \>\>

### **5. Reports**

- List contacts by type

  - Another print function that only prints contacts that are
    “Important,” “Client,” “Friend,” etc.

- Show contacts missing email or phone info

  - We should have multiple constructors that accept blanks for a
    missing email or phone number. These should pass a blank “” to these
    attributes of the Contact object

  - If we do another for loop that searches for the blank emails or
    phone info, it should detect blanks if contact.getEmail() or
    contact.getPhone() == “”;

- Display group summaries

  - This probably overlaps with the first bullet point of the *Report*
    category

Source.cpp

\#include "AddressBook.h"

\#include "UIController.h"

int main()

{

AddressBook book;

book.loadFromFile(); // optional auto-load

UIController::run(book);

book.saveToFile(); // auto-save on exit

return 0;

}

Contact.h

\#pragma once

\#include \<string\>

\#include \<vector\>

class Contact

{

private:

int id;

std::string type;

std::string name;

std::string city;

std::string email;

std::string phone;

std::vector\<std::string\> tags;

public:

Contact(int id, const std::string &type, const std::string &name,

const std::string &city, const std::string &email,

const std::string &phone,

const std::vector\<std::string\> &tags = {});

~Contact() = default;

// Getters

int getId() const;

std::string getType() const;

std::string getName() const;

std::string getCity() const;

std::string getEmail() const;

std::string getPhone() const;

const std::vector\<std::string\> &getTags() const;

// Setters

void setName(const std::string &name);

void setCity(const std::string &city);

void setEmail(const std::string &email);

void setPhone(const std::string &phone);

// Tag operations

void addTag(const std::string &tag);

void removeTag(const std::string &tag);

bool hasTag(const std::string &tag) const;

// String formatting for UI layer

std::string getTagString() const;

};

Contact.cpp

\#include "Contact.h"

\#include \<algorithm\>

Contact::Contact(int id, const std::string &type, const std::string
&name,

const std::string &city, const std::string &email,

const std::string &phone,

const std::vector\<std::string\> &tags)

: id(id), type(type), name(name), city(city), email(email),
phone(phone), tags(tags) {}

int Contact::getId() const { return id; }

std::string Contact::getType() const { return type; }

std::string Contact::getName() const { return name; }

std::string Contact::getCity() const { return city; }

std::string Contact::getEmail() const { return email; }

std::string Contact::getPhone() const { return phone; }

const std::vector\<std::string\> &Contact::getTags() const { return
tags; }

void Contact::setName(const std::string &n) { name = n; }

void Contact::setCity(const std::string &c) { city = c; }

void Contact::setEmail(const std::string &e) { email = e; }

void Contact::setPhone(const std::string &p) { phone = p; }

void Contact::addTag(const std::string &tag)

{

if (!hasTag(tag))

tags.push_back(tag);

}

void Contact::removeTag(const std::string &tag)

{

auto it = std::find(tags.begin(), tags.end(), tag);

if (it != tags.end())

tags.erase(it);

}

bool Contact::hasTag(const std::string &tag) const

{

return std::find(tags.begin(), tags.end(), tag) != tags.end();

}

std::string Contact::getTagString() const

{

std::string s;

for (size_t i = 0; i \< tags.size(); i++)

{

s += tags\[i\];

if (i \< tags.size() - 1)

s += ", ";

}

return s;

}

AddressBook.h

\#pragma once

\#include \<vector\>

\#include \<string\>

\#include "Contact.h"

class AddressBook

{

private:

std::vector\<Contact\> contacts;

struct Group

{

std::string name;

std::vector\<int\> memberIds;

};

std::vector\<Group\> groups;

public:

AddressBook() = default;

~AddressBook() = default;

// Contact management

bool addContact(const Contact &c);

bool removeContactById(int id);

Contact \*searchById(int id);

std::vector\<const Contact \*\> getAllContacts() const;

// Search / filter

std::vector\<const Contact \*\> searchByName(const std::string &name)
const;

std::vector\<const Contact \*\> searchByEmail(const std::string &email)
const;

std::vector\<const Contact \*\> searchByPhone(const std::string &phone)
const;

std::vector\<const Contact \*\> filterByType(const std::string &type)
const;

std::vector\<const Contact \*\> filterByCity(const std::string &city)
const;

std::vector\<const Contact \*\> filterByTag(const std::string &tag)
const;

// Groups

bool createGroup(const std::string &name);

bool deleteGroup(const std::string &name);

bool addContactToGroup(int contactId, const std::string &group);

bool removeContactFromGroup(int contactId, const std::string &group);

std::vector\<Group\> getGroups() const;

// File operations

bool saveToFile(const std::string &filename = "contact.txt") const;

bool loadFromFile(const std::string &filename = "contact.txt");

};

AddressBook.cpp

\#include "AddressBook.h"

\#include \<fstream\>

\#include \<sstream\>

\#include \<algorithm\>

bool AddressBook::addContact(const Contact &c)

{

for (const auto &existing : contacts)

if (existing.getId() == c.getId())

return false; // duplicate ID

contacts.push_back(c);

return true;

}

bool AddressBook::removeContactById(int id)

{

auto it = std::remove_if(contacts.begin(), contacts.end(),

\[id\](const Contact &c)

{ return c.getId() == id; });

if (it == contacts.end())

return false;

contacts.erase(it, contacts.end());

return true;

}

Contact \*AddressBook::searchById(int id)

{

for (auto &c : contacts)

if (c.getId() == id)

return &c;

return nullptr;

}

std::vector\<const Contact \*\> AddressBook::getAllContacts() const

{

std::vector\<const Contact \*\> out;

for (const auto &c : contacts)

out.push_back(&c);

return out;

}

// Searches

std::vector\<const Contact \*\> AddressBook::searchByName(const
std::string &name) const

{

std::vector\<const Contact \*\> out;

for (const auto &c : contacts)

if (c.getName() == name)

out.push_back(&c);

return out;

}

std::vector\<const Contact \*\> AddressBook::searchByEmail(const
std::string &email) const

{

std::vector\<const Contact \*\> out;

for (const auto &c : contacts)

if (c.getEmail() == email)

out.push_back(&c);

return out;

}

std::vector\<const Contact \*\> AddressBook::searchByPhone(const
std::string &phone) const

{

std::vector\<const Contact \*\> out;

for (const auto &c : contacts)

if (c.getPhone() == phone)

out.push_back(&c);

return out;

}

// Filtering

std::vector\<const Contact \*\> AddressBook::filterByType(const
std::string &type) const

{

std::vector\<const Contact \*\> out;

for (const auto &c : contacts)

if (c.getType() == type)

out.push_back(&c);

return out;

}

std::vector\<const Contact \*\> AddressBook::filterByCity(const
std::string &city) const

{

std::vector\<const Contact \*\> out;

for (const auto &c : contacts)

if (c.getCity() == city)

out.push_back(&c);

return out;

}

std::vector\<const Contact \*\> AddressBook::filterByTag(const
std::string &tag) const

{

std::vector\<const Contact \*\> out;

for (const auto &c : contacts)

if (c.hasTag(tag))

out.push_back(&c);

return out;

}

// Group operations

bool AddressBook::createGroup(const std::string &name)

{

for (const auto &g : groups)

if (g.name == name)

return false;

groups.push_back({name, {}});

return true;

}

bool AddressBook::deleteGroup(const std::string &name)

{

auto it = std::remove_if(groups.begin(), groups.end(),

\[&name\](const Group &g)

{ return g.name == name; });

if (it == groups.end())

return false;

groups.erase(it, groups.end());

return true;

}

bool AddressBook::addContactToGroup(int contactId, const std::string
&groupName)

{

for (auto &g : groups)

if (g.name == groupName)

{

g.memberIds.push_back(contactId);

return true;

}

return false;

}

bool AddressBook::removeContactFromGroup(int contactId, const
std::string &groupName)

{

for (auto &g : groups)

if (g.name == groupName)

{

auto it = std::remove(g.memberIds.begin(), g.memberIds.end(),
contactId);

if (it == g.memberIds.end())

return false;

g.memberIds.erase(it, g.memberIds.end());

return true;

}

return false;

}

std::vector\<AddressBook::Group\> AddressBook::getGroups() const

{

return groups;

}

// Save/load

bool AddressBook::saveToFile(const std::string &filename) const

{

std::ofstream out(filename);

if (!out)

return false;

for (const auto &c : contacts)

{

out \<\< c.getId() \<\< "\n"

\<\< c.getType() \<\< "\n"

\<\< c.getName() \<\< "\n"

\<\< c.getCity() \<\< "\n"

\<\< c.getEmail() \<\< "\n"

\<\< c.getPhone() \<\< "\n"

\<\< c.getTagString() \<\< "\n"

\<\< "----\n";

}

return true;

}

bool AddressBook::loadFromFile(const std::string &filename)

{

std::ifstream in(filename);

if (!in)

return false;

contacts.clear();

std::string line;

while (true)

{

std::string idStr, type, name, city, email, phone, tagLine;

if (!std::getline(in, idStr))

break;

std::getline(in, type);

std::getline(in, name);

std::getline(in, city);

std::getline(in, email);

std::getline(in, phone);

std::getline(in, tagLine);

std::getline(in, line); // separator

std::vector\<std::string\> tags;

std::stringstream ss(tagLine);

std::string tag;

while (std::getline(ss, tag, ','))

{

if (!tag.empty())

tags.push_back(tag);

}

int id = std::stoi(idStr);

contacts.emplace_back(id, type, name, city, email, phone, tags);

}

return true;

}

contact.txt

Formatter.h

\#pragma once

\#include \<string\>

\#include "Contact.h"

class Formatter

{

public:

static std::string formatSummary(const Contact &c);

static std::string formatDetails(const Contact &c);

};

Formatter.cpp

\#include "Formatter.h"

std::string Formatter::formatSummary(const Contact &c)

{

return "\[" + std::to_string(c.getId()) + "\] " + c.getName() + " (" +
c.getType() + ")";

}

std::string Formatter::formatDetails(const Contact &c)

{

std::string out;

out += "────────────────────────\n";

out += "ID: " + std::to_string(c.getId()) + "\n";

out += "Type: " + c.getType() + "\n";

out += "Name: " + c.getName() + "\n";

out += "City: " + c.getCity() + "\n";

out += "Email: " + c.getEmail() + "\n";

out += "Phone: " + c.getPhone() + "\n";

out += "Tags: " + c.getTagString() + "\n";

out += "────────────────────────\n";

return out;

}

UIController.h

\#pragma once

\#include "AddressBook.h"

class UIController

{

public:

static void run(AddressBook &book);

private:

// Main menus

static void showMainMenu();

static void manageGroups(AddressBook &book);

static void manageTags(AddressBook &book);

// Contact operations

static void addContactUI(AddressBook &book);

static void editContactUI(AddressBook &book);

static void deleteContactUI(AddressBook &book);

static void viewAllUI(AddressBook &book);

static void searchUI(AddressBook &book);

static void filterUI(AddressBook &book);

// Helpers

static int getIntInput(const std::string &prompt);

static std::string getStringInput(const std::string &prompt);

};

UIController.cpp

\#include "UIController.h"

\#include "Formatter.h"

\#include \<iostream\>

\#include \<limits\>

using namespace std;

void UIController::run(AddressBook &book)

{

int choice = -1;

while (choice != 0)

{

showMainMenu();

choice = getIntInput("Choose an option: ");

switch (choice)

{

case 1:

addContactUI(book);

break;

case 2:

editContactUI(book);

break;

case 3:

deleteContactUI(book);

break;

case 4:

viewAllUI(book);

break;

case 5:

searchUI(book);

break;

case 6:

filterUI(book);

break;

case 7:

manageGroups(book);

break;

case 8:

book.saveToFile();

cout \<\< "Saved.\n";

break;

case 9:

book.loadFromFile();

cout \<\< "Loaded.\n";

break;

case 10:

manageTags(book);

break;

case 0:

cout \<\< "Exiting...\n";

break;

default:

cout \<\< "Invalid choice.\n";

}

}

}

void UIController::showMainMenu()

{

cout \<\< "\n===== ADDRESS BOOK MENU =====\n";

cout \<\< "1. Add New Contact\n";

cout \<\< "2. Edit Contact\n";

cout \<\< "3. Delete Contact\n";

cout \<\< "4. View All Contacts\n";

cout \<\< "5. Search Contacts\n";

cout \<\< "6. Filter Contacts\n";

cout \<\< "7. Manage Groups\n";

cout \<\< "8. Save to File\n";

cout \<\< "9. Load from File\n";

cout \<\< "10. Manage Tags\n";

cout \<\< "0. Exit\n";

}

int UIController::getIntInput(const string &prompt)

{

cout \<\< prompt;

int value;

while (!(cin \>\> value))

{

cin.clear();

cin.ignore(numeric_limits\<streamsize\>::max(), '\n');

cout \<\< "Invalid input. Try again: ";

}

cin.ignore(numeric_limits\<streamsize\>::max(), '\n');

return value;

}

string UIController::getStringInput(const string &prompt)

{

cout \<\< prompt;

string input;

getline(cin, input);

return input;

}

void UIController::addContactUI(AddressBook &book)

{

int id = getIntInput("ID: ");

string type = getStringInput("Type: ");

string name = getStringInput("Name: ");

string city = getStringInput("City: ");

string email = getStringInput("Email: ");

string phone = getStringInput("Phone: ");

vector\<string\> tags;

char addTagChoice;

cout \<\< "Add tag(s)? (y/n): ";

cin \>\> addTagChoice;

cin.ignore(numeric_limits\<streamsize\>::max(), '\n');

while (addTagChoice == 'y' \|\| addTagChoice == 'Y')

{

tags.push_back(getStringInput("Enter tag: "));

cout \<\< "Add another? (y/n): ";

cin \>\> addTagChoice;

cin.ignore(numeric_limits\<streamsize\>::max(), '\n');

}

bool ok = book.addContact(Contact(id, type, name, city, email, phone,
tags));

if (ok)

cout \<\< "Contact added.\n";

else

cout \<\< "Error: ID already exists.\n";

}

void UIController::editContactUI(AddressBook &book)

{

int id = getIntInput("Enter ID to edit: ");

Contact \*c = book.searchById(id);

if (!c)

{

cout \<\< "Contact not found.\n";

return;

}

cout \<\< "\nEditing Contact:\n";

cout \<\< Formatter::formatDetails(\*c);

string name = getStringInput("New name (leave blank to keep): ");

if (!name.empty())

c-\>setName(name);

string city = getStringInput("New city (leave blank to keep): ");

if (!city.empty())

c-\>setCity(city);

string email = getStringInput("New email (leave blank to keep): ");

if (!email.empty())

c-\>setEmail(email);

string phone = getStringInput("New phone (leave blank to keep): ");

if (!phone.empty())

c-\>setPhone(phone);

cout \<\< "Updated.\n";

}

void UIController::deleteContactUI(AddressBook &book)

{

int id = getIntInput("Enter ID to delete: ");

if (book.removeContactById(id))

cout \<\< "Deleted.\n";

else

cout \<\< "Contact not found.\n";

}

void UIController::viewAllUI(AddressBook &book)

{

auto list = book.getAllContacts();

if (list.empty())

{

cout \<\< "No contacts.\n";

return;

}

for (const auto \*c : list)

cout \<\< Formatter::formatDetails(\*c);

}

void UIController::searchUI(AddressBook &book)

{

cout \<\< "\nSearch by:\n1 = Name\n2 = Email\n3 = Phone\n";

int choice = getIntInput("Choose: ");

string query = getStringInput("Enter search value: ");

vector\<const Contact \*\> results;

if (choice == 1)

results = book.searchByName(query);

else if (choice == 2)

results = book.searchByEmail(query);

else if (choice == 3)

results = book.searchByPhone(query);

else

{

cout \<\< "Invalid.\n";

return;

}

if (results.empty())

{

cout \<\< "No results.\n";

return;

}

for (const Contact \*c : results)

cout \<\< Formatter::formatDetails(\*c);

}

void UIController::filterUI(AddressBook &book)

{

cout \<\< "\nFilter by:\n1 = Type\n2 = City\n3 = Tag\n";

int choice = getIntInput("Choose: ");

string query = getStringInput("Enter filter value: ");

vector\<const Contact \*\> results;

if (choice == 1)

results = book.filterByType(query);

else if (choice == 2)

results = book.filterByCity(query);

else if (choice == 3)

results = book.filterByTag(query);

else

{

cout \<\< "Invalid.\n";

return;

}

if (results.empty())

{

cout \<\< "No results.\n";

return;

}

for (const Contact \*c : results)

cout \<\< Formatter::formatDetails(\*c);

}

void UIController::manageGroups(AddressBook &book)

{

cout \<\< "\n--- GROUP MENU ---\n";

cout \<\< "1. Create Group\n";

cout \<\< "2. Add Contact to Group\n";

cout \<\< "3. Remove Contact from Group\n";

cout \<\< "4. Delete Group\n";

cout \<\< "5. View Groups\n";

int choice = getIntInput("Choose: ");

switch (choice)

{

case 1:

{

string name = getStringInput("Group name: ");

if (book.createGroup(name))

cout \<\< "Created.\n";

else

cout \<\< "Group exists.\n";

break;

}

case 2:

{

int id = getIntInput("Contact ID: ");

string g = getStringInput("Group: ");

if (book.addContactToGroup(id, g))

cout \<\< "Added.\n";

else

cout \<\< "Error.\n";

break;

}

case 3:

{

int id = getIntInput("Contact ID: ");

string g = getStringInput("Group: ");

if (book.removeContactFromGroup(id, g))

cout \<\< "Removed.\n";

else

cout \<\< "Error.\n";

break;

}

case 4:

{

string g = getStringInput("Group name: ");

if (book.deleteGroup(g))

cout \<\< "Deleted.\n";

else

cout \<\< "Not found.\n";

break;

}

case 5:

{

auto groups = book.getGroups();

if (groups.empty())

{

cout \<\< "No groups.\n";

break;

}

for (const auto &g : groups)

{

cout \<\< "Group: " \<\< g.name \<\< "\nMembers:\n";

for (int id : g.memberIds)

cout \<\< " - ID " \<\< id \<\< "\n";

cout \<\< "--------------------\n";

}

break;

}

default:

cout \<\< "Invalid.\n";

}

}

void UIController::manageTags(AddressBook &book)

{

int id = getIntInput("ID for tag management: ");

Contact \*c = book.searchById(id);

if (!c)

{

cout \<\< "Not found.\n";

return;

}

cout \<\< "\n--- TAG MENU ---\n";

cout \<\< "1. Add Tag\n";

cout \<\< "2. Remove Tag\n";

cout \<\< "3. View Tags\n";

int choice = getIntInput("Choose: ");

switch (choice)

{

case 1:

{

string t = getStringInput("Tag to add: ");

c-\>addTag(t);

cout \<\< "Added.\n";

break;

}

case 2:

{

string t = getStringInput("Tag to remove: ");

c-\>removeTag(t);

cout \<\< "Removed.\n";

break;

}

case 3:

{

cout \<\< "Tags: " \<\< c-\>getTagString() \<\< "\n";

break;

}

default:

cout \<\< "Invalid.\n";

}

}
