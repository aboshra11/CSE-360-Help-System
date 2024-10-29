/**
 * <p>Instructor Class.</p>
 * 
 * <p>Description: This class represents an Instructor user in the system, which is a subclass of the `User` class. 
 * It initializes the instructor with the necessary details such as email, username, password, and personal information. 
 * By default, the instructor is assigned an Admin role.</p>
 * 
 * <p>Copyright: Th21 © 2024 </p>
 * 
 * @author Th21
 * 
 * @version 1.00    2024-10-09 Initial implementation of the Instructor class as a subclass of User.
 */
package phase1GUI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Constructor to initialize the Instructor object with provided attributes and assigns the "Admin" role by default.
 */
public class Instructor extends User {
	/**
	 * Constructor to create a new Instructor object.
	 * 
	 * @param email         The email address of the instructor.
	 * @param username      The username chosen by the instructor.
	 * @param password      The password for the instructor's account (stored as a byte array).
	 * @param otpExpiry     The expiration time for the one-time password (OTP).
	 * @param firstName     The first name of the instructor.
	 * @param middleName    The middle name of the instructor.
	 * @param lastName      The last name of the instructor.
	 * @param preferredName The preferred name of the instructor (optional).
	 * @param topics        A list of topics the instructor is associated with.
	 */
    public Instructor(String email, String username, byte[] password, LocalDateTime otpExpiry, String firstName,
                 String middleName, String lastName, String preferredName, List<String> topics, String inviteCode) {
        super(email, username, password, false, otpExpiry, firstName, middleName, lastName, preferredName, topics, new ArrayList<>(List.of("Admin")), inviteCode);
    }

}