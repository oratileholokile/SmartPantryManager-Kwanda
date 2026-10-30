package com.example.smartpantrymanager_kwanda;

public class Ingredient {

    private int id;
    private String name;
    private String quantity;
    private String category;
    private String expiry;

    public Ingredient(int id, String name, String quantity,
                      String category, String expiry) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
        this.expiry = expiry;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
}