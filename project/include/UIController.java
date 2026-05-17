/**
 * @file UIController.java
 */
package project.include;

import java.util.ArrayList;
import java.util.Scanner;
import project.include.AddressBook;

public class UIController {
    // getting input
    private final static Scanner sc = new Scanner(System.in);

    /**
     * main program
     */
    static void run(AddressBook book) { 
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
     * 
     */
    private static void manageGroups(AddressBook book) { 
    } // END manageGroups
    private static void manageTags(AddressBook book) { 
    } // END manageTags

    /*******************************************************************
     * Contact operations
     ******************************************************************/
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

        while (addTagChoice == 'y' || addTagChoice == 'Y') {

            tags.add(getStringInput("Enter tag: "));

            System.out.print("Add another? (y/n): ");
            addTagChoice = sc.nextLine().charAt(0);
        } // END while while (addTagChoice == 'y' || addTagChoice == 'Y')


        boolean ok = book.addContact(new Contact(id, type, name, city, email, phone, tags));

        // Contact added
        if (ok)
            System.out.print("Contact added.\n");
        // Contact not added
        else
            System.out.print("Error: ID already exists.\n");

    } // END addContactUI

    private static void editContactUI(AddressBook book) { 
    } // END editContactUI

    private static void deleteContactUI(AddressBook book) { 
    } // END deleteContactUI

    private static void viewAllUI(AddressBook book) { 
    } // END viewAllUI

    private static void searchUI(AddressBook book) { 
    } // END searchUI

    private static void filterUI(AddressBook book) { 
    } // END filterUI

    /*******************************************************************
     * Helpers
     ******************************************************************/
    /**
     * function to get user input for integers
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
     * @param prompt message to ouput to user
     */
    private static String getStringInput(String prompt) { 
        System.out.print(prompt);
        return sc.nextLine();
    } // END getStringInput

} // END UIController