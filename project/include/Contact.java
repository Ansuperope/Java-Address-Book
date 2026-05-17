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

} // END class Contact
