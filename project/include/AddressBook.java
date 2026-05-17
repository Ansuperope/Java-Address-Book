/**
 * @file AddressBook.java
 */
package project.include;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import project.include.Contact;

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
     * Adds a basic contact
     *
     * @param associate person to add
     */
    public void addContact(Contact associate) {
        contacts.add(associate);
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
     * Removes a contact based on the user-inputted id. The contact is 
     * erased if the user-inputted id is found within the vector of 
     * contact objects and returns true, and changes nothing if there is 
     * no contact with the id while returning false.
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
     *  Interactive function that calls displayAllContacts() then allows
     * for the user to input a basic id for a contact to delete, calls 
     * removeContactById(), then reports the result to the user. The 
     * function validates the id, letting the user know that the input 
     * is invalid if it does not fit within the constraints of the 
     * program. The function will let the user know if a contact was 
     * deleted, and if not, the user will be notified that there was no 
     * contact with the inputted id.
     */
    

    /**
     * Searching functions that browse for a specific feature of a 
     * contact, given a vector of contacts. These functions will often 
     * only find one instance of the feature, unless there is a special
     * case.
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

} // END class AddressBook
