package com.example.projektjava;

import com.example.projektjava.entity.Router;
import com.example.projektjava.repository.RouterRepository;
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

public class RouterController {
    RouterRepository rr = new RouterRepository();
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
    private TextField networkCardTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TableView<Router> routerTableView;
    @FXML
    private TableColumn<Router, String> name;
    @FXML
    private TableColumn<Router, String> serialNumber;
    @FXML
    private TableColumn<Router, Integer> price;
    @FXML
    private TableColumn<Router, String> networkCard;

    @FXML
    private TextField serialNumberTextField;

    @FXML
    void clearFields() {
        nameTextField.clear();
        serialNumberTextField.clear();
        priceTextField.clear();
        networkCardTextField.clear();

        createbtn.setDisable(false);
    }


    @FXML
    void createRouter() {
        if (nameTextField.getText().isEmpty() ||
                serialNumberTextField.getText().isEmpty() ||
                priceTextField.getText().isEmpty() ||
                networkCardTextField.getText().isEmpty()) {
            showAlert("Empty fields!", "Please fill in all the fields.");
            return;
        }

        String name = nameTextField.getText();
        String serialNumber = serialNumberTextField.getText();
        String networkCard = networkCardTextField.getText();
        int price;
        try {
            price = Integer.parseInt(priceTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid input!", "Price must be a number.");
            return;
        }

        Router router = new Router(name, serialNumber, price, networkCard);

        rr.create(router);

        try {
            rr.writeToFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        clearFields();
        routerTableView.setItems(getRouters());
        showAlert("Router Created", "Router has been successfully created.");
    }


    @FXML
    void deleteRouter() {
        Router router = routerTableView.getSelectionModel().getSelectedItem();
        if (router == null) {
            return;
        }

        int selectedIndex = routerTableView.getSelectionModel().getSelectedIndex();
        rr.delete(selectedIndex);

        try {
            rr.writeToFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        routerTableView.getItems().remove(selectedIndex);
        clearFields();
        showAlert("Router Deleted", "Router has been successfully deleted.");
    }


    @FXML
    void updateRouter() {
        Router router = routerTableView.getSelectionModel().getSelectedItem();
        if (router == null) {
            return;
        }

        if (nameTextField.getText().isEmpty() ||
                serialNumberTextField.getText().isEmpty() ||
                priceTextField.getText().isEmpty() ||
                networkCardTextField.getText().isEmpty()) {
            showAlert("Empty fields!", "Please fill in all the fields.");
            return;
        }

        int price;
        try {
            price = Integer.parseInt(priceTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid Input!", "Price must be a number.");
            return;
        }

        router.setName(nameTextField.getText());
        router.setSerialNumber(serialNumberTextField.getText());
        router.setPrice(price);
        router.setNetworkCard(networkCardTextField.getText());

        rr.update(routerTableView.getSelectionModel().getSelectedIndex(), router);

        try {
            rr.writeToFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        clearFields();
        routerTableView.refresh();
        showAlert("Router Updated", "Router has been successfully updated.");
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
        Router router = routerTableView.getSelectionModel().getSelectedItem();
        if (router != null) {
            nameTextField.setText(router.getName());
            serialNumberTextField.setText(router.getSerialNumber());
            priceTextField.setText(String.valueOf(router.getPrice()));
            networkCardTextField.setText(router.getNetworkCard());
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
        networkCard.setCellValueFactory(new PropertyValueFactory<>("networkCard"));

        routerTableView.setItems(getRouters());

        updatebtn.setDisable(true);
        deletebtn.setDisable(true);
        createbtn.setDisable(false);
    }

    private ObservableList<Router> getRouters() {
        ObservableList<Router> observableRouters = FXCollections.observableArrayList();
        try {
            rr.readFromFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        List<Router> routers = rr.getAllRouters();
        observableRouters.addAll(routers);
        return observableRouters;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
