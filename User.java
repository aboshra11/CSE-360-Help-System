package phase1GUI;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The `User` class represents a generic user in the system.
 * It contains the user's basic information such as email, username, password, roles, and other personal details.
 * The class includes methods to manage roles, personal information, and OTP functionality.
 */
public class User {
    private String email;
    private String username;
    private byte[] password;
    private boolean isOneTimePassword;
    private LocalDateTime otpExpiry;
    private String firstName;
    private String middleName;
    private String lastName;
    private String preferredName;
    private List<String> topics;
    private String expertiseLevel;
    private ArrayList<String> roles; // Changed to ArrayList
    private String inviteCode;  // Field to store the invitation code

    /**
     * Constructor to initialize a user with the given attributes.
     * 
     * @param email             The email address of the user.
     * @param username          The username of the user.
     * @param password          The user's password, stored as a byte array.
     * @param isOneTimePassword A flag to indicate if the password is a one-time password.
     * @param otpExpiry         The expiration time for the one-time password.
     * @param firstName         The user's first name.
     * @param middleName        The user's middle name.
     * @param lastName          The user's last name.
     * @param preferredName     The user's preferred name (optional).
     * @param topics            A list of topics associated with the user.
     * @param roles             A list of roles assigned to the user.
     * @param inviteCode        Invite code for user.
     */
    public User(String email, String username, byte[] password, boolean isOneTimePassword, LocalDateTime otpExpiry,
                String firstName, String middleName, String lastName, String preferredName, List<String> topics,
                ArrayList<String> roles, String inviteCode) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.isOneTimePassword = isOneTimePassword;
        this.otpExpiry = otpExpiry;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.preferredName = preferredName;
        this.topics = topics;
        this.expertiseLevel = "Intermediate"; // Default expertise level
        this.roles = roles; // Initialize directly
        this.inviteCode = inviteCode;
    }

    // Getters and Setters for each attribute

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public boolean isOneTimePassword() {
        return isOneTimePassword;
    }

    public void setOneTimePassword(boolean oneTimePassword) {
        isOneTimePassword = oneTimePassword;
    }

    public LocalDateTime getOtpExpiry() {
        return otpExpiry;
    }

    public void setOtpExpiry(LocalDateTime otpExpiry) {
        this.otpExpiry = otpExpiry;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getExpertiseLevel() {
        return expertiseLevel;
    }

    public void setExpertiseLevel(String expertiseLevel) {
        this.expertiseLevel = expertiseLevel;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    /**
     * Adds a role to the user if the role is not already present.
     * 
     * @param role The role to add.
     */
    public void addRole(String role) {
        if (!roles.contains(role)) {
            roles.add(role);
            System.out.println("Role " + role + " added to user.");
        } else {
            System.out.println("Role " + role + " already exists for this user.");
        }
    }

    /**
     * Removes a role from the user if the role exists.
     * 
     * @param role The role to remove.
     */
    public void removeRole(String role) {
        if (roles.contains(role)) {
            roles.remove(role);
            System.out.println("Role " + role + " removed from user.");
        } else {
            System.out.println("Role " + role + " does not exist for this user.");
        }
    }

    /**
     * Method to check if OTP is valid based on expiry.
     *
     * @return True if OTP is valid, false otherwise.
     */
    public boolean isOtpValid() {
        return isOneTimePassword && LocalDateTime.now().isBefore(otpExpiry);
    }

    /**
     * Method to reset password using OTP.
     *
     * @param newPassword The new password to set.
     */
    public void resetPassword(byte[] newPassword) {
        if (isOneTimePassword && isOtpValid()) {
            setPassword(newPassword);
            setOneTimePassword(false); // Disable OTP after resetting
            System.out.println("Password successfully reset.");
        } else {
            System.out.println("Password cannot be reset. OTP is invalid or expired.");
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                '}';
    }
}
