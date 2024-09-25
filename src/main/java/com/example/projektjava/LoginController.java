package com.example.projektjava;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;

public class LoginController {

    @FXML
    private Label errorMessage;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Label successLabel;

    @FXML
    private TextField SUusernameField;

    @FXML
    private PasswordField SUpasswordField;

    @FXML
    void loginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticate(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("index.fxml"));
                Parent root = loader.load();
                MainController mainController = loader.getController();
                mainController.setUsername(username);
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            errorMessage.setText("Invalid username or password");
        }
    }

    private boolean authenticate(String username, String password) {
        try {
            Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("user.txt")).toURI());
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    void goToSignupButtonAction(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void signUpButtonAction() {
        String username = SUusernameField.getText();
        String password = SUpasswordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            try {
                Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("user.txt")).toURI());
                try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                    writer.write(username + ":" + password + "\n");
                    successLabel.setText("Signup successful, you can now login");
                }
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
                successLabel.setText("Error occurred during signup");
            }
        } else {
            successLabel.setText("Username and password cannot be empty");
        }
    }



    @FXML
    void backToLoginButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) SUusernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
