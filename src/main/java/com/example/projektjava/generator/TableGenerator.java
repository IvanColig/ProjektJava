package com.example.projektjava.generator;

import com.example.projektjava.entity.Laptop;
import com.example.projektjava.entity.PC;
import com.example.projektjava.entity.Router;

import java.util.List;

public class TableGenerator {

    public static List<Laptop> generateLaptops() {
        return List.of(
                new Laptop("HP", "12345678", 3000, 12000, 1920, 1080),
                new Laptop("DELL", "23456789", 3500, 14000, 1920, 1080),
                new Laptop("LENOVO", "34567890", 2800, 9500, 1920, 1080),
                new Laptop("HP", "45678901", 2300, 7000, 1080, 720),
                new Laptop("DELL", "56789012", 2250, 7500, 1080, 720)
        );
    }

    public static List<PC> generatePCs() {
        return List.of(
                new PC("Optiplex", "12345678", 5000, 1920, 1080),
                new PC("Alienware", "23456789", 4500, 1920, 1080),
                new PC("Sunshine", "34567890", 4999, 1920, 1080),
                new PC("HP", "45678901", 3000, 1080, 720),
                new PC("MSI", "56789012", 3300, 1080, 720)
        );
    }

    public static List<Router> generateRouters() {
        return List.of(
                new Router("Mini-250", "12345678", 100, "Asus"),
                new Router("Home 12L", "23456789", 120, "Huawei"),
                new Router("Galaxy route 4+", "34567890", 250, "Samsung"),
                new Router("Bad 12", "45678901", 80, "HP"),
                new Router("Intel Router", "56789012", 110, "Intel")
        );
    }

}
