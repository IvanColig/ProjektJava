package com.example.projektjava;

import com.example.projektjava.entity.PC;
import com.example.projektjava.repository.PCRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class PCController {
    PCRepository pr = new PCRepository();
    @FXML
    private Button bckbtn;
    @FXML
    private Button updatebtn;
    @FXML
    private Button deletebtn;
    @FXML
    private Button createbtn;

    @FXML
    private TextField nameTextField;

    @FXML
    private TableView<PC> pcTableView;
    @FXML
    private TableColumn<PC, String> name;
    @FXML
    private TableColumn<PC, String> serialNumber;
    @FXML
    private TableColumn<PC, Integer> price;
    @FXML
    private TableColumn<PC, Integer> screenWidth;
    @FXML
    private TableColumn<PC, Integer> screenHeight;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField screenHeightTextField;

    @FXML
    private TextField screenWidthTextField;

    @FXML
    private TextField serialNumberTextField;

    @FXML
    void clearFields() {
        nameTextField.clear();
        serialNumberTextField.clear();
        priceTextField.clear();
        screenWidthTextField.clear();
        screenHeightTextField.clear();

        createbtn.setDisable(false);
    }

    @FXML
    void createPC() {

        if (nameTextField.getText().isEmpty() ||
                serialNumberTextField.getText().isEmpty() ||
                priceTextField.getText().isEmpty() ||
                screenWidthTextField.getText().isEmpty() ||
                screenHeightTextField.getText().isEmpty()) {
            showAlert("Empty Fields!", "Please fill in all fields.");
            return;
        }

        String name = nameTextField.getText();
        String serialNumber = serialNumberTextField.getText();
        int price, screenWidth, screenHeight;
        try {
            price = Integer.parseInt(priceTextField.getText());
            screenWidth = Integer.parseInt(screenWidthTextField.getText());
            screenHeight = Integer.parseInt(screenHeightTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Input!", "Price, Screen Width, and Screen Height must be numbers.");
            return;
        }

        PC pc = new PC(name, serialNumber, price, screenWidth, screenHeight);

        pr.create(pc);

        try {
            pr.writeToFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        clearFields();
        pcTableView.setItems(getPCs());
        showAlert("PC Created", "PC has been successfully created.");
    }

    @FXML
    void deletePC() {
        PC pc = pcTableView.getSelectionModel().getSelectedItem();
        if (pc == null) {
            return;
        }

        int selectedIndex = pcTableView.getSelectionModel().getSelectedIndex();
        pr.delete(selectedIndex);

        try {
            pr.writeToFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        pcTableView.getItems().remove(selectedIndex);
        clearFields();
        showAlert("PC Deleted", "PC has been successfully deleted.");
    }

    @FXML
    void updatePC() {
        PC pc = pcTableView.getSelectionModel().getSelectedItem();
        if (pc == null) {
            return;
        }

        if (nameTextField.getText().isEmpty() ||
                serialNumberTextField.getText().isEmpty() ||
                priceTextField.getText().isEmpty() ||
                screenWidthTextField.getText().isEmpty() ||
                screenHeightTextField.getText().isEmpty()) {
            showAlert("Empty Fields!", "Please fill in all fields.");
            return;
        }

        int price, screenWidth, screenHeight;
        try {
            price = Integer.parseInt(priceTextField.getText());
            screenWidth = Integer.parseInt(screenWidthTextField.getText());
            screenHeight = Integer.parseInt(screenHeightTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Input!", "Price, Screen Width, and Screen Height must be numbers.");
            return;
        }

        pc.setName(nameTextField.getText());
        pc.setSerialNumber(serialNumberTextField.getText());
        pc.setPrice(price);
        pc.setScreenWidth(screenWidth);
        pc.setScreenHeight(screenHeight);

        pr.update(pcTableView.getSelectionModel().getSelectedIndex(), pc);

        try {
            pr.writeToFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        clearFields();
        pcTableView.refresh();
        showAlert("PC Updated", "PC has been successfully updated.");
    }

    @FXML
    public void goBck() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("index.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) bckbtn.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void tableClicked() {
        PC pc = pcTableView.getSelectionModel().getSelectedItem();
        if (pc != null) {
            nameTextField.setText(pc.getName());
            serialNumberTextField.setText(pc.getSerialNumber());
            priceTextField.setText(String.valueOf(pc.getPrice()));
            screenWidthTextField.setText(String.valueOf(pc.getScreenWidth()));
            screenHeightTextField.setText(String.valueOf(pc.getScreenHeight()));
            deletebtn.setDisable(false);
            updatebtn.setDisable(false);
            createbtn.setDisable(true);
        }
    }

    @FXML
    private void initialize() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        serialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        screenWidth.setCellValueFactory(new PropertyValueFactory<>("screenWidth"));
        screenHeight.setCellValueFactory(new PropertyValueFactory<>("screenHeight"));

        pcTableView.setItems(getPCs());

        updatebtn.setDisable(true);
        deletebtn.setDisable(true);
        createbtn.setDisable(false);
    }


    private ObservableList<PC> getPCs() {
        ObservableList<PC> observablePCs = FXCollections.observableArrayList();
        try {
            pr.readFromFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        List<PC> pcs = pr.getAllPCs();
        observablePCs.addAll(pcs);
        return observablePCs;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
