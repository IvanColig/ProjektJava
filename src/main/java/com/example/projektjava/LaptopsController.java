package com.example.projektjava;

import com.example.projektjava.entity.Laptop;
import com.example.projektjava.repository.LaptopRepository;
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

public class LaptopsController {
    LaptopRepository lr = new LaptopRepository();
    @FXML
    private Button bckbtn;

    @FXML
    private Button updatebtn;

    @FXML
    private Button deletebtn;

    @FXML
    private Button createbtn;

    @FXML
    private TextField batteryCapacityTextField;

    @FXML
    private TableView<Laptop> laptopTableView;
    @FXML
    private TableColumn<Laptop, String> name;
    @FXML
    private TableColumn<Laptop, String> serialNumber;
    @FXML
    private TableColumn<Laptop, Integer> price;
    @FXML
    private TableColumn<Laptop, Integer> batteryCapacity;
    @FXML
    private TableColumn<Laptop, Integer> screenWidth;
    @FXML
    private TableColumn<Laptop, Integer> screenHeight;

    @FXML
    private TextField nameTextField;

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
        batteryCapacityTextField.clear();
        screenWidthTextField.clear();
        screenHeightTextField.clear();

        createbtn.setDisable(false);
    }

    @FXML
    void createLaptop() {
        if (nameTextField.getText().isEmpty() ||
                serialNumberTextField.getText().isEmpty() ||
                priceTextField.getText().isEmpty() ||
                batteryCapacityTextField.getText().isEmpty() ||
                screenWidthTextField.getText().isEmpty() ||
                screenHeightTextField.getText().isEmpty()) {
            showAlert("Empty fields!", "Please fill in all the fields.");
            return;
        }

        String name = nameTextField.getText();
        String serialNumber = serialNumberTextField.getText();
        int price, batteryCapacity, screenWidth, screenHeight;
        try {
            price = Integer.parseInt(priceTextField.getText());
            batteryCapacity = Integer.parseInt(batteryCapacityTextField.getText());
            screenWidth = Integer.parseInt(screenWidthTextField.getText());
            screenHeight = Integer.parseInt(screenHeightTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid input!", "Price, Battery Capacity, Screen Width, and Screen Height must be numbers.");
            return;
        }

        Laptop laptop = new Laptop(name, serialNumber, price, batteryCapacity, screenWidth, screenHeight);

        lr.create(laptop);

        try {
            lr.writeToFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        clearFields();
        laptopTableView.setItems(getLaptops());
        showAlert("Laptop Created", "Laptop has been successfully created.");
    }

    @FXML
    void deleteLaptop() {
        Laptop laptop = laptopTableView.getSelectionModel().getSelectedItem();
        if (laptop == null) {
            return;
        }

        int selectedIndex = laptopTableView.getSelectionModel().getSelectedIndex();
        lr.delete(selectedIndex);

        try {
            lr.writeToFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        laptopTableView.getItems().remove(selectedIndex);
        clearFields();
        showAlert("Laptop Deleted", "Laptop has been successfully deleted.");
    }


    @FXML
    void updateLaptop() {
        Laptop laptop = laptopTableView.getSelectionModel().getSelectedItem();
        if (laptop == null) {
            return;
        }

        if (nameTextField.getText().isEmpty() ||
                serialNumberTextField.getText().isEmpty() ||
                priceTextField.getText().isEmpty() ||
                batteryCapacityTextField.getText().isEmpty() ||
                screenWidthTextField.getText().isEmpty() ||
                screenHeightTextField.getText().isEmpty()) {
            showAlert("Empty fields!", "Please fill in all the fields.");
            return;
        }


        int price, batteryCapacity, screenWidth, screenHeight;
        try {
            price = Integer.parseInt(priceTextField.getText());
            batteryCapacity = Integer.parseInt(batteryCapacityTextField.getText());
            screenWidth = Integer.parseInt(screenWidthTextField.getText());
            screenHeight = Integer.parseInt(screenHeightTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid input!", "Price, Battery Capacity, Screen Width, and Screen Height must be numbers.");
            return;
        }

        laptop.setName(nameTextField.getText());
        laptop.setSerialNumber(serialNumberTextField.getText());
        laptop.setPrice(price);
        laptop.setBatteryCapacity(batteryCapacity);
        laptop.setScreenWidth(screenWidth);
        laptop.setScreenHeight(screenHeight);

        lr.update(laptopTableView.getSelectionModel().getSelectedIndex(), laptop);

        try {
            lr.writeToFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        clearFields();
        laptopTableView.refresh();
        showAlert("Laptop Updated", "Laptop has been successfully updated.");
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
        Laptop laptop = laptopTableView.getSelectionModel().getSelectedItem();
        if (laptop != null) {
            nameTextField.setText(laptop.getName());
            serialNumberTextField.setText(laptop.getSerialNumber());
            priceTextField.setText(String.valueOf(laptop.getPrice()));
            batteryCapacityTextField.setText(String.valueOf(laptop.getBatteryCapacity()));
            screenWidthTextField.setText(String.valueOf(laptop.getScreenWidth()));
            screenHeightTextField.setText(String.valueOf(laptop.getScreenHeight()));

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
        batteryCapacity.setCellValueFactory(new PropertyValueFactory<>("batteryCapacity"));
        screenWidth.setCellValueFactory(new PropertyValueFactory<>("screenWidth"));
        screenHeight.setCellValueFactory(new PropertyValueFactory<>("screenHeight"));

        laptopTableView.setItems(getLaptops());
    }


    private ObservableList<Laptop> getLaptops() {
        ObservableList<Laptop> observableLaptops = FXCollections.observableArrayList();
        try {
            lr.readFromFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        List<Laptop> laptops = lr.getAllLaptops();
        observableLaptops.addAll(laptops);
        return observableLaptops;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
