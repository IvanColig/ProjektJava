package com.example.projektjava;

import com.example.projektjava.entity.Laptop;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DeviceApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DeviceApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Devices");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        /*Laptop laptop1 = new Laptop("L1", "123", 123,123,123,123);
        Laptop laptop2 = new Laptop("L2", "124", 122,123,123,123);
        Laptop laptop3 = new Laptop("L1", "123", 123,123,123,123);

        System.out.println("laptop1 equals laptop2: " + laptop1.equals(laptop2));
        System.out.println("laptop1 equals laptop3: " + laptop1.equals(laptop3));*/

        launch();
    }
}