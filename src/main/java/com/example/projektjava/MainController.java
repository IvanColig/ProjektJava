package com.example.projektjava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class MainController {

    @FXML
    private Button buttonPC;

    @FXML
    private Button buttonRouter;

    @FXML
    private Button buttonLaptop;

    @FXML
    private VBox root;

    @FXML
    private Label usernameLabel;

    public void setUsername(String username) {
        usernameLabel.setText("Welcome, " + username + "!");
    }

    @FXML
    private void initialize() {
        setButtonStyle(buttonPC);
        setButtonStyle(buttonRouter);
        setButtonStyle(buttonLaptop);
    }

    private void setButtonStyle(Button button) {
        button.setStyle("-fx-background-color: #4caf50; -fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: #388e3c; -fx-border-width: 1px; -fx-text-fill: white;");

        button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: #388e3c; -fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: #388e3c; -fx-border-width: 1px; -fx-text-fill: white;"));

        button.setOnMouseExited(event -> button.setStyle("-fx-background-color: #4caf50; -fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: #388e3c; -fx-border-width: 1px; -fx-text-fill: white;"));
    }

    @FXML
    private void switchScene(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        String fxmlPath = switch (buttonId) {
            case "buttonPC" -> "pcs.fxml";
            case "buttonRouter" -> "routers.fxml";
            case "buttonLaptop" -> "laptops.fxml";
            default -> "";
        };

        try {
            root.getScene().setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
