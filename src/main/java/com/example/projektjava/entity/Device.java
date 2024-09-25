package com.example.projektjava.entity;

import java.util.Objects;

public abstract class Device implements Savable {

    private String name;
    private String serialNumber;
    private int price;

    public Device() {
    }

    public Device(String name, String serialNumber, int price) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device device)) return false;
        return price == device.price && Objects.equals(name, device.name) && Objects.equals(serialNumber, device.serialNumber);
    }

}
