package com.example.projektjava.repository;

import com.example.projektjava.Database;
import com.example.projektjava.entity.Laptop;

import java.util.List;

public class LaptopRepository extends DeviceRepository<Laptop> {

    public LaptopRepository() {
        this.database = Database.getInstance().getLaptops();
        this.filename = "Laptop_table.txt";
    }

    public List<Laptop> getAllLaptops() {
        return database;
    }

    @Override
    Laptop fromLine(String line) {
        String[] parts = line.split(",");
        String name = parts[0];
        String serialNumber = parts[1];
        int price = Integer.parseInt(parts[2]);
        int batteryCapacity = Integer.parseInt(parts[3]);
        int screenWidth = Integer.parseInt(parts[4]);
        int screenHeight = Integer.parseInt(parts[5]);
        return new Laptop(name, serialNumber, price, batteryCapacity, screenWidth, screenHeight);
    }

}
