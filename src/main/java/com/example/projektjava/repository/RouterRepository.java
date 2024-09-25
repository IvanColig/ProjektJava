package com.example.projektjava.repository;

import com.example.projektjava.Database;
import com.example.projektjava.entity.Router;

import java.util.List;

public class RouterRepository extends DeviceRepository<Router> {

    public RouterRepository() {
        this.database = Database.getInstance().getRouters();
        this.filename = "Router_table.txt";
    }

    public List<Router> getAllRouters() {
        return database;
    }

    @Override
    Router fromLine(String line) {
        String[] parts = line.split(",");
        String name = parts[0];
        String serialNumber = parts[1];
        int price = Integer.parseInt(parts[2]);
        String networkCard = parts[3];
        return new Router(name, serialNumber, price, networkCard);
    }

}
