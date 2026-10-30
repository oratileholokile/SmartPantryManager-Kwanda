package com.example.smartpantrymanager_kwanda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartPantryKwanda.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_INGREDIENTS = "ingredients";
    private static final String TABLE_RECIPES = "recipes";
    private static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
        seedRecipes(db);
    }

    private void createTables(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS ingredients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "quantity TEXT NOT NULL, " +
                "category TEXT NOT NULL, " +
                "expiry TEXT NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "instructions TEXT NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS recipe_ingredients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "recipe_id INTEGER NOT NULL, " +
                "name TEXT NOT NULL, " +
                "quantity REAL NOT NULL, " +
                "unit TEXT NOT NULL, " +
                "FOREIGN KEY(recipe_id) REFERENCES recipes(id) ON DELETE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 2) {
            createTables(db);
            seedRecipes(db);
        }
    }

    // PANTRY METHODS

    public boolean addIngredient(String name, String quantity,
                                 String category, String expiry) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("quantity", quantity);
        values.put("category", category);
        values.put("expiry", expiry);

        long result = db.insert(TABLE_INGREDIENTS, null, values);
        db.close();

        return result != -1;
    }

    public ArrayList<Ingredient> getIngredients() {

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM ingredients ORDER BY name ASC", null);

        try {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String quantity = cursor.getString(cursor.getColumnIndexOrThrow("quantity"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                String expiry = cursor.getString(cursor.getColumnIndexOrThrow("expiry"));

                ingredients.add(new Ingredient(
                        id, name, quantity, category, expiry));
            }
        } finally {
            cursor.close();
            db.close();
        }

        return ingredients;
    }

    public boolean updateIngredient(int id, String name, String quantity,
                                    String category, String expiry) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("quantity", quantity);
        values.put("category", category);
        values.put("expiry", expiry);

        int result = db.update(
                TABLE_INGREDIENTS,
                values,
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        return result > 0;
    }

    public boolean deleteIngredient(int id) {

        SQLiteDatabase db = getWritableDatabase();

        int result = db.delete(
                TABLE_INGREDIENTS,
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        return result > 0;
    }

    // RECIPE METHODS

    private void addRecipe(SQLiteDatabase db, String name,
                           String instructions, String[][] ingredients) {

        ContentValues recipeValues = new ContentValues();
        recipeValues.put("name", name);
        recipeValues.put("instructions", instructions);

        long recipeId = db.insert(TABLE_RECIPES, null, recipeValues);

        if (recipeId == -1) {
            return;
        }

        for (String[] ingredient : ingredients) {

            ContentValues ingredientValues = new ContentValues();
            ingredientValues.put("recipe_id", recipeId);
            ingredientValues.put("name", ingredient[0]);
            ingredientValues.put("quantity", Double.parseDouble(ingredient[1]));
            ingredientValues.put("unit", ingredient[2]);

            db.insert(TABLE_RECIPE_INGREDIENTS, null, ingredientValues);
        }
    }

    private void seedRecipes(SQLiteDatabase db) {

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM recipes", null);
        int count = 0;

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();

        if (count > 0) {
            return;
        }

        addRecipe(db, "Tomato Pasta",
                "1. Cook the pasta.\n2. Fry the onion and garlic.\n3. Add tomatoes and cook into a sauce.\n4. Mix with pasta and serve.",
                new String[][]{
                        {"pasta", "200", "g"},
                        {"tomato", "2", "pcs"},
                        {"onion", "1", "pcs"},
                        {"garlic", "2", "cloves"}
                });

        addRecipe(db, "Cheese Omelette",
                "1. Beat the eggs.\n2. Add milk, salt and pepper.\n3. Cook in a pan.\n4. Add cheese and fold.",
                new String[][]{
                        {"egg", "2", "pcs"},
                        {"cheese", "30", "g"},
                        {"milk", "30", "ml"}
                });

        addRecipe(db, "Chicken Rice",
                "1. Cook the rice.\n2. Cook the chicken with onion.\n3. Add spices and water.\n4. Simmer until cooked.",
                new String[][]{
                        {"chicken", "250", "g"},
                        {"rice", "150", "g"},
                        {"onion", "1", "pcs"},
                        {"spices", "1", "tsp"}
                });

        addRecipe(db, "Fruit Salad",
                "1. Wash the fruit.\n2. Peel and chop it.\n3. Mix in a bowl and serve.",
                new String[][]{
                        {"apple", "1", "pcs"},
                        {"banana", "1", "pcs"},
                        {"orange", "1", "pcs"}
                });

        addRecipe(db, "Potato Soup",
                "1. Peel and chop the potatoes.\n2. Fry the onion.\n3. Add potatoes and stock.\n4. Add milk and simmer until soft.",
                new String[][]{
                        {"potato", "3", "pcs"},
                        {"onion", "1", "pcs"},
                        {"stock", "500", "ml"},
                        {"milk", "100", "ml"}
                });

        addRecipe(db, "Avocado Toast",
                "1. Toast the bread.\n2. Mash the avocado.\n3. Add salt and spread over the toast.",
                new String[][]{
                        {"bread", "2", "slices"},
                        {"avocado", "1", "pcs"}
                });

        addRecipe(db, "Vegetable Sandwich",
                "1. Slice the vegetables.\n2. Place lettuce, tomato and cucumber on bread.\n3. Add cheese and serve.",
                new String[][]{
                        {"bread", "2", "slices"},
                        {"lettuce", "2", "leaves"},
                        {"tomato", "1", "pcs"},
                        {"cucumber", "0.5", "pcs"},
                        {"cheese", "20", "g"}
                });

        addRecipe(db, "Banana Pancakes",
                "1. Mash the bananas.\n2. Mix with flour, eggs and milk.\n3. Cook small pancakes in a pan.",
                new String[][]{
                        {"banana", "2", "pcs"},
                        {"flour", "100", "g"},
                        {"egg", "1", "pcs"},
                        {"milk", "100", "ml"}
                });

        addRecipe(db, "Bean Curry",
                "1. Fry the onion with curry powder.\n2. Add tomatoes and beans.\n3. Simmer until the sauce thickens.",
                new String[][]{
                        {"beans", "200", "g"},
                        {"tomato", "2", "pcs"},
                        {"onion", "1", "pcs"},
                        {"curry powder", "1", "tsp"}
                });

        addRecipe(db, "Garlic Fried Rice",
                "1. Cook the rice.\n2. Fry garlic in oil.\n3. Add rice and soy sauce.\n4. Stir until heated.",
                new String[][]{
                        {"rice", "200", "g"},
                        {"garlic", "2", "cloves"},
                        {"oil", "15", "ml"},
                        {"soy sauce", "15", "ml"}
                });

        addRecipe(db, "Cheese Sandwich",
                "1. Place cheese between bread slices.\n2. Toast in a pan until golden.",
                new String[][]{
                        {"bread", "2", "slices"},
                        {"cheese", "40", "g"}
                });

        addRecipe(db, "Tomato Soup",
                "1. Chop the tomatoes and onion.\n2. Cook with stock.\n3. Add milk and blend until smooth.",
                new String[][]{
                        {"tomato", "4", "pcs"},
                        {"onion", "1", "pcs"},
                        {"stock", "300", "ml"},
                        {"milk", "100", "ml"}
                });

        addRecipe(db, "Chicken Sandwich",
                "1. Cook the chicken.\n2. Slice the chicken.\n3. Place it between bread with lettuce.",
                new String[][]{
                        {"chicken", "150", "g"},
                        {"bread", "2", "slices"},
                        {"lettuce", "2", "leaves"}
                });

        addRecipe(db, "Mashed Potatoes",
                "1. Boil the potatoes until soft.\n2. Mash with milk and butter.\n3. Season and serve.",
                new String[][]{
                        {"potato", "3", "pcs"},
                        {"milk", "50", "ml"},
                        {"butter", "20", "g"}
                });

        addRecipe(db, "Vegetable Stir-Fry",
                "1. Chop the vegetables.\n2. Heat oil in a pan.\n3. Add vegetables and soy sauce.\n4. Stir-fry until cooked.",
                new String[][]{
                        {"carrot", "1", "pcs"},
                        {"broccoli", "100", "g"},
                        {"oil", "15", "ml"},
                        {"soy sauce", "15", "ml"}
                });

        addRecipe(db, "Egg Fried Rice",
                "1. Cook the rice.\n2. Scramble the eggs.\n3. Add rice and soy sauce.\n4. Stir until heated.",
                new String[][]{
                        {"rice", "200", "g"},
                        {"egg", "2", "pcs"},
                        {"soy sauce", "15", "ml"}
                });

        addRecipe(db, "Apple Pancakes",
                "1. Grate the apple.\n2. Mix with flour, egg and milk.\n3. Cook in a pan until golden.",
                new String[][]{
                        {"apple", "1", "pcs"},
                        {"flour", "100", "g"},
                        {"egg", "1", "pcs"},
                        {"milk", "100", "ml"}
                });

        addRecipe(db, "Chicken Potato Bake",
                "1. Chop the chicken and potatoes.\n2. Place in a baking dish.\n3. Season and bake until cooked.",
                new String[][]{
                        {"chicken", "250", "g"},
                        {"potato", "2", "pcs"},
                        {"onion", "1", "pcs"},
                        {"spices", "1", "tsp"}
                });

        addRecipe(db, "Banana Smoothie",
                "1. Peel the banana.\n2. Add banana and milk to a blender.\n3. Blend until smooth.",
                new String[][]{
                        {"banana", "2", "pcs"},
                        {"milk", "200", "ml"}
                });

        addRecipe(db, "Garlic Potato Wedges",
                "1. Cut potatoes into wedges.\n2. Mix with oil and garlic.\n3. Bake until golden and tender.",
                new String[][]{
                        {"potato", "3", "pcs"},
                        {"oil", "15", "ml"},
                        {"garlic", "2", "cloves"}
                });
    }

    // STRICT RECIPE MATCHING

    public ArrayList<Recipe> getSuggestedRecipes() {

        ArrayList<Recipe> suggestions = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor recipes = db.rawQuery("SELECT * FROM recipes ORDER BY name ASC", null);

        try {
            while (recipes.moveToNext()) {

                int recipeId = recipes.getInt(recipes.getColumnIndexOrThrow("id"));
                String name = recipes.getString(recipes.getColumnIndexOrThrow("name"));
                String instructions = recipes.getString(recipes.getColumnIndexOrThrow("instructions"));

                Cursor required = db.rawQuery(
                        "SELECT name, quantity, unit FROM recipe_ingredients WHERE recipe_id = ?",
                        new String[]{String.valueOf(recipeId)}
                );

                boolean canMake = true;
                ArrayList<String> ingredientList = new ArrayList<>();

                try {
                    while (required.moveToNext()) {

                        String requiredName = required.getString(0);
                        double requiredQuantity = required.getDouble(1);
                        String requiredUnit = required.getString(2);

                        ingredientList.add(
                                requiredName + " - " + requiredQuantity + " " + requiredUnit
                        );

                        if (!hasEnoughIngredient(
                                db, requiredName, requiredQuantity, requiredUnit)) {
                            canMake = false;
                            break;
                        }
                    }
                } finally {
                    required.close();
                }

                if (canMake) {
                    suggestions.add(new Recipe(
                            recipeId,
                            name,
                            ingredientList,
                            instructions
                    ));
                }
            }
        } finally {
            recipes.close();
            db.close();
        }

        return suggestions;
    }

    private boolean hasEnoughIngredient(SQLiteDatabase db, String requiredName,
                                        double requiredQuantity, String requiredUnit) {

        Cursor cursor = db.rawQuery(
                "SELECT name, quantity FROM ingredients",
                null
        );

        double available = 0;

        try {
            while (cursor.moveToNext()) {

                String pantryName = cursor.getString(0);

                if (normaliseName(pantryName).equals(normaliseName(requiredName))) {

                    String pantryQuantity = cursor.getString(1);
                    double converted = convertQuantity(pantryQuantity, requiredUnit);

                    if (converted >= 0) {
                        available += converted;
                    }
                }
            }
        } finally {
            cursor.close();
        }

        return available >= requiredQuantity;
    }

    private String normaliseName(String name) {

        String value = name.toLowerCase(Locale.ROOT).trim();

        if (value.endsWith("ies") && value.length() > 3) {
            value = value.substring(0, value.length() - 3) + "y";
        } else if (value.endsWith("oes") && value.length() > 3) {
            value = value.substring(0, value.length() - 2);
        } else if (value.endsWith("s") && !value.endsWith("ss")
                && value.length() > 2) {
            value = value.substring(0, value.length() - 1);
        }

        return value;
    }

    private double convertQuantity(String quantity, String requiredUnit) {

        try {
            String value = quantity.toLowerCase(Locale.ROOT).trim();
            StringBuilder number = new StringBuilder();

            for (int i = 0; i < value.length(); i++) {
                char c = value.charAt(i);

                if (Character.isDigit(c) || c == '.') {
                    number.append(c);
                } else if (number.length() > 0) {
                    break;
                }
            }

            if (number.length() == 0) {
                return -1;
            }

            double amount = Double.parseDouble(number.toString());

            String pantryUnit = value.substring(number.length()).trim();
            pantryUnit = pantryUnit.replaceAll("[^a-z]", "");

            String target = requiredUnit.toLowerCase(Locale.ROOT);

            if (pantryUnit.isEmpty()) {
                return target.equals("pcs") || target.equals("slices")
                        || target.equals("leaves") ? amount : -1;
            }

            if (pantryUnit.equals("kg") && target.equals("g")) {
                return amount * 1000;
            }

            if (pantryUnit.equals("g") && target.equals("kg")) {
                return amount / 1000;
            }

            if (pantryUnit.equals("l") && target.equals("ml")) {
                return amount * 1000;
            }

            if (pantryUnit.equals("ml") && target.equals("l")) {
                return amount / 1000;
            }

            if (pantryUnit.equals("pcs") || pantryUnit.equals("pc")
                    || pantryUnit.equals("piece") || pantryUnit.equals("pieces")) {
                return target.equals("pcs") ? amount : -1;
            }

            if (pantryUnit.equals(target)) {
                return amount;
            }

            if (pantryUnit.equals("slices") && target.equals("slices")) {
                return amount;
            }

            if (pantryUnit.equals("leaves") && target.equals("leaves")) {
                return amount;
            }

            if (pantryUnit.equals("cloves") && target.equals("cloves")) {
                return amount;
            }

            if (pantryUnit.equals("tsp") && target.equals("tsp")) {
                return amount;
            }

            return -1;

        } catch (Exception e) {
            return -1;
        }
    }
}
