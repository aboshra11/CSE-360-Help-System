/**
 * <p>Role Enum.</p>
 * 
 * <p>Description: This enum represents the different roles in the system, including Admin, Student, 
 * and Instructor. Each role has an associated display name. It also provides utility methods 
 * for retrieving roles by their string names (case-insensitive).</p>
 * 
 * <p>Copyright: Th21 © 2024 </p>
 * 
 * @author Th21
 * 
 * @version 1.00    2024-10-09 Initial implementation of Role enum to define user roles.
 */
package phase1GUI;

/**
 * Enum representing the roles within the system.
 * Each role has a display name.
 */
public enum Role {
    /**
     * Administrator role with higher privileges.
     */
    ADMIN("Admin"),
    
    /**
     * Student role with access to learning materials.
     */
    STUDENT("Student"),
    

    /**
     * Instructor role with teaching privileges.
     */
    INSTRUCTOR("Instructor");

    private final String displayName;

    /**
     * Constructor to initialize the enum with the role's display name.
     * 
     * @param displayName The string representation of the role.
     */
    Role(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of the role.
     * 
     * @return The display name of the role.
     */
    public String getDisplayName() {
        return displayName;
    }


    /**
     * Converts a string to the corresponding Role enum.
     * The method performs a case-insensitive match.
     * 
     * @param roleName The string representation of the role.
     * @return The Role corresponding to the string.
     * @throws IllegalArgumentException If the provided string doesn't match any role.
     */
    public static Role fromString(String roleName) {
        for (Role role : Role.values()) {
            if (role.displayName.equalsIgnoreCase(roleName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role name: " + roleName);
    }

    /**
     * Returns the string representation of the role.
     * 
     * @return The display name of the role.
     */
    @Override
    public String toString() {
        return displayName;
    }
}
