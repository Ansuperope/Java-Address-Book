/**
 * @file Main.java
 *
 * @details
 * Build a console-based Address Book application that lets users manage
 * different types of contacts. Users can add, edit, delete, search,
 * and list contacts, as well as save and load them from a file.
 */
package project;

// Include files from other folders
import project.include.AddressBook;
import project.include.UIController;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        AddressBook myBook = new AddressBook();
        try {
            myBook.loadFromFile();
        } catch (IOException e) {
            System.out.println("Could not load contacts: " + e.getMessage());
        }

        UIController.run(myBook);

    } // END main(String[] args)

} // END class Main
