package com.example.projektjava.repository;

import com.example.projektjava.Database;
import com.example.projektjava.entity.PC;

import java.util.List;

public class PCRepository extends DeviceRepository<PC> {

    public PCRepository() {
        this.database = Database.getInstance().getPCs();
        this.filename = "PC_table.txt";
    }

    public List<PC> getAllPCs() {
        return database;
    }

    @Override
    PC fromLine(String line) {
        String[] parts = line.split(",");
        String name = parts[0];
        String serialNumber = parts[1];
        int price = Integer.parseInt(parts[2]);
        int screenWidth = Integer.parseInt(parts[3]);
        int screenHeight = Integer.parseInt(parts[4]);
        return new PC(name, serialNumber, price, screenWidth, screenHeight);
    }

}
