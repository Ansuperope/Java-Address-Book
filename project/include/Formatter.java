/**
 * @file Formatter.java
 * @brief utility class used to print information neatly
 */
package project.include;

public class Formatter {

    /**
     * Format contact id, name and type to print
     */
    public static String formatSummary (Contact c) {
        return "[" + c.getId() + "] "
            + c.getName()
            + " (" + c.getType() + ")";
    } // END formatSummary

    /**
     * Format to print all contact details
     */
    public static String formatDetails(Contact c) {

        StringBuilder out = new StringBuilder();

        out.append("────────────────────────\n");
        out.append("ID: ").append(c.getId()).append("\n");
        out.append("Type: ").append(c.getType()).append("\n");
        out.append("Name: ").append(c.getName()).append("\n");
        out.append("City: ").append(c.getCity()).append("\n");
        out.append("Email: ").append(c.getEmail()).append("\n");
        out.append("Phone: ").append(c.getPhone()).append("\n");
        out.append("Tags: ").append(c.getTags()).append("\n");
        out.append("────────────────────────\n");

        return out.toString();
    } // END formatDetails
} // END class Formatter