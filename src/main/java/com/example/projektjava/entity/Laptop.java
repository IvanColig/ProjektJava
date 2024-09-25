package com.example.projektjava.entity;

public class Laptop extends Device implements Screen, Battery {

    private int batteryCapacity;
    private int screenWidth;
    private int screenHeight;

    public Laptop(String name, String serialNumber, int price, int batteryCapacity, int screenWidth, int screenHeight) {
        super(name, serialNumber, price);
        this.batteryCapacity = batteryCapacity;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    @Override
    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    @Override
    public int getScreenWidth() {
        return screenWidth;
    }

    @Override
    public int getScreenHeight() {
        return screenHeight;
    }

    @Override
    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    @Override
    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    @Override
    public void setScreenHeight(int screenLength) {
        this.screenHeight = screenLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Laptop laptop)) return false;
        if (!super.equals(o)) return false;
        return batteryCapacity == laptop.batteryCapacity && screenWidth == laptop.screenWidth && screenHeight == laptop.screenHeight;
    }

    @Override
    public String toTableString() {
        return String.format("%s,%s,%d,%d,%d,%d", getName(), getSerialNumber(), getPrice(), batteryCapacity, screenWidth, screenHeight);
    }

}
