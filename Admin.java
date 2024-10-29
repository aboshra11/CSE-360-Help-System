package phase1GUI;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import database.DatabaseHelper;
import database.HelpArticle;

/**
 * Admin class extends the User class and provides additional admin functionalities.
 */
public class Admin extends User {

    private DatabaseHelper dbHelper;

    public Admin(String name, String email, DatabaseHelper dbHelper) {
        // Assuming default values for password, isOneTimePassword, etc.
        super(email, name, new byte[0], false, null, name, null, null, name, new ArrayList<>(), new ArrayList<>(List.of("Admin")), null);
        this.dbHelper = dbHelper;
    }

    public Admin(String email, String username, byte[] password, LocalDateTime otpExpiry, String firstName,
                 String middleName, String lastName, String preferredName, List<String> topics) throws Exception {
        super(email, username, password, false, otpExpiry, firstName, middleName, lastName, preferredName, topics,
              new ArrayList<>(List.of("Admin")), null);
        this.dbHelper = new DatabaseHelper();
        dbHelper.connectToDatabase();
    }

    /**
     * Creates a new help article and saves it to the database.
     *
     * @param title             The title of the article.
     * @param authors           The authors of the article.
     * @param abstractText      The abstract of the article.
     * @param keywords          The keywords of the article.
     * @param body              The body content of the article.
     * @param references        The references for the article.
     * @param groups            The groups this article belongs to.
     * @param level             The level of the article (e.g., beginner, intermediate).
     * @param shortDescription  A brief description of the article.
     * @param systemInfo        Additional system information for restricted content.
     * @throws Exception If there is an issue inserting the article.
     */
    public void createHelpArticle(char[] title, char[] authors, char[] abstractText, char[] keywords, char[] body, char[] references,
                                  List<String> groups, String level, String shortDescription, String systemInfo) throws Exception {
        HelpArticle newArticle = new HelpArticle(System.currentTimeMillis(), title, authors, abstractText, keywords, body,
                                                 references, groups, level, shortDescription, systemInfo);
        dbHelper.insertArticle(newArticle);
        System.out.println("Help article created with ID: " + newArticle.getId());
    }

    /**
     * Updates an existing help article in the database.
     *
     * @param articleId         The unique ID of the article to update.
     * @param title             The new title of the article.
     * @param authors           The new authors of the article.
     * @param abstractText      The new abstract of the article.
     * @param keywords          The new keywords of the article.
     * @param body              The new body content of the article.
     * @param references        The new references for the article.
     * @param groups            The new groups for the article.
     * @param level             The level of the article (e.g., beginner, intermediate).
     * @param shortDescription  The new short description of the article.
     * @param systemInfo        The new system information.
     * @throws Exception If there is an issue updating the article.
     */
    public void updateHelpArticle(long articleId, char[] title, char[] authors, char[] abstractText, char[] keywords, char[] body,
                                  char[] references, List<String> groups, String level, String shortDescription, String systemInfo) throws Exception {
        HelpArticle article = dbHelper.getArticleById(articleId);
        if (article != null) {
            HelpArticle updatedArticle = new HelpArticle(articleId, title, authors, abstractText, keywords, body,
                                                         references, groups, level, shortDescription, systemInfo);
            dbHelper.insertArticle(updatedArticle);  // Reinsert the article with updated data
            System.out.println("Help article with ID: " + articleId + " has been updated.");
        } else {
            System.out.println("Article with ID: " + articleId + " not found.");
        }
    }

    /**
     * Deletes a help article from the database.
     *
     * @param articleId The unique ID of the article to delete.
     * @throws Exception If there is an issue deleting the article.
     */
    /**
     * Deletes a help article from the database.
     *
     * @param articleId The unique ID of the article to delete.
     * @throws Exception If there is an issue deleting the article.
     */
    public void deleteHelpArticle(long articleId) throws Exception {
        // Check if the article exists before attempting to delete
        HelpArticle article = dbHelper.getArticleById(articleId);
        
        if (article != null) {
            dbHelper.deleteArticleById(articleId);  // Delete the article by ID
            System.out.println("Help article with ID: " + articleId + " has been deleted.");
        } else {
            System.out.println("No article found with ID: " + articleId);
        }
    }
    /**
     * Backs up all help articles in the specified groups to a file.
     *
     * @param folderPath The folder where the backup file will be saved.
     * @param fileName   The name of the backup file.
     * @param groups     The groups of articles to back up.
     * @throws Exception If there is an issue backing up the articles.
     */
    public void backupHelpArticlesByGroup(String folderPath, String fileName, List<String> groups) throws Exception {
        dbHelper.backupArticlesByGroup(folderPath, fileName, groups);
        System.out.println("Backup completed for groups: " + String.join(", ", groups));
    }

    /**
     * Restores help articles from a backup file, either merging with or replacing existing ones.
     *
     * @param filePath The path to the backup file.
     * @param merge    If true, articles will be merged with existing ones; otherwise, they will replace existing articles.
     * @throws Exception If there is an issue restoring the articles.
     */
    public void restoreHelpArticles(String filePath, boolean merge) throws Exception {
        dbHelper.restoreArticles(filePath, merge);
        System.out.println("Restore operation completed with merge option set to: " + merge);
    }

    /**
     * Lists all the help articles in the database.
     *
     * @throws Exception If there is an issue retrieving the articles.
     */
    public void listHelpArticles() throws Exception {
        List<HelpArticle> articles = dbHelper.getAllArticles();
        for (HelpArticle article : articles) {
            System.out.println("ID: " + article.getId() + ", Title: " + new String(article.getTitle()) + ", Groups: " + article.getGroups());
        }
    }

    /**
     * Adds a new role to a user.
     *
     * @param email The email of the user.
     * @param role  The role to add.
     * @throws SQLException If there is an issue updating the user roles.
     */
    public void addRoleToUser(String email, String role) throws Exception {
        dbHelper.addRoleToUser(email, role);
    }

    /**
     * Removes a role from a user.
     *
     * @param email The email of the user.
     * @param role  The role to remove.
     * @throws SQLException If there is an issue updating the user roles.
     */
    public void removeRoleFromUser(String email, String role) throws Exception {
        dbHelper.removeRoleFromUser(email, role);
    }

    /**
     * Generates a secure invitation code for a user based on their username and roles.
     *
     * @param username The username of the user to generate an invitation for.
     * @param roles    The list of roles assigned to the user.
     * @return The generated invitation code.
     */
    public String generateInvitationCode(String username, List<String> roles) {
        String invitationCode = UUID.randomUUID().toString();
        System.out.println("Invitation code generated for " + username + ": " + invitationCode);
        return invitationCode;
    }

    /**
     * Resets the account of a user by setting a new one-time password and expiry.
     *
     * @param user The user whose account is being reset.
     */
    public void resetUserAccount(User user) {
        String oneTimePassword = "newOTP_" + System.currentTimeMillis();
        user.setPassword(oneTimePassword.getBytes());
        user.setOneTimePassword(true);
        user.setOtpExpiry(LocalDateTime.now().plusDays(1));
        System.out.println("Password reset for user: " + user.getUsername());
    }
    
    public User getUserByEmail(String email) throws Exception {
        return dbHelper.getUserByEmail(email); // Assumes getUserByEmail(email) exists in DatabaseHelper
    }

    /**
     * Deletes a user account from the system.
     *
     * @param email The email of the user to delete.
     * @throws SQLException If there is an issue deleting the user.
     */
    /**
     * Deletes a user account from the system.
     *
     * @param email The email of the user to delete.
     * @throws Exception If there is an issue deleting the user.
     */
    public void deleteUser(String email) throws Exception {
        // Check if the user exists before attempting to delete
        User user = dbHelper.getUserByEmail(email);
        
        if (user != null) {
            dbHelper.deleteUserByEmail(email);  // Delete user by email
            System.out.println("User account with email: " + email + " has been deleted.");
        } else {
            System.out.println("No user found with email: " + email);
        }
    }

    /**
     * Lists all users from the database.
     */
    public List<User> listUserAccounts() throws Exception {
        return dbHelper.getAllUsers();
    }

    
    public HelpArticle getArticleById(long id) throws Exception {
        return dbHelper.getArticleById(id);
    }

}
