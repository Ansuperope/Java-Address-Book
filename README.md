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

\#include "Contact.h"

\#include "AddressBook.h"

\#include \<iostream\>

\#include \<string\>

\#include \<limits\>

using namespace std;

void showMainMenu();

void showGroupMenu();

void showTagMenu(AddressBook& myBook);

int main() {

AddressBook myBook(vector\<Contact\>{});

myBook.loadFromFile();

int choice = -1;

while (choice != 0) {

showMainMenu();

cin \>\> choice;

cin.ignore();

switch (choice) {

case 1: { // Add contact

myBook.addContactInteractive();

break;

}

case 2: { // Edit contact

cout \<\< "Enter the ID of the contact to edit: ";

int id;

cin \>\> id;

cin.ignore();

Contact\* contact = myBook.searchById(id);

if (contact) {

cout \<\< "Editing Contact \[" \<\< contact-\>getName() \<\< "\]\n";

cout \<\< "Enter new name (or press Enter to keep '" \<\<
contact-\>getName() \<\< "'): ";

string name;

getline(cin, name);

if (!name.empty()) contact-\>setName(name);

cout \<\< "Enter new city (or press Enter to keep '" \<\<
contact-\>getCity() \<\< "'): ";

string city;

getline(cin, city);

if (!city.empty()) contact-\>setCity(city);

cout \<\< "Enter new email (or press Enter to keep '" \<\<
contact-\>getEmail() \<\< "'): ";

string email;

getline(cin, email);

if (!email.empty()) contact-\>setEmail(email);

cout \<\< "Enter new phone (or press Enter to keep '" \<\<
contact-\>getPhone() \<\< "'): ";

string phone;

getline(cin, phone);

if (!phone.empty()) contact-\>setPhone(phone);

cout \<\< "Contact updated successfully!\n";

}

else {

cout \<\< "No contact found with ID " \<\< id \<\< ".\n";

}

break;

}

case 3: { // Delete contact

myBook.deleteContactInteractive();

break;

}

case 4: { // View all

cout \<\< "\n--- CONTACT LIST ---\n";

myBook.displayAllContacts();

break;

}

case 5: { // Search

cout \<\< "\nSearch by:\n1. Name\n2. Email\n3. Phone\nChoose: ";

int s; cin \>\> s;

cin.ignore();

string query;

cout \<\< "Enter search term: ";

getline(cin, query);

vector\<const Contact\*\> results;

if (s == 1) results = myBook.searchByName(query);

else if (s == 2) results = myBook.searchByEmail(query);

else if (s == 3) results = myBook.searchByPhone(query);

if (results.empty()) cout \<\< "No results found.\n";

else {

cout \<\< results.size() \<\< " result(s):\n";

for (const auto\* c : results) c-\>printDetails();

}

break;

}

case 6: { // Filter

cout \<\< "\nFilter by:\n1. Type\n2. City\nChoose: ";

int f; cin \>\> f;

cin.ignore();

string query;

cout \<\< "Enter filter value: ";

getline(cin, query);

vector\<const Contact\*\> results;

if (f == 1) results = myBook.filterByType(query);

else if (f == 2) results = myBook.filterByCity(query);

if (results.empty()) cout \<\< "No results found.\n";

else {

cout \<\< results.size() \<\< " contact(s) found:\n";

for (const auto\* c : results) c-\>printDetails();

}

break;

}

case 7: { // Groups

int gChoice = -1;

while (gChoice != 0) {

showGroupMenu();

cin \>\> gChoice;

cin.ignore();

switch (gChoice) {

case 1: {

cout \<\< "Enter new group name: ";

string name; getline(cin, name);

myBook.createGroup(name);

break;

}

case 2: {

cout \<\< "Enter contact ID: ";

int id; cin \>\> id;

cin.ignore();

cout \<\< "Enter group name: ";

string gname; getline(cin, gname);

myBook.addContactToGroup(id, gname);

break;

}

case 3: {

cout \<\< "Enter contact ID to remove: ";

int id; cin \>\> id;

cin.ignore();

cout \<\< "Enter group name: ";

string gname; getline(cin, gname);

myBook.removeContactFromGroup(id, gname);

break;

}

case 4: {

cout \<\< "Enter group name to delete: ";

string gname; getline(cin, gname);

myBook.deleteGroup(gname);

break;

}

case 5: {

myBook.listGroupsandContacts();

break;

}

case 0: break;

default: cout \<\< "Invalid option.\n";

}

}

break;

}

case 8: { // Save

myBook.saveToFile();

break;

}

case 9: { // Load

myBook.loadFromFile();

break;

}

case 10: { // Manage Tags

showTagMenu(myBook);

break;

}

case 0:

myBook.saveToFile();

cout \<\< "Goodbye! All changes saved.\n";

break;

default:

cout \<\< "Invalid choice, try again.\n";

}

}

return 0;

}

void showMainMenu() {

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

cout \<\< "Choose an option: ";

}

void showGroupMenu() {

cout \<\< "\n--- GROUP MENU ---\n";

cout \<\< "1. Create Group\n";

cout \<\< "2. Add Contact to Group\n";

cout \<\< "3. Remove Contact from Group\n";

cout \<\< "4. Delete Group\n";

cout \<\< "5. List Groups and Members\n";

cout \<\< "0. Return to Main Menu\n";

cout \<\< "Choose an option: ";

}

void showTagMenu(AddressBook& myBook) {

cout \<\< "Enter contact ID to manage tags: ";

int id;

cin \>\> id;

cin.ignore();

Contact\* contact = myBook.searchById(id);

if (!contact) {

cout \<\< "No contact found with that ID.\n";

return;

}

int tagChoice = -1;

while (tagChoice != 0) {

cout \<\< "\n--- TAG MENU ---\n";

cout \<\< "Managing tags for: " \<\< contact-\>getName() \<\< "\n";

cout \<\< "1. Add Tag\n";

cout \<\< "2. Remove Tag\n";

cout \<\< "3. View Tags\n";

cout \<\< "0. Return to Main Menu\n";

cout \<\< "Choose: ";

cin \>\> tagChoice;

cin.ignore();

switch (tagChoice) {

case 1: {

cout \<\< "Enter tag to add: ";

string tag;

getline(cin, tag);

contact-\>addTag(tag);

cout \<\< "Tag added successfully!\n";

break;

}

case 2: {

cout \<\< "Enter tag to remove: ";

string tag;

getline(cin, tag);

contact-\>removeTag(tag);

cout \<\< "Tag removed successfully!\n";

break;

}

case 3: {

cout \<\< "Current tags:\n";

contact-\>displayTags();

break;

}

case 0:

cout \<\< "Returning to main menu...\n";

break;

default:

cout \<\< "Invalid choice. Try again.\n";

}

}

}

Contact.h

\#pragma once

\#include \<iostream\>

\#include \<string\>

\#include \<vector\>

\#include \<algorithm\>

\#include \<limits\>

using namespace std;

class Contact {

private:

int id;

string type;

string name;

string city;

string email;

string phone;

vector\<string\> tags;

public:

// Constructor

Contact(int id, string type, string name, string city, string email,
string phone, vector\<string\> tags);

Contact(int id, string type, string name, string city, string email,
string phone);

// Destructor

virtual ~Contact();

// Getter functions. These will get a certain feature from the program,
often to use in another function or if it is necessary for the user to
see what a specific feature is.

int getId() const;

string getType() const;

string getName() const;

string getCity() const;

string getEmail() const;

string getPhone() const;

// Setter functions. These set the attributes of the contact to what the
user chooses to enter.

void setId(int newId);

void setName(string n);

void setCity(string c);

void setEmail(string e);

void setPhone(string p);

// tagging

void addTag(const string& tag);

void removeTag(const string& tag);

bool hasTag(const string& tag) const;

void displayTags() const;

// Prints the contact’s most important features in one line, making it
quick for the user to see.

void printSummary() const; // one line: \[id\] name (type)

// Prints the contact’s features, but in a more organized manner with
documentation. It allows for the user to see exactly what the contact
has, and what it is.

void printDetails() const; // type, name, email, phone

vector\<string\> getTags() const { return tags; }

};

Contact.cpp

\#include "Contact.h"

Contact::Contact(int id, string type, string name, string city, string
email, string phone, vector\<string\> tags)

: id(id), type(type), name(name), city(city), email(email),
phone(phone), tags(tags)

{

}

Contact::Contact(int id, string type, string name, string city, string
email, string phone)

: id(id), type(type), name(name), city(city), email(email), phone(phone)
{

}

Contact::~Contact() {}

// Getters

int Contact::getId() const { return id; }

string Contact::getType() const { return type; }

string Contact::getName() const { return name; }

string Contact::getCity() const { return city; }

string Contact::getEmail() const { return email; }

string Contact::getPhone() const { return phone; }

// Setters

void Contact::setId(int newId) { id = newId; }

void Contact::setName(string n) { name = n; }

void Contact::setCity(string c) { city = c; }

void Contact::setEmail(string e) { email = e; }

void Contact::setPhone(string p) { phone = p; }

void Contact::displayTags() const

{

cout \<\< "Tags: ";

for (const auto& tag : tags) {

cout \<\< tag \<\< " ";

}

cout \<\< endl;

}

void Contact::addTag(const string& tag)

{

if (!hasTag(tag)) {

tags.push_back(tag);

}

}

void Contact::removeTag(const string& tag)

{

auto it = find(tags.begin(), tags.end(), tag);

if (it == tags.end()) return;

tags.erase(it);

}

bool Contact::hasTag(const string& tag) const

{

return find(tags.begin(), tags.end(), tag) != tags.end();

}

// Prints quick summary of key contact features

void Contact::printSummary() const {

cout \<\< "\[" \<\< id \<\< "\] " \<\< name \<\< " (" \<\< type \<\< ")"
\<\< endl;

}

// Prints important details about the contact in a neatly formatted
manner.

void Contact::printDetails() const {

cout \<\< "ID: " \<\< id \<\< endl;

cout \<\< "Type: " \<\< type \<\< endl;

cout \<\< "Name: " \<\< name \<\< endl;

cout \<\< "City: " \<\< city \<\< endl;

cout \<\< "Email: " \<\< email \<\< endl;

cout \<\< "Phone: " \<\< phone \<\< endl;

displayTags();

cout \<\< endl;

cout \<\< "------------------------" \<\< endl;

}

AddressBook.h

\#pragma once

\#include \<iostream\>

\#include \<string\>

\#include \<vector\>

\#include \<sstream\>

\#include "Contact.h"

using namespace std;

class AddressBook

{

private:

vector\<Contact\> contacts; // Vector of contact objects

struct Group

{

string name;

vector\<int\> memberIds;

};

vector\<Group\> groups;

public:

// Constructors and destructors

AddressBook(vector\<Contact\> list);

AddressBook(vector\<Group\> groups);

~AddressBook();

// Contact Management

void addContact(const Contact& associate);

void displayAllContacts() const;

void addContactInteractive(); // Adds a new contact to the vector of
contacts based on the user input.

void deleteContactInteractive(); //

bool removeContactById(int id); // Removes a contact based on the id
that the user searches for.

// group management (simple)

void createGroup(const string& name);

void deleteGroup(const string& groupName);

void listGroupsandContacts() const;

void addContactToGroup(int contactId, const string& groupName);

void removeContactFromGroup(int contactId, const string& groupName);

// Searching for contacts based on their features such as name, email,
and phone number.

Contact\* searchById(int id);

vector\<const Contact\*\> searchByName(const string& name) const;

vector\<const Contact\*\> searchByEmail(const string& email) const;

vector\<const Contact\*\> searchByPhone(const string& phone) const;

// Filtering contacts based on other features such as type and city

vector\<const Contact\*\> filterByType(const string& type) const;

vector\<const Contact\*\> filterByCity(const string& city) const;

// Import / Export to text file

void saveToFile() const;

void loadFromFile();

//Listing contacts by type

void listByType() const;

void showMissingInfo() const;

void displayGroupSummaries() const;

};

AddressBook.cpp

\#include "AddressBook.h"

\#include \<fstream\>

AddressBook::AddressBook(vector\<Contact\> list)

{

contacts = list;

}

AddressBook::AddressBook(vector\<Group\> groups)

{

this-\>groups = groups;

}

AddressBook::~AddressBook()

{

}

// Adds a basic contact.

void AddressBook::addContact(const Contact& associate)

{

contacts.push_back(associate);

}

// Adds a new contact to the vector of the contacts based on the user
input.

void AddressBook::addContactInteractive()

{

vector\<string\> tags;

cout \<\< "Adding new contact interactively" \<\< endl;

int id;

string type, name, city, email, phone, tag;

cout \<\< "ID: "; cin \>\> id;
cin.ignore(numeric_limits\<streamsize\>::max(), '\n');

cout \<\< "Type: "; getline(cin, type);

cout \<\< "Name: "; getline(cin, name);

cout \<\< "City: "; getline(cin, city);

cout \<\< "Email: "; getline(cin, email);

cout \<\< "Phone: "; getline(cin, phone);

cout \<\< "Woud you like to add a tag? (y/n): ";

char t;

cin \>\> t;

cin.ignore(numeric_limits\<streamsize\>::max(), '\n');

if (t == 'y' \|\| t == 'Y')

{

do

{

cout \<\< "Choose tag (Client, Friend, Important): ";

getline(cin, tag);

cout \<\< "Tag '" \<\< tag \<\< "' added." \<\< endl;

tags.push_back(tag);

cout \<\< "Would you like to add another tag? (y/n): ";

cin \>\> t;

cin.ignore(numeric_limits\<streamsize\>::max(), '\n');

} while (t == 'y' \|\| t == 'Y');

}

Contact c(id, type, name, city, email, phone, tags);

addContact(c);

}

// Displays all contacts in the address book.

void AddressBook::displayAllContacts() const

{

for (const auto& contact : contacts)

{

contact.printDetails();

}

}

// Removes a contact based on the user-inputted id. The contact is
erased if the user-inputted id is found within the vector of contact
objects and returns true, and changes nothing if there is no contact
with the id while returning false.

bool AddressBook::removeContactById(int id)

{

for (size_t i = 0; i \< contacts.size(); ++i) {

if (contacts\[i\].getId() == id) {

contacts.erase(contacts.begin() + static_cast\<long\>(i));

return true;

}

}

return false;

}

// Interactive function that calls displayAllContacts() then allows for
the user to input a basic id for a contact to delete, calls
removeContactById(), then reports the result to the user. The function
validates the id, letting the user know that the input is invalid if it
does not fit within the constraints of the program. The function will
let the user know if a contact was deleted, and if not, the user will be
notified that there was no contact with the inputted id.

void AddressBook::deleteContactInteractive()

{

cout \<\< "Current contacts:" \<\< endl;

displayAllContacts();

if (contacts.empty()) return;

cout \<\< "Enter the ID of the contact to delete: ";

int id;

if (!(cin \>\> id)) {

cin.clear();

cin.ignore(numeric_limits\<streamsize\>::max(), '\n');

cout \<\< "Invalid input." \<\< endl;

return;

}

if (removeContactById(id)) {

cout \<\< "Contact deleted." \<\< endl;

}

else {

cout \<\< "No contact with that ID." \<\< endl;

}

}

// Creates a group of contacts.

void AddressBook::createGroup(const string& name)

{

Group newGroup;

newGroup.name = name;

groups.push_back(newGroup);

}

// Lists all groups of contacts.

void AddressBook::listGroupsandContacts() const

{

for (const auto& g : groups)

{

cout \<\< "Group: " \<\< g.name \<\< endl;

cout \<\< "Members:" \<\< endl;

for (int memberId : g.memberIds)

{

auto it = find_if(contacts.begin(), contacts.end(), \[memberId\](const
Contact& c) {

return c.getId() == memberId;

});

if (it != contacts.end())

{

cout \<\< " - " \<\< it-\>getName() \<\< " (ID: " \<\< it-\>getId() \<\<
")" \<\< endl;

}

else

{

cout \<\< " - Contact ID " \<\< memberId \<\< " not found." \<\< endl;

}

}

cout \<\< "------------------------" \<\< endl;

}

}

//Add Contact to Group

void AddressBook::addContactToGroup(int contactId, const string&
groupName)

{

for (auto& g : groups)

{

if (g.name == groupName)

{

// Check if contact exists

auto it = find_if(contacts.begin(), contacts.end(), \[contactId\](const
Contact& c) {

return c.getId() == contactId;

});

if (it != contacts.end())

{

g.memberIds.push_back(contactId);

cout \<\< "Contact ID " \<\< contactId \<\< " added to group '" \<\<
groupName \<\< "'." \<\< endl;

}

else

{

cout \<\< "Contact ID " \<\< contactId \<\< " not found." \<\< endl;

}

return;

}

}

cout \<\< "Group '" \<\< groupName \<\< "' not found." \<\< endl;

}

//Remove contact from Group

void AddressBook::removeContactFromGroup(int contactId, const string&
groupName)

{

for (auto& g : groups)

{

if (g.name == groupName)

{

auto it = find(g.memberIds.begin(), g.memberIds.end(), contactId);

if (it != g.memberIds.end())

{

g.memberIds.erase(it);

cout \<\< "Contact ID " \<\< contactId \<\< " removed from group '" \<\<
groupName \<\< "'." \<\< endl;

}

else

{

cout \<\< "Contact ID " \<\< contactId \<\< " not found in group '" \<\<
groupName \<\< "'." \<\< endl;

}

return;

}

}

cout \<\< "Group '" \<\< groupName \<\< "' not found." \<\< endl;

}

// Delete a group as a whole

void AddressBook::deleteGroup(const string& groupName)

{

auto it = find_if(groups.begin(), groups.end(), \[&groupName\](const
Group& g) {

return g.name == groupName;

});

if (it != groups.end())

{

groups.erase(it);

cout \<\< "Group '" \<\< groupName \<\< "' deleted." \<\< endl;

}

else

{

cout \<\< "Group '" \<\< groupName \<\< "' not found." \<\< endl;

}

}

// Searching functions that browse for a specific feature of a contact,
given a vector of contacts. These functions will often only find one
instance of the feature, unless there is a special case.

Contact\* AddressBook::searchById(int id)

{

for (size_t i = 0; i \< contacts.size(); ++i)

{

if (contacts\[i\].getId() == id)

{

return &contacts\[i\];

}

}

return nullptr;

}

// Searches for a contact based on the user-inputted name.

vector\<const Contact\*\> AddressBook::searchByName(const string& name)
const {

vector\<const Contact\*\> results;

for (size_t i = 0; i \< contacts.size(); ++i) {

if (contacts\[i\].getName() == name) {

results.push_back(&contacts\[i\]);

}

}

return results;

}

// Searches for a contact based on the user-inputted email.

vector\<const Contact\*\> AddressBook::searchByEmail(const string&
email) const {

vector\<const Contact\*\> results;

for (size_t i = 0; i \< contacts.size(); ++i) {

if (contacts\[i\].getEmail() == email) {

results.push_back(&contacts\[i\]);

}

}

return results;

}

// Searches for a contact based on the user-inputted phone number.

vector\<const Contact\*\> AddressBook::searchByPhone(const string&
phone) const {

vector\<const Contact\*\> results;

for (size_t i = 0; i \< contacts.size(); ++i) {

if (contacts\[i\].getPhone() == phone) {

results.push_back(&contacts\[i\]);

}

}

return results;

}

// Similar to searching functions, but these functions filter. Instead
of being cases where one instance is found, there are often multiple, as
the filtering is done by very broad criteria.

// Filters the contacts based on the type, such as work or family. Since
there could be multiple, all of the contacts with the specified type are
returned.

vector\<const Contact\*\> AddressBook::filterByType(const string& type)
const {

vector\<const Contact\*\> results;

for (size_t i = 0; i \< contacts.size(); ++i) {

if (contacts\[i\].getType() == type) {

results.push_back(&contacts\[i\]);

}

}

return results;

}

// Filters the contacts based on the city, such as Los Angeles or
Chicago. Since there could be multiple, all of the contacts with the
specified city are returned.

vector\<const Contact\*\> AddressBook::filterByCity(const string& city)
const {

vector\<const Contact\*\> results;

for (size_t i = 0; i \< contacts.size(); ++i) {

if (contacts\[i\].getCity() == city) {

results.push_back(&contacts\[i\]);

}

}

return results;

}

// Save contacts directly to "contact.txt"

void AddressBook::saveToFile() const {

// Open the output file stream to write to "contact.txt"

ofstream outFile("contact.txt");

// Check if the file failed to open

if (!outFile) {

cerr \<\< "Error: could not open contact.txt for writing.\n";

return; // Exit early if unable to write

}

// Loop through every contact in the AddressBook

for (const auto& c : contacts) {

// Write each field on a new line in a fixed order

outFile \<\< c.getId() \<\< '\n'

\<\< c.getType() \<\< '\n'

\<\< c.getName() \<\< '\n'

\<\< c.getCity() \<\< '\n'

\<\< c.getEmail() \<\< '\n'

\<\< c.getPhone() \<\< '\n';

// Retrieve all tags for this contact (e.g. "Friend", "Client")

vector\<string\> tags = c.getTags();

// Write tags separated by commas (e.g. "Friend,VIP,Important")

for (size_t i = 0; i \< tags.size(); ++i) {

outFile \<\< tags\[i\];

if (i \< tags.size() - 1)

outFile \<\< ","; // Avoid trailing comma

}

// Add a newline and a separator to mark the end of this contact

outFile \<\< '\n' \<\< "----" \<\< '\n';

}

// Close the output file stream

outFile.close();

// Notify user that saving completed successfully

cout \<\< "Contacts saved to contact.txt successfully!\n";

}

// Load contacts directly from "contact.txt"

void AddressBook::loadFromFile() {

// Open the input file stream to read from "contact.txt"

ifstream inFile("contact.txt");

// Check if file could not be opened (e.g. it doesn't exist yet)

if (!inFile) {

cerr \<\< "No contact.txt file found. Starting fresh.\n";

return; // Exit early if no saved data exists

}

// Clear any existing contacts to prevent duplicates

contacts.clear();

string line; // Temporary string used for reading lines from file

// Read contacts one at a time until we reach end of file

while (true) {

string idStr, type, name, city, email, phone, tagLine;

// Read each contact's 7 lines in the same order they were written

if (!getline(inFile, idStr)) break;

if (!getline(inFile, type)) break;

if (!getline(inFile, name)) break;

if (!getline(inFile, city)) break;

if (!getline(inFile, email)) break;

if (!getline(inFile, phone)) break;

if (!getline(inFile, tagLine)) break;

// Split tag line (comma-separated) into a vector of strings

vector\<string\> tags;

stringstream ss(tagLine);

string tag;

while (getline(ss, tag, ',')) {

if (!tag.empty())

tags.push_back(tag);

}

// Skip the "----" separator line between contacts

getline(inFile, line);

// Convert ID from string to integer and create a new Contact object

int id = 0;

stringstream idStream(idStr);

idStream \>\> id;

Contact c(id, type, name, city, email, phone, tags);

// Add the newly created contact to the AddressBook vector

contacts.push_back(c);

}

// Close the input file stream

inFile.close();

// Notify user that contacts were loaded successfully

cout \<\< "Contacts loaded from contact.txt successfully!\n";

}

//List contacts by their type

void AddressBook::listByType() const {

if (contacts.empty()) {

cout \<\< "No contacts to list.\n";

return;

}

cout \<\< "\n===== Contacts by Type =====\n";

vector\<string\> printedTypes;

for (const auto& c : contacts) {

string currentType = c.getType();

// Checking to see if this type was already printed

if (find(printedTypes.begin(), printedTypes.end(), currentType) ==
printedTypes.end()) {

cout \<\< "\n--- " \<\< currentType \<\< " ---\n";

// Print all contacts that have this type

for (const auto& other : contacts) {

if (other.getType() == currentType)

other.printSummary();

}

printedTypes.push_back(currentType);

}

}

cout \<\< endl;

}

//Showing missing info contacts missing email of phone info

void AddressBook::showMissingInfo() const {

cout \<\< "\n===== Contacts Missing Info =====\n";

bool found = false;

for (const auto& c : contacts) {

if (c.getEmail().empty() \|\| c.getPhone().empty()) {

c.printSummary();

if (c.getEmail().empty()) cout \<\< " -\> Missing Email\n";

if (c.getPhone().empty()) cout \<\< " -\> Missing Phone\n";

found = true;

}

}

if (!found) {

cout \<\< "All contacts have complete information." \<\< endl;

}

cout \<\< endl;

}

//DIsplays the number of members in each group and their names

void AddressBook::displayGroupSummaries() const {

cout \<\< "\n===== Group Summaries =====\n";

if (groups.empty()) {

cout \<\< "No groups have been created.\n";

return;

}

for (const auto& g : groups) {

cout \<\< "Group: " \<\< g.name

\<\< " (Total members: " \<\< g.memberIds.size() \<\< ")\n";

for (int memberId : g.memberIds) {

auto it = find_if(contacts.begin(), contacts.end(),

\[memberId\](const Contact& c) { return c.getId() == memberId; });

if (it != contacts.end()) {

cout \<\< " - " \<\< it-\>getName() \<\< " (" \<\< it-\>getType() \<\<
")\n";

}

else {

cout \<\< " - Contact ID " \<\< memberId \<\< " not found.\n";

}

}

cout \<\< endl;

}

cout \<\< endl;

}

contact.txt

123

Family

Bob

New York

shfs@gmail.com

949-999-9999

Friend,Client

----

(WIP) Formatter.h

\#pragma once

\#include \<string\>

\#include "Contact.h"

class Formatter

{

public:

static std::string formatSummary(const Contact &c);

static std::string formatDetails(const Contact &c);

};

(WIP) Formatter.cpp

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

(WIP) UIController.h

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

(WIP) UIController.cpp

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
