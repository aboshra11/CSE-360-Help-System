package phase1GUI;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.Arrays;
import javafx.scene.control.Label;

import database.HelpArticle;
import database.DatabaseHelper;
import javafx.geometry.Insets;

/**
 * AdminHelpArticleManager handles the UI for creating, updating, deleting, backing up, and restoring help articles.
 */
public class AdminHelpArticleManager {

    private Admin adminUser; // Reference to the admin user for operations
    private Label messageLabel; // Label to display messages to the user
    private MainGUI mainGUI; // Reference to MainGUI instance

    public AdminHelpArticleManager(Admin adminUser, Label messageLabel, MainGUI mainGUI) {
        this.adminUser = adminUser;
        this.messageLabel = messageLabel;
        this.mainGUI = mainGUI;
    }

    /**
     * Show page for creating a new help article.
     */
    public void showCreateArticlePage(Stage primaryStage) {
        primaryStage.setTitle("Create Help Article");

        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField();
        Label authorsLabel = new Label("Authors:");
        TextField authorsField = new TextField();
        Label abstractLabel = new Label("Abstract:");
        TextField abstractField = new TextField();
        Label keywordsLabel = new Label("Keywords:");
        TextField keywordsField = new TextField();
        Label bodyLabel = new Label("Body:");
        TextField bodyField = new TextField();
        Label referencesLabel = new Label("References:");
        TextField referencesField = new TextField();
        Label groupsLabel = new Label("Groups (comma separated):");
        TextField groupsField = new TextField();

        // Additional fields for level, shortDescription, and systemInfo
        Label levelLabel = new Label("Level:");
        TextField levelField = new TextField();
        Label shortDescLabel = new Label("Short Description:");
        TextField shortDescField = new TextField();
        Label systemInfoLabel = new Label("System Info:");
        TextField systemInfoField = new TextField();

        Button submitButton = new Button("Submit");

        submitButton.setOnAction(e -> {
            try {
                adminUser.createHelpArticle(
                        titleField.getText().toCharArray(),
                        authorsField.getText().toCharArray(),
                        abstractField.getText().toCharArray(),
                        keywordsField.getText().toCharArray(),
                        bodyField.getText().toCharArray(),
                        referencesField.getText().toCharArray(),
                        List.of(groupsField.getText().split(",")),
                        levelField.getText(),
                        shortDescField.getText(),
                        systemInfoField.getText()
                );
                messageLabel.setText("Help article created successfully.");
                
                // Close the createStage to return to the initial admin page
                primaryStage.close();

            } catch (Exception ex) {
                messageLabel.setText("Error creating article: " + ex.getMessage());
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(titleLabel, 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(authorsLabel, 0, 1);
        grid.add(authorsField, 1, 1);
        grid.add(abstractLabel, 0, 2);
        grid.add(abstractField, 1, 2);
        grid.add(keywordsLabel, 0, 3);
        grid.add(keywordsField, 1, 3);
        grid.add(bodyLabel, 0, 4);
        grid.add(bodyField, 1, 4);
        grid.add(referencesLabel, 0, 5);
        grid.add(referencesField, 1, 5);
        grid.add(groupsLabel, 0, 6);
        grid.add(groupsField, 1, 6);
        grid.add(levelLabel, 0, 7);
        grid.add(levelField, 1, 7);
        grid.add(shortDescLabel, 0, 8);
        grid.add(shortDescField, 1, 8);
        grid.add(systemInfoLabel, 0, 9);
        grid.add(systemInfoField, 1, 9);
        grid.add(submitButton, 1, 10);

        primaryStage.setScene(new Scene(grid, 500, 600));
    }


    /**
     * Show page for updating an existing help article.
     */
    public void showUpdateArticlePage(Stage primaryStage) {
        primaryStage.setTitle("Update Help Article");

        Label articleIdLabel = new Label("Article ID:");
        TextField articleIdField = new TextField();
        Label titleLabel = new Label("Title:");
        TextField titleField = new TextField();
        Label authorsLabel = new Label("Authors:");
        TextField authorsField = new TextField();
        Label abstractLabel = new Label("Abstract:");
        TextField abstractField = new TextField();
        Label keywordsLabel = new Label("Keywords:");
        TextField keywordsField = new TextField();
        Label bodyLabel = new Label("Body:");
        TextField bodyField = new TextField();
        Label referencesLabel = new Label("References:");
        TextField referencesField = new TextField();
        Label groupsLabel = new Label("Groups (comma separated):");
        TextField groupsField = new TextField();

        // Additional fields for level, shortDescription, and systemInfo
        Label levelLabel = new Label("Level:");
        TextField levelField = new TextField();
        Label shortDescLabel = new Label("Short Description:");
        TextField shortDescField = new TextField();
        Label systemInfoLabel = new Label("System Info:");
        TextField systemInfoField = new TextField();

        Button submitButton = new Button("Update");

        submitButton.setOnAction(e -> {
            try {
                long articleId = Long.parseLong(articleIdField.getText());
                adminUser.updateHelpArticle(
                        articleId,
                        titleField.getText().toCharArray(),
                        authorsField.getText().toCharArray(),
                        abstractField.getText().toCharArray(),
                        keywordsField.getText().toCharArray(),
                        bodyField.getText().toCharArray(),
                        referencesField.getText().toCharArray(),
                        List.of(groupsField.getText().split(",")),
                        levelField.getText(),
                        shortDescField.getText(),
                        systemInfoField.getText()
                );
                messageLabel.setText("Help article updated successfully.");

                // Close the updateStage to return to the initial admin page
                primaryStage.close();

            } catch (Exception ex) {
                messageLabel.setText("Error updating article: " + ex.getMessage());
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(articleIdLabel, 0, 0);
        grid.add(articleIdField, 1, 0);
        grid.add(titleLabel, 0, 1);
        grid.add(titleField, 1, 1);
        grid.add(authorsLabel, 0, 2);
        grid.add(authorsField, 1, 2);
        grid.add(abstractLabel, 0, 3);
        grid.add(abstractField, 1, 3);
        grid.add(keywordsLabel, 0, 4);
        grid.add(keywordsField, 1, 4);
        grid.add(bodyLabel, 0, 5);
        grid.add(bodyField, 1, 5);
        grid.add(referencesLabel, 0, 6);
        grid.add(referencesField, 1, 6);
        grid.add(groupsLabel, 0, 7);
        grid.add(groupsField, 1, 7);
        grid.add(levelLabel, 0, 8);
        grid.add(levelField, 1, 8);
        grid.add(shortDescLabel, 0, 9);
        grid.add(shortDescField, 1, 9);
        grid.add(systemInfoLabel, 0, 10);
        grid.add(systemInfoField, 1, 10);
        grid.add(submitButton, 1, 11);

        primaryStage.setScene(new Scene(grid, 500, 600));
    }


    /**
     * Show page for deleting a help article.
     */
    public void showDeleteArticlePage(Stage primaryStage) {
        primaryStage.setTitle("Delete Help Article");

        Label articleIdLabel = new Label("Article ID:");
        TextField articleIdField = new TextField();
        Button deleteButton = new Button("Delete");

        deleteButton.setOnAction(e -> {
            try {
                long articleId = Long.parseLong(articleIdField.getText());
                adminUser.deleteHelpArticle(articleId);
                messageLabel.setText("Help article deleted successfully.");
            } catch (Exception ex) {
                messageLabel.setText("Error deleting article: " + ex.getMessage());
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(articleIdLabel, 0, 0);
        grid.add(articleIdField, 1, 0);
        grid.add(deleteButton, 1, 1);

        primaryStage.setScene(new Scene(grid, 300, 200));
    }

    /**
     * List all help articles.
     */
    public void listHelpArticles() {
        try {
            adminUser.listHelpArticles();
        } catch (Exception ex) {
            messageLabel.setText("Error listing articles: " + ex.getMessage());
        }
    }

    /**
     * Show page for backing up help articles by group.
     */
    public void showBackupArticlesPage(Stage primaryStage) {
        primaryStage.setTitle("Backup Articles");

        Label folderPathLabel = new Label("Folder Path:");
        TextField folderPathField = new TextField();
        Label fileNameLabel = new Label("File Name:");
        TextField fileNameField = new TextField();
        Label groupsLabel = new Label("Groups (comma separated):");
        TextField groupsField = new TextField();
        Button backupButton = new Button("Backup");

        backupButton.setOnAction(e -> {
            try {
                adminUser.backupHelpArticlesByGroup(
                        folderPathField.getText(),
                        fileNameField.getText(),
                        List.of(groupsField.getText().split(","))
                );
                messageLabel.setText("Articles backed up successfully.");
            } catch (Exception ex) {
                messageLabel.setText("Error backing up articles: " + ex.getMessage());
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(folderPathLabel, 0, 0);
        grid.add(folderPathField, 1, 0);
        grid.add(fileNameLabel, 0, 1);
        grid.add(fileNameField, 1, 1);
        grid.add(groupsLabel, 0, 2);
        grid.add(groupsField, 1, 2);
        grid.add(backupButton, 1, 3);

        primaryStage.setScene(new Scene(grid, 400, 300));
    }

    /**
     * Show page for restoring help articles.
     */
    public void showRestoreArticlesPage(Stage primaryStage) {
        primaryStage.setTitle("Restore Articles");

        Label filePathLabel = new Label("Backup File Path:");
        TextField filePathField = new TextField();
        Label mergeLabel = new Label("Merge with existing articles?");
        CheckBox mergeCheckBox = new CheckBox();
        Button restoreButton = new Button("Restore");

        restoreButton.setOnAction(e -> {
            try {
                adminUser.restoreHelpArticles(filePathField.getText(), mergeCheckBox.isSelected());
                messageLabel.setText("Articles restored successfully.");
            } catch (Exception ex) {
                messageLabel.setText("Error restoring articles: " + ex.getMessage());
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.add(filePathLabel, 0, 0);
        grid.add(filePathField, 1, 0);
        grid.add(mergeLabel, 0, 1);
        grid.add(mergeCheckBox, 1, 1);
        grid.add(restoreButton, 1, 2);

        primaryStage.setScene(new Scene(grid, 400, 200));
    }
    
    private void openDeleteArticleWindow(Admin adminUser) {
        Stage deleteStage = new Stage();
        VBox layout = new VBox(10);

        TextField articleNumberField = new TextField();
        articleNumberField.setPromptText("Enter article ID to delete");

        Button btnDelete = new Button("Delete");

        // Handle delete button action
        btnDelete.setOnAction(event -> {
            try {
                int articleId = Integer.parseInt(articleNumberField.getText());
                
                // Call deleteHelpArticle on the passed Admin instance (adminUser)
                adminUser.deleteHelpArticle(articleId);
                
                showAlert(Alert.AlertType.INFORMATION, "Success", "Article deleted successfully.");
                deleteStage.close();
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error deleting article: " + e.getMessage());
            }
        });

        layout.getChildren().addAll(articleNumberField, btnDelete);

        Scene scene = new Scene(layout, 400, 300);
        deleteStage.setScene(scene);
        deleteStage.setTitle("Delete Article");
        deleteStage.show();
    }

    
    private void openViewArticleWindow(Admin adminUser) {
        Stage viewStage = new Stage();
        VBox layout = new VBox(10);

        TextField articleNumberField = new TextField();
        articleNumberField.setPromptText("Enter article ID to view");

        Button btnView = new Button("View Details");

        // Handle view button action
        btnView.setOnAction(event -> {
            try {
                int articleId = Integer.parseInt(articleNumberField.getText());
                
                // Get the article by ID using articleManager
                HelpArticle article = adminUser.getArticleById(articleId);

                if (article != null) {
                    // Display article details in a label
                    Label articleDetails = new Label(
                        "ID: " + article.getId() + "\n" +
                        "Title: " + new String(article.getTitle()) + "\n" +
                        "Authors: " + new String(article.getAuthors()) + "\n" +
                        "Abstract: " + new String(article.getAbstractText()) + "\n" +
                        "Keywords: " + new String(article.getKeywords()) + "\n" +
                        "Body: " + new String(article.getBody()) + "\n" +
                        "References: " + new String(article.getReferences())
                    );

                    // Add the article details to the layout
                    layout.getChildren().add(articleDetails);
                } else {
                    showAlert(Alert.AlertType.WARNING, "Not Found", "Article with ID " + articleId + " not found.");
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error viewing article: " + e.getMessage());
            }
        });

        layout.getChildren().addAll(articleNumberField, btnView);

        Scene scene = new Scene(layout, 400, 300);
        viewStage.setScene(scene);
        viewStage.setTitle("View Article");
        viewStage.show();
    }

    
    private void openCreateArticleWindow(Admin adminUser, Stage primaryStage) {
        Stage createStage = new Stage();
        VBox layout = new VBox(10);

        TextField titleField = new TextField();
        titleField.setPromptText("Title");

        TextField authorsField = new TextField();
        authorsField.setPromptText("Authors");

        TextField abstractField = new TextField();
        abstractField.setPromptText("Abstract");

        TextField keywordsField = new TextField();
        keywordsField.setPromptText("Keywords");

        TextArea bodyField = new TextArea();
        bodyField.setPromptText("Body");

        TextArea referencesField = new TextArea();
        referencesField.setPromptText("References");

        TextField groupsField = new TextField();
        groupsField.setPromptText("Groups (comma separated)");

        TextField levelField = new TextField();
        levelField.setPromptText("Level (e.g., Beginner, Intermediate)");

        TextField shortDescriptionField = new TextField();
        shortDescriptionField.setPromptText("Short Description");

        TextField systemInfoField = new TextField();
        systemInfoField.setPromptText("System Information");

        Button btnSubmit = new Button("Submit");

        btnSubmit.setOnAction(event -> {
            try {
                if (titleField.getText().isEmpty() || authorsField.getText().isEmpty() || bodyField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Validation Error", "Title, authors, and body are required!");
                    return;
                }

                char[] title = titleField.getText().toCharArray();
                char[] authors = authorsField.getText().toCharArray();
                char[] abstractText = abstractField.getText().toCharArray();
                char[] keywords = keywordsField.getText().toCharArray();
                char[] body = bodyField.getText().toCharArray();
                char[] references = referencesField.getText().toCharArray();

                List<String> groups = Arrays.asList(groupsField.getText().split(","));
                String level = levelField.getText();
                String shortDescription = shortDescriptionField.getText();
                String systemInfo = systemInfoField.getText();

                adminUser.createHelpArticle(title, authors, abstractText, keywords, body, references, groups, level, shortDescription, systemInfo);

                showAlert(Alert.AlertType.INFORMATION, "Success", "Article created successfully.");
                createStage.close();

                mainGUI.showAdminPage(primaryStage, this); // Show the admin page after closing

            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error creating article: " + e.getMessage());
            }
        });

        layout.getChildren().addAll(
            new Label("Create New Article"), 
            titleField, authorsField, abstractField, keywordsField, 
            bodyField, referencesField, groupsField, 
            levelField, shortDescriptionField, systemInfoField, 
            btnSubmit
        );

        Scene scene = new Scene(layout, 400, 600);
        createStage.setScene(scene);
        createStage.setTitle("Create New Article");
        createStage.show();
    }

    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showSuccessPopup(String message, String filePath) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(message);
        alert.setContentText("File saved at: " + filePath);
        alert.showAndWait();
    }
    
    public Admin getAdminUser() {
        return adminUser;
    }
    
 // In AdminHelpArticleManager.java
    public void resetAccount(Stage primaryStage, Label messageLabel) {
        Stage resetStage = new Stage();
        resetStage.setTitle("Reset User Account");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField emailField = new TextField();
        emailField.setPromptText("Enter user email");

        Button resetButton = new Button("Reset Account");
        resetButton.setOnAction(event -> {
            String email = emailField.getText();
            if (email.isEmpty()) {
                messageLabel.setText("Please enter an email.");
                return;
            }

            try {
                // Retrieve the user from the database by email
                User user = getAdminUser().getUserByEmail(email); // Assume getUserByEmail is implemented in Admin
                if (user != null) {
                    getAdminUser().resetUserAccount(user); // Use your existing resetUserAccount(User user) method
                    messageLabel.setText("Account reset successfully for " + email);
                    resetStage.close();
                } else {
                    messageLabel.setText("User with email " + email + " not found.");
                }
            } catch (Exception e) {
                messageLabel.setText("Error resetting account: " + e.getMessage());
            }
        });

        layout.getChildren().addAll(new Label("Reset User Account"), emailField, resetButton);
        Scene scene = new Scene(layout, 300, 200);
        resetStage.setScene(scene);
        resetStage.show();
    }
    
 // In AdminHelpArticleManager.java
    public void deleteAccount(Stage primaryStage, Label messageLabel) {
        Stage deleteStage = new Stage();
        deleteStage.setTitle("Delete User Account");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField emailField = new TextField();
        emailField.setPromptText("Enter user email");

        Button deleteButton = new Button("Delete Account");
        deleteButton.setOnAction(event -> {
            String email = emailField.getText();
            if (email.isEmpty()) {
                messageLabel.setText("Please enter an email.");
                return;
            }

            try {
                // Call deleteUser in the Admin class
                getAdminUser().deleteUser(email); // Ensure getAdminUser() is accessible and returns Admin instance
                messageLabel.setText("User account with email " + email + " deleted successfully.");
                deleteStage.close();
            } catch (Exception e) {
                messageLabel.setText("Error deleting account: " + e.getMessage());
            }
        });

        layout.getChildren().addAll(new Label("Delete User Account"), emailField, deleteButton);
        Scene scene = new Scene(layout, 300, 200);
        deleteStage.setScene(scene);
        deleteStage.show();
    }
    
 // In AdminHelpArticleManager.java
    public void listUserAccounts(Label messageLabel) {
        try {
            // Clear previous messages
            StringBuilder userAccounts = new StringBuilder("User Accounts:\n");
            
            // Call listUserAccounts from the Admin class
            List<User> users = getAdminUser().listUserAccounts(); // Ensure getAdminUser() returns an Admin instance

            // Append user information to display
            for (User user : users) {
                userAccounts.append("User: ").append(user.getUsername())
                            .append(", Roles: ").append(user.getRoles()).append("\n");
            }

            // Set the messageLabel with the user account details
            messageLabel.setText(userAccounts.toString());
        } catch (Exception e) {
            messageLabel.setText("Error listing user accounts: " + e.getMessage());
        }
    }
    
 // In AdminHelpArticleManager.java
    public void addRemoveRoles(Stage primaryStage, Label messageLabel) {
        Stage roleStage = new Stage();
        roleStage.setTitle("Add/Remove Roles");

        VBox layout = new VBox(10);

        // UI Elements
        TextField emailField = new TextField();
        emailField.setPromptText("User Email");

        TextField roleField = new TextField();
        roleField.setPromptText("Role");

        Button addRoleButton = new Button("Add Role");
        Button removeRoleButton = new Button("Remove Role");

        // Add Role Action
        addRoleButton.setOnAction(event -> {
            try {
                String email = emailField.getText();
                String role = roleField.getText();

                if (!email.isEmpty() && !role.isEmpty()) {
                    getAdminUser().addRoleToUser(email, role);  // Call add role method in Admin
                    messageLabel.setText("Role added successfully to user: " + email);
                } else {
                    messageLabel.setText("Please enter both email and role.");
                }
            } catch (Exception e) {
                messageLabel.setText("Error adding role: " + e.getMessage());
            }
        });

        // Remove Role Action
        removeRoleButton.setOnAction(event -> {
            try {
                String email = emailField.getText();
                String role = roleField.getText();

                if (!email.isEmpty() && !role.isEmpty()) {
                    getAdminUser().removeRoleFromUser(email, role);  // Call remove role method in Admin
                    messageLabel.setText("Role removed successfully from user: " + email);
                } else {
                    messageLabel.setText("Please enter both email and role.");
                }
            } catch (Exception e) {
                messageLabel.setText("Error removing role: " + e.getMessage());
            }
        });

        // Layout setup
        layout.getChildren().addAll(
            new Label("Add/Remove User Role"),
            emailField, roleField,
            addRoleButton, removeRoleButton,
            messageLabel
        );

        Scene scene = new Scene(layout, 300, 250);
        roleStage.setScene(scene);
        roleStage.show();
    }





    
}
