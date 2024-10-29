/**
 * <p>Student Class.</p>
 * 
 * <p>Description: This class represents a Student user in the system, which is a subclass of the `User` class. 
 * It initializes the student with the necessary details such as email, username, password, and personal information. 
 * By default, the student is assigned an Admin role.</p>
 * 
 * <p>Copyright: Th21 © 2024 </p>
 * 
 * @author Th21
 * 
 * @version 1.00    2024-10-09 Initial implementation of the Student class as a subclass of User.
 */
package phase1GUI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Constructor to initialize the Student object with provided attributes and assigns the "Admin" role by default.
 */
public class Student extends User {
	/**
	 * Constructor to create a new Student object.
	 * 
	 * @param email         The email address of the student.
	 * @param username      The username chosen by the student.
	 * @param password      The password for the student's account (stored as a byte array).
	 * @param otpExpiry     The expiration time for the one-time password (OTP).
	 * @param firstName     The first name of the student.
	 * @param middleName    The middle name of the student.
	 * @param lastName      The last name of the student.
	 * @param preferredName The preferred name of the student (optional).
	 * @param topics        A list of topics the student is associated with.
	 */
    public Student(String email, String username, byte[] password, LocalDateTime otpExpiry, String firstName,
                 String middleName, String lastName, String preferredName, List<String> topics, String inviteCode) {
        super(email, username, password, false, otpExpiry, firstName, middleName, lastName, preferredName, topics, new ArrayList<>(List.of("Admin")), inviteCode);
    }

}