/**
 * @file AddressBook.java
 */
package project.include;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class AddressBook {

    private ArrayList<Contact> contacts = new ArrayList<>(); // ArrayList of contact objects

    private class Group {

        public String name;
        public ArrayList<Integer> memberIds;

        public Group(String name, ArrayList<Integer> memberIds) {
            this.name = name;
            this.memberIds = memberIds;
        }
    } // END class Group

    private ArrayList<Group> groups = new ArrayList<>();

    /**
     * Default constructor
     */
    public AddressBook() {
        this.contacts = new ArrayList<>();
        this.groups = new ArrayList<>();
    } // END AddressBook default

    /**
     * Constructor list
     */
    public AddressBook(List<Contact> list) {
        this.contacts = new ArrayList<>(list);
    } // END AddressBook list

    /**
     * Constructor group
     */
    public AddressBook(ArrayList<Group> groups) {
        this.groups = groups;
    } // END AddressBook group

    /**
     * Getter function. Gets all contacts
     */
    public ArrayList<Contact> getAllContacts() {
        return contacts;
    } // END getAllContacts

    /**
     * Getter function. Gets all groups
     */
    public ArrayList<Group> getGroups() {
        return groups;
    } // END getGroups

    /**
     * Adds a basic contact
     *
     * @param associate person to add
     * @return boolean: true = added, false = not added
     */
    public boolean addContact(Contact associate) {

        // Checksj if contact already added, return false if so
        for (Contact c : contacts) {
            // duplicate ID
            if (c.getId() == associate.getId()) {
                return false;
            } // END if
        } // END for (Contact c : contacts)

        // Can add id
        contacts.add(associate);
        return true;
    } // END addContact

    /**
     * Adds a new contact to the vector of the contacts based on the user input.
     */
    public void addContactInteractive() {
        Scanner sc = new Scanner(System.in);

        ArrayList<String> tags = new ArrayList<>();

        System.out.println("Adding new contact interactively");

        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Type: ");
        String type = sc.nextLine();

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("City: ");
        String city = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Phone: ");
        String phone = sc.nextLine();

        System.out.print("Would you like to add a tag? (y/n): ");
        char t = sc.nextLine().charAt(0);

        if (t == 'y' || t == 'Y') {
            do {
                System.out.print("Choose tag: ");
                String tag = sc.nextLine();

                tags.add(tag);

                System.out.print("Add another? (y/n): ");
                t = sc.nextLine().charAt(0);

            } while (t == 'y' || t == 'Y');
        } // END if (t == 'y' || t == 'Y') 

        contacts.add(new Contact(id, type, name, city, email, phone, tags));
    } // END addContactInteractive

    /**
     * Displays all contacts in the address book
     */
    public void displayAllContacts() {
        for (Contact c : contacts) {
            c.printDetails();
        } // END for 
    } // END displayAllContacts

    /**
     * Removes a contact based on the user-inputted id. The contact is erased if
     * the user-inputted id is found within the vector of contact objects and
     * returns true, and changes nothing if there is no contact with the id
     * while returning false.
     *
     * @param id contact to delete
     */
    public boolean removeContactById(int id) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() == id) {
                contacts.remove(i);
                return true;
            } // END if (contacts.get(i).getId() == id)
        } // END (int i = 0; i < contacts.size(); i++)

        // Unable to remove contact
        return false;
    } // END removeContactById

    /**
     * Interactive function that calls displayAllContacts() then allows for the
     * user to input a basic id for a contact to delete, calls
     * removeContactById(), then reports the result to the user. The function
     * validates the id, letting the user know that the input is invalid if it
     * does not fit within the constraints of the program. The function will let
     * the user know if a contact was deleted, and if not, the user will be
     * notified that there was no contact with the inputted id.
     */
    public void deleteContactInteractive() {

        // Display all contacts
        System.out.println("Current contacts:");
        displayAllContacts();

        // Exit if no contacts
        if (contacts.isEmpty()) {
            return;
        } // END if

        // Get id if has contacts
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the ID of the contact to delete: ");
        int id;

        // Validate integer input
        if (!sc.hasNextInt()) {
            System.out.println("Invalid input.");
            sc.nextLine(); // clear invalid input
            return;
        } // END if

        id = sc.nextInt();

        // Remove contact if found
        if (removeContactById(id)) {
            System.out.println("Contact deleted.");
        } // end if
        // Cannot find contact, output error
        else {
            System.out.println("No contact with that ID.");
        } // END else
    } // END deleteContactInteractive

    /**
     * *****************************************************************
     * GROUP RELATED FUNCTIONS
     *****************************************************************
     */
    /**
     * Creates a group of contacts.
     */
    public boolean createGroup(String name) {
        // Check if group already exists
        for (Group group : groups) {
            if (group.name.equalsIgnoreCase(name)) {
                return false;
            }
        } // END for

        // Create empty member list
        ArrayList<Integer> memberIds = new ArrayList<>();

        // Create and add group
        Group newGroup = new Group(name, memberIds);
        groups.add(newGroup);

        return true;
    } // END createGroup

    /**
     * Add Contact to Group
     */
    public boolean addContactToGroup(int contactId, String groupName) {
        for (Group g : groups) {
            if (g.name.equals(groupName)) {

                // Check if contact exists
                Contact foundContact = null;

                for (Contact c : contacts) {
                    if (c.getId() == contactId) {
                        foundContact = c;
                        break;
                    }
                } // END for

                if (foundContact != null) {

                    // Prevent duplicates
                    if (!g.memberIds.contains(contactId)) {

                        g.memberIds.add(contactId);

                        System.out.println("Contact ID " + contactId
                                + " added to group '" + groupName + "'.");

                    } else {
                        System.out.println("Contact already in group.");
                    }

                } // END if (foundContact != null)
                else {
                    System.out.println("Contact ID " + contactId + " not found.");
                }

                return true;
            } // END if (g.name.equals(groupName))
        } // END for

        System.out.println("Group '" + groupName + "' not found.");
        return false;
    } // END addContactToGroup

    /**
     * Remove contact from Group
     */
    public boolean removeContactFromGroup(int contactId, String groupName) {

        for (Group g : groups) {

            // Look for group
            if (g.name.equals(groupName)) {

                // Found id in group
                if (g.memberIds.contains(contactId)) {

                    g.memberIds.remove(Integer.valueOf(contactId));

                    System.out.println("Contact ID " + contactId
                            + " removed from group '" + groupName + "'.");

                } // END if (g.memberIds.contains(contactId))
                // Could not find id in group
                else {

                    System.out.println("Contact ID " + contactId
                            + " not found in group '" + groupName + "'.");
                } // END else

                // Exit function, return true
                return true;
            } // END if (g.name.equals(groupName))
        } // END for (Group g : groups)

        // If not found, return false
        System.out.println("Group '" + groupName + "' not found.");
        return false;
    } // END removeContactFromGroup

    /**
     * Delete a group as a whole
     */
    public boolean deleteGroup(String groupName) {

        // Search through groups to find group name
        for (int i = 0; i < groups.size(); i++) {
            // Found group name
            if (groups.get(i).name.equals(groupName)) {

                // Remove group
                groups.remove(i);
                System.out.println("Group '" + groupName + "' deleted.");

                // Exit funciton, return true
                return true;
            } // END if
        } // END for

        // Could not find group name, return false
        System.out.println("Group '" + groupName + "' not found.");
        return false;
    } // END deleteGroup

    /**
     * Displays the number of members in each group and their names
     */
    public void displayGroupSummaries() {
        System.out.println("\n===== Group Summaries =====");

        // No groups, exit
        if (groups.isEmpty()) {
            System.out.println("No groups have been created.");
            return;
        } // END if (groups.isEmpty())

        // Iteratre through all groups
        for (Group g : groups) {

            System.out.println("Group: " + g.name
                    + " (Total members: " + g.memberIds.size() + ")");

            // Print all members in the group
            for (int memberId : g.memberIds) {

                Contact foundContact = null;

                // Get member id to print
                for (Contact c : contacts) {
                    // Check if id exists
                    if (c.getId() == memberId) {
                        foundContact = c;
                        break;
                    }
                } // END for (Contact c : contacts)

                // Found id, print id
                if (foundContact != null) {
                    System.out.println(" - " + foundContact.getName()
                            + " (" + foundContact.getType() + ")");
                } // END if (foundContact != null)
                // Id does not exist, print error
                else {
                    System.out.println(" - Contact ID " + memberId
                            + " not found.");
                } // END else
            } // END for (int memberId : g.memberIds)

            System.out.println();
        } // END for (Group g : groups)

        System.out.println();
    } // END displayGroupSummaries

    /**
     * Searching functions that browse for a specific feature of a contact,
     * given a vector of contacts. These functions will often only find one
     * instance of the feature, unless there is a special case.
     */
    public Contact searchById(int id) {
        for (Contact c : contacts) {
            if (c.getId() == id) {
                return c;
            } // END if
        } // END for

        // Cannot find
        return null;
    } // END searchById

    /**
     * Searches for a contact based on the user-inputted name.
     */
    public ArrayList<Contact> searchByName(String name) {
        ArrayList<Contact> results = new ArrayList<>();

        for (Contact c : contacts) {
            if (c.getName().equals(name)) {
                results.add(c);
            } // END if
        } // END for
        return results;
    } // END searchByName

    /**
     * Searches for a contact based on the user-inputted email.
     */
    public ArrayList<Contact> searchByEmail(String email) {
        ArrayList<Contact> results = new ArrayList<>();

        for (Contact c : contacts) {
            if (c.getEmail().equals(email)) {
                results.add(c);
            } // END if
        } // END for
        return results;
    } // END searchByEmail

    /**
     * Searches for a contact based on the user-inputted phone number.
     */
    public ArrayList<Contact> searchByPhone(String phone) {
        ArrayList<Contact> results = new ArrayList<>();

        for (Contact c : contacts) {
            if (c.getPhone().equals(phone)) {
                results.add(c);
            } // END if
        } // END for
        return results;
    } // END searchByPhone

    /**
     * *****************************************************************
     * Similar to searching functions, but these functions filter. Instead of
     * being cases where one instance is found, there are often multiple, as the
     * filtering is done by very broad criteria.
     * ****************************************************************
     */
    /**
     * Filters the contacts based on the type, such as work or family. Since
     * there could be multiple, all of the contacts with the specified type are
     * returned.
     */
    public ArrayList<Contact> filterByType(String type) {
        ArrayList<Contact> results = new ArrayList<>();

        for (Contact c : contacts) {
            if (c.getType().equals(type)) {
                results.add(c);
            } // END if
        } // END for

        return results;
    } // END filterByType

    /**
     * Filters the contacts based on the city, such as Los Angeles or Chicago.
     * Since there could be multiple, all of the contacts with the specified
     * city are returned.
     */
    public ArrayList<Contact> filterByCity(String city) {
        ArrayList<Contact> results = new ArrayList<>();

        for (Contact c : contacts) {
            if (c.getCity().equals(city)) {
                results.add(c);
            } // END if
        } // END for

        return results;
    } // END filterByCity

    /**
     * Filters the contacts based on the tag, such as friends or family. Since
     * there could be multiple, all of the contacts with the specified tags are
     * returned.
     */
    public ArrayList<Contact> filterByTag(String tag) {
        ArrayList<Contact> results = new ArrayList<>();

        for (Contact c : contacts) {
            if (c.getTags().contains(tag)) {
                results.add(c);
            } // END if
        } // END for

        return results;
    } // END filterByCity

    /**
     * Save contacts directly to "contact.txt"
     */
    public void saveToFile() throws IOException {

        BufferedWriter out = new BufferedWriter(new FileWriter("contact.txt"));

        // Loop through every contact in the AddressBook
        for (Contact c : contacts) {
            // Write each field on a new line in a fixed order
            out.write(String.valueOf(c.getId()));
            out.newLine();
            out.write(c.getType());
            out.newLine();
            out.write(c.getName());
            out.newLine();
            out.write(c.getCity());
            out.newLine();
            out.write(c.getEmail());
            out.newLine();
            out.write(c.getPhone());
            out.newLine();

            // Retrieve all tags for this contact (e.g. "Friend", "Client")
            ArrayList<String> tags = c.getTags();

            for (int i = 0; i < tags.size(); i++) {
                out.write(tags.get(i));

                // Avoid trailing comma
                if (i < tags.size() - 1) {
                    out.write(",");
                }
            } // END for (int i = 0; i < tags.size(); i++)

            // Add a newline and a separator to mark the end of this contact
            out.newLine();
            out.write("----");
            out.newLine();
        } // END for for (Contact c : contacts)

        // Close the output file stream
        out.close();
    } // END saveToFile

    /**
     * Load contacts directly from "contact.txt"
     */
    public void loadFromFile() throws IOException {

        // Open the input file stream to read from "contact.txt"
        BufferedReader in = new BufferedReader(new FileReader("contact.txt"));

        // Clear any existing contacts to prevent duplicates
        contacts.clear();

        // Read contacts one at a time until we reach end of file
        while (true) {

            // Read each contact's 7 lines in the same order they were written
            String idStr = in.readLine();
            if (idStr == null) {
                break;
            }

            String type = in.readLine();
            String name = in.readLine();
            String city = in.readLine();
            String email = in.readLine();
            String phone = in.readLine();
            String tagLine = in.readLine();
            in.readLine(); // "----"

            ArrayList<String> tags = new ArrayList<>();

            // Split tag line (comma-separated) into a vector of strings
            if (tagLine != null && !tagLine.isEmpty()) {
                for (String t : tagLine.split(",")) {
                    tags.add(t);
                } // END for
            } // END if

            // Convert ID from string to integer and create a new Contact object
            int id = Integer.parseInt(idStr);

            // Add the newly created contact to the AddressBook vector
            contacts.add(new Contact(id, type, name, city, email, phone, tags));

        } // END while (true)

        // Close the input file stream
        in.close();

        // Notify user that contacts were loaded successfully
        System.out.println("Contacts loaded from contact.txt successfully!");

    } // END loadFromFile

    /**
     * List contacts by their type
     */
    public void listByType() {

        // Contacts are empty, exit
        if (contacts.isEmpty()) {
            System.out.println("No contacts to list.");
            return;
        } // END if (contacts.isEmpty())

        System.out.println("\n===== Contacts by Type =====");
        ArrayList<String> printedTypes = new ArrayList<>();

        // Iterate through all contacts
        for (Contact c : contacts) {

            String currentType = c.getType();

            // Check if this type was already printed
            if (!printedTypes.contains(currentType)) {

                System.out.println("\n--- " + currentType + " ---");

                // Print all contacts with this type
                for (Contact other : contacts) {

                    if (other.getType().equals(currentType)) {
                        other.printSummary();
                    } // END (Contact other : contacts)
                } // END for (Contact other : contacts)

                printedTypes.add(currentType);
            } // END if (!printedTypes.contains(currentType))
        } // END for (Contact c : contacts)

        System.out.println();
    } // END listByType

    /**
     * Showing missing info contacts missing email or phone info
     */
    public void showMissingInfo() {

        System.out.println("\n===== Contacts Missing Info =====");
        boolean found = false;

        // Iterate through all contacts
        for (Contact c : contacts) {

            // Check if email or phone number is there
            if (c.getEmail().isEmpty() || c.getPhone().isEmpty()) {
                // Print contact info
                c.printSummary();

                // Print if email is missing
                if (c.getEmail().isEmpty()) {
                    System.out.println("  -> Missing Email");
                } // END if (c.getEmail().isEmpty())

                // Print if phone number is missing
                if (c.getPhone().isEmpty()) {
                    System.out.println("  -> Missing Phone");
                } // END if (c.getPhone().isEmpty())

                found = true;
            } // END if (c.getEmail().isEmpty() || c.getPhone().isEmpty())
        } // END for (Contact c : contacts)

        // Checked all contacts, no one is missing anything
        if (!found) {
            System.out.println("All contacts have complete information.");
        } // END if (!found)

        System.out.println();
    } // END showMissingInfo

} // END class AddressBook
