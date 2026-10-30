
package com.example.smartpantrymanager_kwanda;

import java.util.ArrayList;

public class Recipe {

    private int id;
    private String name;
    private ArrayList<String> ingredients;
    private String instructions;

    public Recipe(int id, String name,
                  ArrayList<String> ingredients, String instructions) {

        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }
}
