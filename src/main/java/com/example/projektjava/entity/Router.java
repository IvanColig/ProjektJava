package com.example.projektjava.entity;

public class Router extends Device implements Network {

    private String networkCard;

    public Router(String name, String serialNumber, int price, String networkCard) {
        super(name, serialNumber, price);
        this.networkCard = networkCard;
    }

    @Override
    public String getNetworkCard() {
        return networkCard;
    }

    @Override
    public void setNetworkCard(String networkCard) {
        this.networkCard = networkCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Router router)) return false;
        if (!super.equals(o)) return false;
        return networkCard == router.networkCard;
    }

    @Override
    public String toTableString() {
        return String.format("%s,%s,%d,%s", getName(), getSerialNumber(), getPrice(), networkCard);
    }

}
