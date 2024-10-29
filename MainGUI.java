package phase1GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import database.DatabaseHelper;
import database.HelpArticle;

/**
 * Main class for the GUI application.
 * This class extends the JavaFX Application class to provide the main GUI window.
 */
public class MainGUI extends Application {
    private List<User> users = new ArrayList<>();
    private boolean isInitialLogin = true; // Flag to indicate if it's the initial login
    private Label messageLabel = new Label(); // Label to display messages
    private Admin adminUser;
    private AdminHelpArticleManager articleManager;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            adminUser = new Admin("Admin Name", "admin@example.com", new DatabaseHelper());
            articleManager = new AdminHelpArticleManager(adminUser, messageLabel, this); // Pass MainGUI instance

            primaryStage.setTitle("Login Page");
            primaryStage.setScene(createLoginScene(primaryStage, isInitialLogin));
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Error initializing the application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public List<User> getUsers() {
        return users;
    }

    private Scene createLoginScene(Stage primaryStage, boolean showConfirmPassword) {
        messageLabel.setText(""); // Clear old messages
        Label userLabel = new Label("Username:");
        TextField userTextField = new TextField();
        Label pwLabel = new Label("Password:");
        PasswordField pwTextField = new PasswordField();
        Label pwConfirmLabel = new Label("Confirm Password:");
        PasswordField pwConfirmTextField = new PasswordField();

        Button loginOrCreateButton = new Button(showConfirmPassword ? "Submit" : "Login");
        Button invitationCodeButton = new Button("Use Invitation Code");
        Button forgotPasswordButton = new Button("Forgot Password");

        // Layout for the login/registration form
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(userLabel, 0, 0);
        grid.add(userTextField, 1, 0);
        grid.add(pwLabel, 0, 1);
        grid.add(pwTextField, 1, 1);

        if (showConfirmPassword) {
            // Add Confirm Password field and Submit button for account creation
            grid.add(pwConfirmLabel, 0, 2);
            grid.add(pwConfirmTextField, 1, 2);
            grid.add(loginOrCreateButton, 1, 3);
            grid.add(invitationCodeButton, 1, 4);
            grid.add(forgotPasswordButton, 1, 5);
        } else {
            // Standard login (no Confirm Password)
            grid.add(loginOrCreateButton, 1, 2);
            grid.add(invitationCodeButton, 1, 3);
            grid.add(forgotPasswordButton, 1, 4);
        }

        grid.add(messageLabel, 0, 6, 2, 1);

        // Set event handlers for buttons
        loginOrCreateButton.setOnAction(e -> {
            String username = userTextField.getText();
            byte[] password = pwTextField.getText().getBytes();

            if (username.isEmpty() || pwTextField.getText().isEmpty() || (showConfirmPassword && pwConfirmTextField.getText().isEmpty())) {
                messageLabel.setText("All fields must be filled out.");
                return;
            }

            if (showConfirmPassword) {
                byte[] confirmPassword = pwConfirmTextField.getText().getBytes();
                if (!new String(password).equals(new String(confirmPassword))) {
                    messageLabel.setText("Passwords do not match.");
                    return;
                }

                // Create a new admin user or another role
                try {
                    User admin = new Admin("", username, password, LocalDateTime.now().plusDays(1), "", "", "", "", new ArrayList<>());
                    users.add(admin);
                    messageLabel.setText("Account created for: " + username);
                    userTextField.clear();
                    pwTextField.clear();
                    pwConfirmTextField.clear();
                    isInitialLogin = false; // No longer the first login

                    // Redirect to account setup
                    showSetupPage(primaryStage, admin);
                } catch (Exception excptn) {
                    excptn.printStackTrace();
                    messageLabel.setText("An error occurred while creating the account.");
                }

                // Redirect to account setup
            } else {
                // Handle user login
                boolean userFound = false;
                for (User user : users) {
                    if (user.getUsername().equals(username) && new String(user.getPassword()).equals(new String(password))) {
                        if (user.getInviteCode() == null) {
                            messageLabel.setText("Welcome, " + username + "! Role: " + user.getRoles());
                            showRoleSelectionPage(primaryStage, user);
                        } else {
                            messageLabel.setText("Please complete your account setup using the invitation code.");
                        }
                        userFound = true;
                        break;
                    }
                }
                if (!userFound) {
                    messageLabel.setText("Invalid username or password.");
                }
            }
        });

        // Invitation code and OTP event handlers
        invitationCodeButton.setOnAction(e -> showInvitationCodePage(primaryStage));
        forgotPasswordButton.setOnAction(e -> showForgotPasswordPage(primaryStage));

        return new Scene(grid, 300, 300);
    }



    private void showSetupPage(Stage primaryStage, User user) {
        primaryStage.setTitle("Finish Setting Up Your Account");

        // Create labels and text fields
        Label emailLabel = new Label("Email:");
        TextField emailTextField = new TextField();
        Label firstNameLabel = new Label("First Name:");
        TextField firstNameTextField = new TextField();
        Label middleNameLabel = new Label("Middle Name:");
        TextField middleNameTextField = new TextField();
        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameTextField = new TextField();
        Label preferredNameLabel = new Label("Preferred Name (optional):");
        TextField preferredNameTextField = new TextField();
        Label setupMessageLabel = new Label(); // Label to display messages on the setup page

        // Create submit button
        Button submitButton = new Button("Submit");

        submitButton.setOnAction(e -> {
            if (emailTextField.getText().isEmpty() || 
                firstNameTextField.getText().isEmpty() ||
                middleNameTextField.getText().isEmpty() ||
                lastNameTextField.getText().isEmpty()) {
                setupMessageLabel.setText("All fields must be filled out.");
                return;
            }

            user.setEmail(emailTextField.getText());
            user.setFirstName(firstNameTextField.getText());
            user.setMiddleName(middleNameTextField.getText());
            user.setLastName(lastNameTextField.getText());
            String preferredName = preferredNameTextField.getText().isEmpty() ? firstNameTextField.getText() : preferredNameTextField.getText();
            user.setPreferredName(preferredName);

            setupMessageLabel.setText("Account setup complete for: " + user.getUsername());

            // Redirect back to login page
            primaryStage.setTitle("Login Page");
            primaryStage.setScene(createLoginScene(primaryStage, false));
        });

        // Create layout and add components
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(emailLabel, 0, 0);
        grid.add(emailTextField, 1, 0);
        grid.add(firstNameLabel, 0, 1);
        grid.add(firstNameTextField, 1, 1);
        grid.add(middleNameLabel, 0, 2);
        grid.add(middleNameTextField, 1, 2);
        grid.add(lastNameLabel, 0, 3);
        grid.add(lastNameTextField, 1, 3);
        grid.add(preferredNameLabel, 0, 4);
        grid.add(preferredNameTextField, 1, 4);
        grid.add(submitButton, 1, 5);
        grid.add(setupMessageLabel, 0, 6, 2, 1); // Add the message label to the grid

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
    }

    private void showRoleSelectionPage(Stage primaryStage, User user) {
        List<String> roles = new ArrayList<>(); // Collect all the roles the user has
        if (user.getRoles().contains("Admin")) roles.add("Admin");
        if (user.getRoles().contains("Student")) roles.add("Student");
        if (user.getRoles().contains("Instructor")) roles.add("Instructor");
        // If the user has only one role, go directly to the corresponding page
        if (roles.size() == 1) {
            String selectedRole = roles.get(0);
            switch (selectedRole) {
                case "Admin":
                    showAdminPage(primaryStage, articleManager);
                    break;
                case "Student":
                    showStudentPage(primaryStage, user);
                    break;
                case "Instructor":
                    showInstructorPage(primaryStage, user);
                    break;
            }
            return;
        }
        // Multiple roles, show role selection page
        primaryStage.setTitle("Select Role");
        Label roleLabel = new Label("Select your role for this session:");
        Button adminButton = new Button("Admin");
        Button studentButton = new Button("Student");
        Button instructorButton = new Button("Instructor");
       
        // Add event handlers to switch to the appropriate page
        adminButton.setOnAction(e -> showAdminPage(primaryStage, articleManager));
        studentButton.setOnAction(e -> showStudentPage(primaryStage, user));
        instructorButton.setOnAction(e -> showInstructorPage(primaryStage, user));
        // Create layout and add components
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(roleLabel, 0, 0, 2, 1);
       
        if (roles.contains("Admin")) {
            grid.add(adminButton, 0, 1);
        }
        if (roles.contains("Student")) {
            grid.add(studentButton, 1, 1);
        }
        if (roles.contains("Instructor")) {
            grid.add(instructorButton, 0, 2);
        }
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
    }
   
    void showAdminPage(Stage primaryStage, AdminHelpArticleManager articleManager) {
        primaryStage.setTitle("Admin Page");

        // Existing admin functionalities (Phase 1)
        Label adminLabel = new Label("Admin Functions");
        Button inviteUserButton = new Button("Invite User");
        Button resetAccountButton = new Button("Reset Account");
        Button deleteAccountButton = new Button("Delete Account");
        Button listUsersButton = new Button("List User Accounts");
        Button addRemoveRolesButton = new Button("Add/Remove Roles");
        Button logoutButton = new Button("Logout");
        Label adminMessageLabel = new Label(); // Label for admin messages

        // Invite user functionality (Phase 1)
        inviteUserButton.setOnAction(e -> {
            try {
                String inviteCode = articleManager.getAdminUser().generateInvitationCode("new_user", List.of("Student"));
                adminMessageLabel.setText("Invite user with this code: " + inviteCode);
            } catch (Exception ex) {
                adminMessageLabel.setText("Error inviting user: " + ex.getMessage());
            }
        });

        // Reset account functionality (Phase 1)
        resetAccountButton.setOnAction(e -> articleManager.resetAccount(primaryStage, adminMessageLabel));

        // Delete account functionality (Phase 1)
        deleteAccountButton.setOnAction(e -> articleManager.deleteAccount(primaryStage, adminMessageLabel));

        // List user accounts functionality (Phase 1)
        listUsersButton.setOnAction(e -> articleManager.listUserAccounts(adminMessageLabel));

        // Add/remove roles functionality (Phase 1)
        addRemoveRolesButton.setOnAction(e -> articleManager.addRemoveRoles(primaryStage, adminMessageLabel));

        // Logout button functionality
        logoutButton.setOnAction(e -> {
            primaryStage.setTitle("Login Page");
            primaryStage.setScene(createLoginScene(primaryStage, false));
        });

        // GridPane layout for the Phase 1 functionalities
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        // Add Phase 1 components to the GridPane
        grid.add(adminLabel, 0, 0);
        grid.add(inviteUserButton, 0, 1);
        grid.add(resetAccountButton, 0, 2);
        grid.add(deleteAccountButton, 0, 3);
        grid.add(listUsersButton, 0, 4);
        grid.add(addRemoveRolesButton, 0, 5);
        grid.add(logoutButton, 0, 6);
        grid.add(adminMessageLabel, 0, 7);

        // Add Phase 2 functionalities (Help article management)
        Label articleLabel = new Label("Help Article Management");

        Button createArticleButton = new Button("Create Help Article");
        Button updateArticleButton = new Button("Update Help Article");
        Button deleteArticleButton = new Button("Delete Help Article");
        Button listArticlesButton = new Button("List All Articles");
        Button backupArticlesButton = new Button("Backup Articles by Group");
        Button restoreArticlesButton = new Button("Restore Articles");

        // Help Article Creation (Phase 2)
        createArticleButton.setOnAction(e -> articleManager.showCreateArticlePage(primaryStage));

        // Help Article Update (Phase 2)
        updateArticleButton.setOnAction(e -> articleManager.showUpdateArticlePage(primaryStage));

        // Help Article Deletion (Phase 2)
        deleteArticleButton.setOnAction(e -> articleManager.showDeleteArticlePage(primaryStage));

        // List All Help Articles (Phase 2)
        listArticlesButton.setOnAction(e -> articleManager.listHelpArticles());

        // Backup Articles by Group (Phase 2)
        backupArticlesButton.setOnAction(e -> articleManager.showBackupArticlesPage(primaryStage));

        // Restore Articles (Phase 2)
        restoreArticlesButton.setOnAction(e -> articleManager.showRestoreArticlesPage(primaryStage));

        // Adding components for Help Article Management to the GridPane
        grid.add(articleLabel, 0, 8);
        grid.add(createArticleButton, 0, 9);
        grid.add(updateArticleButton, 1, 9);
        grid.add(deleteArticleButton, 0, 10);
        grid.add(listArticlesButton, 1, 10);
        grid.add(backupArticlesButton, 0, 11);
        grid.add(restoreArticlesButton, 1, 11);

        // Set the scene for the admin page with Phase 1 and Phase 2 features
        primaryStage.setScene(new Scene(grid, 400, 500));
    }

   
    private void showInstructorPage(Stage primaryStage, User user) {
        // Add your Instructor page components here
        primaryStage.setTitle("Instructor Page");
        Label instructorLabel = new Label("Welcome to the Instructor Page, " + user.getUsername() + "!");
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            primaryStage.setTitle("Login Page");
            primaryStage.setScene(createLoginScene(primaryStage, false));
        });
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(instructorLabel, 0, 0);
        grid.add(logoutButton, 0, 1);
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
    }
    private void showStudentPage(Stage primaryStage, User user) {
        // Add your Student page components here
        primaryStage.setTitle("Student Page");
        Label studentLabel = new Label("Welcome to the Student Page, " + user.getUsername() + "!");
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            primaryStage.setTitle("Login Page");
            primaryStage.setScene(createLoginScene(primaryStage, false));
        });
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(studentLabel, 0, 0);
        grid.add(logoutButton, 0, 1);
        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
    }

    
    private void showInvitationCodePage(Stage primaryStage) {
        primaryStage.setTitle("Enter Invitation Code");

        // Invitation code input
        Label invitationLabel = new Label("Invitation Code:");
        TextField invitationTextField = new TextField();

        // Username input
        Label usernameLabel = new Label("Create Username:");
        TextField usernameField = new TextField();

        // Password input
        Label passwordLabel = new Label("Create Password:");
        PasswordField passwordField = new PasswordField();

        // Confirm password input
        Label confirmPasswordLabel = new Label("Confirm Password:");
        PasswordField confirmPasswordField = new PasswordField();

        // Message label for submission
        Label invitationMessageLabel = new Label();
        Button submitButton = new Button("Submit");

        submitButton.setOnAction(e -> {
            String enteredInviteCode = invitationTextField.getText();
            String newUsername = usernameField.getText();
            String newPassword = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // Validate the invitation code
            boolean validCode = false;
            User invitedUser = null;

            // Searches for users with the same invite code
            for (User user : users) {
                if (user.getInviteCode() != null && user.getInviteCode().equals(enteredInviteCode)) {
                    validCode = true;
                    invitedUser = user;
                    break;
                }
            }

            // Proceed with account setup if the invitation code is valid
            if (validCode && invitedUser != null) {
                if (newPassword.equals(confirmPassword)) {
                    invitedUser.setUsername(newUsername);
                    invitedUser.setPassword(newPassword.getBytes());
                    invitedUser.setInviteCode(null);  // Clear the invite code now that it's used

                    invitationMessageLabel.setText("Account setup complete! Welcome, " + newUsername);
                    showRoleSelectionPage(primaryStage, invitedUser); // Redirect to role selection
                } else {
                    invitationMessageLabel.setText("Passwords do not match.");
                }
            } else {
                invitationMessageLabel.setText("Invalid invitation code.");
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(invitationLabel, 0, 0);
        grid.add(invitationTextField, 1, 0);
        grid.add(usernameLabel, 0, 1);
        grid.add(usernameField, 1, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(confirmPasswordLabel, 0, 3);
        grid.add(confirmPasswordField, 1, 3);
        grid.add(submitButton, 1, 4);
        grid.add(invitationMessageLabel, 0, 5, 2, 1);

        Scene scene = new Scene(grid, 400, 250);
        primaryStage.setScene(scene);
    }
    
    private void showForgotPasswordPage(Stage primaryStage) {
        primaryStage.setTitle("Reset Password with OTP");

        // Labels and fields for OTP and new password
        Label otpLabel = new Label("One-Time Password (OTP):");
        TextField otpTextField = new TextField();
        Label newPasswordLabel = new Label("New Password:");
        PasswordField newPasswordTextField = new PasswordField();
        Label confirmNewPasswordLabel = new Label("Confirm New Password:");
        PasswordField confirmNewPasswordTextField = new PasswordField();
        Label otpMessageLabel = new Label();
        Button submitButton = new Button("Submit");

        submitButton.setOnAction(e -> {
            String otp = otpTextField.getText();
            String newPassword = newPasswordTextField.getText();
            String confirmNewPassword = confirmNewPasswordTextField.getText();
            boolean otpValid = false;
            User userToUpdate = null;

            // Validate the OTP and password match
            if (!newPassword.equals(confirmNewPassword)) {
                otpMessageLabel.setText("Passwords do not match.");
                return;
            }

            // Check if the OTP is valid
            for (User user : users) {
                if (user.isOneTimePassword() && user.isOtpValid()) {
                    userToUpdate = user;
                    otpValid = true;
                    break;
                }
            }

            if (otpValid && userToUpdate != null) {
                userToUpdate.resetPassword(newPassword.getBytes());
                otpMessageLabel.setText("Password successfully reset.");
                primaryStage.setScene(createLoginScene(primaryStage, false)); // Return to login page
            } else {
                otpMessageLabel.setText("Invalid or expired OTP.");
            }
        });

        // Layout for the reset password form
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(otpLabel, 0, 0);
        grid.add(otpTextField, 1, 0);
        grid.add(newPasswordLabel, 0, 1);
        grid.add(newPasswordTextField, 1, 1);
        grid.add(confirmNewPasswordLabel, 0, 2);
        grid.add(confirmNewPasswordTextField, 1, 2);
        grid.add(submitButton, 1, 3);
        grid.add(otpMessageLabel, 0, 4, 2, 1);

        // Create and set the scene
        Scene scene = new Scene(grid, 300, 250);  // Adjusted scene height for extra field
        primaryStage.setScene(scene);
    }
    public String generateInviteCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder(6);
        Random rnd = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        String inviteCode = code.toString();
        System.out.println("Invite code generated: " + inviteCode); // Prints to console
        return inviteCode;
    }
    private void resetAccount(Stage primaryStage, Label adminMessageLabel) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Reset Account");
        dialog.setHeaderText("Enter the username of the account to reset:");
        dialog.setContentText("Username:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String username = result.get();
            User userToReset = null;
            // Find user by username
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    userToReset = user;
                    break;
                }
            }
            if (userToReset != null) {
                // Implement logic to reset the account (e.g., set a new password, send OTP)
                adminMessageLabel.setText("Account reset for: " + username);
                // Additional logic goes here...
            } else {
                adminMessageLabel.setText("User not found.");
            }
        } else {
            adminMessageLabel.setText("Reset cancelled.");
        }
    }
    private void deleteAccount(Stage primaryStage, Label adminMessageLabel) {
        // Show a dialog to delete an account
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Account");
        dialog.setHeaderText("Enter the username to delete:");
        dialog.setContentText("Username:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String username = result.get();
            users.removeIf(user -> user.getUsername().equals(username));
            adminMessageLabel.setText("Account deleted for user: " + username);
        } else {
            adminMessageLabel.setText("Deletion cancelled.");
        }
    }
    private void listUserAccounts(Label adminMessageLabel) {
        StringBuilder userList = new StringBuilder("User Accounts:\n");
        for (User user : users) {
            userList.append(user.getUsername()).append("\n");
        }
        adminMessageLabel.setText(userList.toString());
    }
    private void addRemoveRoles(Stage primaryStage, Label adminMessageLabel) {
        // Show a dialog to add/remove roles
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add/Remove Roles");
        dialog.setHeaderText("Enter the username and role (format: username, role):");
        dialog.setContentText("Username, Role:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String[] input = result.get().split(",\\s*");
            if (input.length == 2) {
                String username = input[0].trim();
                String role = input[1].trim();
                for (User user : users) {
                    if (user.getUsername().equals(username)) {
                        if (user.getRoles().contains(role)) {
                            user.removeRole(role); // Assuming you have a removeRole method
                            adminMessageLabel.setText("Removed role: " + role + " from user: " + username);
                        } else {
                            user.addRole(role); // Assuming you have an addRole method
                            adminMessageLabel.setText("Added role: " + role + " to user: " + username);
                        }
                        return;
                    }
                }
                adminMessageLabel.setText("User not found.");
            } else {
                adminMessageLabel.setText("Invalid input format.");
            }
        } else {
            adminMessageLabel.setText("Operation cancelled.");
        }
       
    }


}


