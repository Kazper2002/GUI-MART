package com.example.activiesandviews;

public class Item {
    private String name;
    private double price;
    private String imageName;

    public Item(String name, double price, String imageName) {
        this.name = name;
        this.price = price;
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImageName() {
        return imageName;
    }
}
