/**
 * @file UIController.java
 */
package project.include;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import project.include.AddressBook;

public class UIController {

    // getting input
    private final static Scanner sc = new Scanner(System.in);

    /**
     * main program
     */
    public static void run(AddressBook book) {
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
                try {
                    book.saveToFile();
                    System.out.println("Saved.");
                } 
                catch (IOException e) {
                    System.out.println("Save failed: " + e.getMessage());
                }
                break;
            case 9:
                try {
                    book.loadFromFile();
                    System.out.println("Loaded.");
                } 
                catch (IOException e) {
                    System.out.println("Load failed: " + e.getMessage());
                }
                break;
            case 10:
                manageTags(book);
                break;
            case 0:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice.");
            } // END switch
        } // END while

    } // END run

    /*******************************************************************
     * Main menus
     ******************************************************************/
    /**
     * print options user can choose from
     */
    private static void showMainMenu() {
        System.out.println("\n===== ADDRESS BOOK MENU =====");
        System.out.println("1. Add New Contact");
        System.out.println("2. Edit Contact");
        System.out.println("3. Delete Contact");
        System.out.println("4. View All Contacts");
        System.out.println("5. Search Contacts");
        System.out.println("6. Filter Contacts");
        System.out.println("7. Manage Groups");
        System.out.println("8. Save to File");
        System.out.println("9. Load from File");
        System.out.println("10. Manage Tags");
        System.out.println("0. Exit");
    } // END showMainMenu

    /**
     * Options to modify groups
     */
    public static void manageGroups(AddressBook book) {

        System.out.println("\n--- GROUP MENU ---");
        System.out.println("1. Create Group");
        System.out.println("2. Add Contact to Group");
        System.out.println("3. Remove Contact from Group");
        System.out.println("4. Delete Group");
        System.out.println("5. View Groups");

        int choice = getIntInput("Choose: ");

        switch (choice) {
            // Create group
            case 1: {
                String name = getStringInput("Group name: ");

                if (book.createGroup(name)) {
                    System.out.println("Created.");
                } else {
                    System.out.println("Group exists.");
                }
                break;
            } // END case 1

            // Add contact
            case 2: {
                int id = getIntInput("Contact ID: ");
                String g = getStringInput("Group: ");

                if (book.addContactToGroup(id, g)) {
                    System.out.println("Added.");
                } else {
                    System.out.println("Error.");
                }
                break;
            }

            // Remove
            case 3: {
                int id = getIntInput("Contact ID: ");
                String g = getStringInput("Group: ");

                if (book.removeContactFromGroup(id, g)) {
                    System.out.println("Removed.");
                } else {
                    System.out.println("Error.");
                }
                break;
            }

            // Delete
            case 4: {
                String g = getStringInput("Group name: ");

                if (book.deleteGroup(g)) {
                    System.out.println("Deleted.");
                } else {
                    System.out.println("Not found.");
                }
                break;
            }

            // List all group info
            case 5: {
                book.displayGroupSummaries();
                break;
            }

            default:
                System.out.println("Invalid.");
        }
    } // END manageGroups

    /**
     * Options to modify tags
     */
    private static void manageTags(AddressBook book) {

        int id = getIntInput("ID for tag management: ");

        Contact c = book.searchById(id);

        // Check if user id found
        if (c == null) {
            System.out.println("Not found.");
            return;
        }

        // Tag options prompt
        System.out.println("\n--- TAG MENU ---");
        System.out.println("1. Add Tag");
        System.out.println("2. Remove Tag");
        System.out.println("3. View Tags");

        int choice = getIntInput("Choose: ");

        switch (choice) {
            // Add tag
            case 1: {
                String t = getStringInput("Tag to add: ");
                c.addTag(t);
                System.out.println("Added.");
                break;
            }
            // Remove
            case 2: {
                String t = getStringInput("Tag to remove: ");
                c.removeTag(t);
                System.out.println("Removed.");
                break;
            }
            // Print tags
            case 3: {
                System.out.println("Tags: " + c.getTags());
                break;
            }
            // Default, error
            default:
                System.out.println("Invalid.");
        } // END switch
    } // END manageTags

    /*******************************************************************
     * Contact operations
     ******************************************************************/
    /**
     * UI for entering contacts
     */
    private static void addContactUI(AddressBook book) {
        int id = getIntInput("ID: ");
        String type = getStringInput("Type: ");
        String name = getStringInput("Name: ");
        String city = getStringInput("City: ");
        String email = getStringInput("Email: ");
        String phone = getStringInput("Phone: ");

        ArrayList<String> tags = new ArrayList<>();
        char addTagChoice;

        System.out.print("Add tag(s)? (y/n): ");
        addTagChoice = sc.nextLine().charAt(0);

        // Get user to enter tags as long as they input y
        while (addTagChoice == 'y' || addTagChoice == 'Y') {
            tags.add(getStringInput("Enter tag: "));

            System.out.print("Add another? (y/n): ");
            addTagChoice = sc.nextLine().charAt(0);
        } // END while while (addTagChoice == 'y' || addTagChoice == 'Y')

        // Check if contact was added
        boolean ok = book.addContact(new Contact(id, type, name, city, email, phone, tags));

        // Contact added
        if (ok) {
            System.out.print("Contact added.\n");
        }// Contact not added
        else {
            System.out.print("Error: ID already exists.\n");
        }

    } // END addContactUI

    /**
     * UI for editing contacts
     */
    public static void editContactUI(AddressBook book) {
        // Get id of contact to edit
        int id = getIntInput("Enter ID to edit: ");
        Contact c = book.searchById(id);

        // Check if contact exists
        if (c == null) {
            System.out.println("Contact not found.");
            return;
        } // END if (c == null)

        // Editing contact
        System.out.println("\nEditing Contact:\n");
        System.out.println(Formatter.formatDetails(c));

        // Name
        String name = getStringInput("New name (leave blank to keep): ");
        if (!name.isEmpty()) {
            c.setName(name);
        } // END if

        // City
        String city = getStringInput("New city (leave blank to keep): ");
        if (!city.isEmpty()) {
            c.setCity(city);
        } // END if

        // Email
        String email = getStringInput("New email (leave blank to keep): ");
        if (!email.isEmpty()) {
            c.setEmail(email);
        } // END if

        // Phone Number
        String phone = getStringInput("New phone (leave blank to keep): ");
        if (!phone.isEmpty()) {
            c.setPhone(phone);
        } // END if

        System.out.println("Updated.");
    } // END editContactUI

    /**
     * UI for deleting contacts
     */
    private static void deleteContactUI(AddressBook book) {
        int id = getIntInput("Enter ID to delete: ");
        // Found ID
        if (book.removeContactById(id)) {
            System.out.println("Deleted.");
        }// Did not find
        else {
            System.out.println("Contact not found.");
        }

    } // END deleteContactUI

    /**
     * View all contacts information
     */
    public static void viewAllUI(AddressBook book) {

        ArrayList<Contact> list = book.getAllContacts();

        // No contacts
        if (list.isEmpty()) {
            System.out.println("No contacts.");
            return;
        } // END if

        // Print all contact info
        for (Contact c : list) {
            System.out.println(Formatter.formatDetails(c));
        } // END for
    } // END viewAllUI

    /**
     * UI to search for a person by name, email or phone
     */
    public static void searchUI(AddressBook book) {

        // Prompt options
        System.out.println("\nSearch by:");
        System.out.println("1 = Name");
        System.out.println("2 = Email");
        System.out.println("3 = Phone");

        int choice = getIntInput("Choose: ");
        String query = getStringInput("Enter search value: ");

        ArrayList<Contact> results;

        // Search by name
        if (choice == 1) {
            results = book.searchByName(query);
        } // END if
        // Search by email
        else if (choice == 2) {
            results = book.searchByEmail(query);
        } // END else if
        // Search by phone
        else if (choice == 3) {
            results = book.searchByPhone(query);
        } // END else if
        // Search option invalid, default
        else {
            System.out.println("Invalid.");
            return;
        } // END else

        // Did not find anything
        if (results.isEmpty()) {
            System.out.println("No results.");
            return;
        } // END if

        // Searching through all contacts
        for (Contact c : results) {
            System.out.println(Formatter.formatDetails(c));
        } // END for
    } // END searchUI

    /**
     * UI to filter through contacts by type, city or tag
     */
    public static void filterUI(AddressBook book) {

        // Prompt filter options
        System.out.println("\nFilter by:");
        System.out.println("1 = Type");
        System.out.println("2 = City");
        System.out.println("3 = Tag");

        int choice = getIntInput("Choose: ");
        String query = getStringInput("Enter filter value: ");

        ArrayList<Contact> results;

        // Filter by type
        if (choice == 1) {
            results = book.filterByType(query);
        } // END if
        // Filter by city
        else if (choice == 2) {
            results = book.filterByCity(query);
        } // END else if
        // Filter by tag
        else if (choice == 3) {
            results = book.filterByTag(query);
        } // END else if
        // Invalid option, default
        else {
            System.out.println("Invalid.");
            return;
        } // END else

        // Found nothing
        if (results.isEmpty()) {
            System.out.println("No results.");
            return;
        } // END if

        // Filtering
        for (Contact c : results) {
            System.out.println(Formatter.formatDetails(c));
        } // END for
    } // END filterUI

    /**
     * *****************************************************************
     * Helpers
     *****************************************************************
     */
    /**
     * function to get user input for integers
     *
     * @param prompt message to ouput to user
     */
    private static int getIntInput(String prompt) {
        System.out.print(prompt);

        while (!sc.hasNextInt()) {
            sc.nextLine(); // discard invalid input
            System.out.print("Invalid input. Try again: ");
        }

        int value = sc.nextInt();
        sc.nextLine(); // consume leftover newline
        return value;
    } // END getIntInput

    /**
     * function to get user input for Strings
     *
     * @param prompt message to ouput to user
     */
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    } // END getStringInput

} // END UIController
