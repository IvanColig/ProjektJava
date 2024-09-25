package com.example.projektjava.entity;

public class PC extends Device implements Screen {

    private int screenWidth;
    private  int screenHeight;

    public PC(String name, String serialNumber, int price, int screenWidth, int screenHeight) {
        super(name, serialNumber, price);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
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
        if (!(o instanceof PC pc)) return false;
        if (!super.equals(o)) return false;
        return screenWidth == pc.screenWidth && screenHeight == pc.screenHeight;
    }

    @Override
    public String toTableString() {
        return String.format("%s,%s,%d,%d,%d", getName(), getSerialNumber(), getPrice(), screenWidth, screenHeight);
    }

}
