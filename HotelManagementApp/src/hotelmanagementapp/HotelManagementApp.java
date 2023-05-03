import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class HotelManagementApp extends Application {

    private Map<String, String> userCredentials = new HashMap<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hotel Management App");

        // Login Page
        Label lblLoginUsername = new Label("Username:");
        TextField txtLoginUsername = new TextField();

        Label lblLoginPassword = new Label("Password:");
        PasswordField txtLoginPassword = new PasswordField();

        Button btnLogin = new Button("Login");
        btnLogin.setOnAction(e -> {
            String username = txtLoginUsername.getText();
            String password = hashPassword(txtLoginPassword.getText());
            if (authenticateUser(username, password)) {
                showAlert("Login Successful", "Welcome back, " + username + "!");
                txtLoginUsername.clear();
                txtLoginPassword.clear();
            } else {
                showAlert("Login Failed", "Invalid username or password.");
            }
        });

        Button btnRegister = new Button("Register");
        btnRegister.setOnAction(e -> {
            showRegistrationPage();
        });

        VBox loginPage = new VBox(10);
        loginPage.setPadding(new Insets(10));
        loginPage.getChildren().addAll(lblLoginUsername, txtLoginUsername, lblLoginPassword, txtLoginPassword, btnLogin, btnRegister);

        // Registration Page
        Label lblRegUsername = new Label("Username:");
        TextField txtRegUsername = new TextField();

        Label lblRegPassword = new Label("Password:");
        PasswordField txtRegPassword = new PasswordField();

        Button btnRegisterUser = new Button("Register");
        btnRegisterUser.setOnAction(e -> {
            String username = txtRegUsername.getText();
            String password = hashPassword(txtRegPassword.getText());
            registerUser(username, password);
            showAlert("Registration Successful", "User registered successfully!");
            txtRegUsername.clear();
            txtRegPassword.clear();
        });

        Button btnBackToLogin = new Button("Back to Login");
        btnBackToLogin.setOnAction(e -> {
            primaryStage.getScene().setRoot(loginPage);
        });

        VBox registrationPage = new VBox(10);
        registrationPage.setPadding(new Insets(10));
        registrationPage.getChildren().addAll(lblRegUsername, txtRegUsername, lblRegPassword, txtRegPassword, btnRegisterUser, btnBackToLogin);

        primaryStage.setScene(new Scene(loginPage, 500, 500));
        primaryStage.show();
    }

    private void showRegistrationPage() {
        Stage stage = new Stage();
        stage.setTitle("Registration");
        stage.setScene(new Scene(getRegistrationPage(), 500, 500));
        stage.show();
    }

    private Pane getRegistrationPage() {
        Label lblRegUsername = new Label("Username:");
        TextField txtRegUsername = new TextField();

        Label lblRegPassword = new Label("Password:");
        PasswordField txtRegPassword = new PasswordField();

        Button btnRegisterUser = new Button("Register");
        btnRegisterUser.setOnAction(e -> {
            String username = txtRegUsername.getText();
            String password = hashPassword(txtRegPassword.getText());
            registerUser(username, password);
            showAlert("Registration Successful", "User registered successfully!");
            txtRegUsername.clear();
            txtRegPassword.clear();
        });

        VBox registrationPage = new VBox(10);
        registrationPage.setPadding(new Insets(10));
        registrationPage.getChildren().addAll(lblRegUsername, txtRegUsername, lblRegPassword, txtRegPassword, btnRegisterUser);

        return registrationPage;
    }

    private void registerUser(String username, String password) {
        userCredentials.put(username, password);
    }

    private boolean authenticateUser(String username, String password) {
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

