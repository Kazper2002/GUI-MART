package com.example.activiesandviews;

// SIMPLE PRODUCT CLASS CREATED FOR TESTING
// FEEL FREE TO MODIFY
// CURRENTLY CART QUANTITIES OF A PRODUCT ARE TRACKED VIA THIS CLASS.
// QUANTITIES ARE RESET TO ZERO UPON CHECKOUT
// PRODUCTS ARE GENERATED USING DATA INSIDE OF CART.TXT IN THE FOLLOWING FORMAT:
// NAME,PRICE,QUANTITY\n
public class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    // Getters
    public String getName() {return name;}
    public double getPrice() {return price;}
    public int getQuantity() {return quantity;}

    // Setters
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void resetQuantity(int quantity) {this.quantity = 0;}
}