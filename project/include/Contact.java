/**
 * @file Contact.java
 * @brief
 */
package project.include;

import java.util.Vector;

public class Contact {

    private int id;
    private String type;
    private String name;
    private String city;
    private String email;
    private String phone;
    private Vector<String> tags;

    /**
     * Constructor with tags
     */
    public Contact(int id, String type, String name, String city,
            String email, String phone, Vector<String> tags) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.tags = tags;
    } // END Contact / Constructor with tags

    /**
     * Constructor NO tags
     */
    public Contact(int id, String type, String name, String city,
                  String email, String phone) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.city = city;
        this.email = email;
        this.phone = phone;
    } // END Contact / Constructor no tags

    /**
     * Getters
     */
    public int getId() { return id; }
    public String getType() { return type; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    /**
     * Setters
     */
    public void setId(int newId) { id = newId; }
    public void setName(String n) { name = n; }
    public void setCity(String c) { city = c; }
    public void setEmail(String e) { email = e; }
    public void setPhone(String p) { phone = p; }

    /**
     * Print all tags
     */
    public void displayTags() {
        System.out.println("Tags: ");

        for (String tag : tags) {
            System.out.print(tag + " ");
        } // END for (String tag : tags)

        System.out.println();
    } // END displayTags

    /**
     * Add tags if tage does not already exist
     * @param tag tag to add
     */
    public void addTag(String tag) {
        if (!hasTag(tag)) {
            tags.add(tag);
        } // END (!hasTag(tag))
    } // END addTag

    /**
     * Remove tag
     * @param tag tag to remove
     */
    public void removeTag(String tag) {
        tags.remove(tag);
    } // END removeTag

    /**
     * Checks if tag exists
     * @param tag tag to check
     */
    public boolean hasTag(String tag) {
        return tags.contains(tag);
    } // END hasTag
    
    /**
     * Prints quick summary of key contact features
     */
    public void printSummary() {
        System.out.println("[" + id + "] " + name + " (" + type + ")");
    } // END printSummary

    /**
     * Prints important details about the contact
     */
    public void printDetails() {
        System.out.println("ID: " + id);
        System.out.println("Type: " + type);
        System.out.println("Name: " + name);
        System.out.println("City: " + city);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);

        displayTags();

        System.out.println();
        System.out.println("------------------------");
    } // END printDetails
} // END class Contact
