package com.example.smartpantrymanager_kwanda;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.content.Intent;

public class RecipesActivity extends Activity {

    private final int background = Color.rgb(241, 248, 241);
    private final int green = Color.rgb(56, 142, 60);
    private final int darkGreen = Color.rgb(27, 94, 32);
    private final int textColor = Color.rgb(36, 59, 42);
    private final int cardColor = Color.rgb(220, 239, 217);

    private final String[] recipeNames = {
            "Vegetable Stir-Fry",
            "Tomato Pasta",
            "Cheese Omelette",
            "Chicken Rice",
            "Fruit Salad",
            "Potato Soup",
            "Avocado Toast",
            "Vegetable Sandwich",
            "Banana Pancakes",
            "Bean Curry"
    };

    private final String[] recipeIngredients = {
            "Mixed vegetables, oil, garlic, salt",
            "Pasta, tomatoes, onion, oil, salt",
            "Eggs, cheese, butter, salt",
            "Chicken, rice, onion, spices",
            "Apples, bananas, oranges, grapes",
            "Potatoes, onion, stock, milk",
            "Bread, avocado, salt, lemon juice",
            "Bread, lettuce, tomato, cucumber",
            "Bananas, flour, milk, eggs",
            "Beans, tomatoes, onion, curry powder"
    };

    private final String[] recipeInstructions = {
            "1. Chop the vegetables.\n2. Heat oil in a pan.\n3. Add garlic and vegetables.\n4. Stir-fry until tender and serve.",
            "1. Cook the pasta.\n2. Chop and cook the tomatoes and onion.\n3. Add seasoning.\n4. Mix with pasta and serve.",
            "1. Beat the eggs.\n2. Heat butter in a pan.\n3. Add eggs and cheese.\n4. Cook until set.",
            "1. Cook the rice.\n2. Cook the chicken thoroughly with onion and spices.\n3. Serve the chicken with rice.",
            "1. Wash the fruit.\n2. Cut it into small pieces.\n3. Mix in a bowl and serve.",
            "1. Peel and chop the potatoes.\n2. Cook with onion and stock until soft.\n3. Add milk if desired.\n4. Mash or blend until smooth.",
            "1. Toast the bread.\n2. Mash the avocado with salt and lemon juice.\n3. Spread over the toast and serve.",
            "1. Wash and slice the vegetables.\n2. Place them between slices of bread.\n3. Serve fresh.",
            "1. Mash the bananas.\n2. Mix with flour, milk and eggs.\n3. Cook small portions in a lightly oiled pan until cooked through.",
            "1. Cook the onion with curry powder.\n2. Add beans and tomatoes.\n3. Simmer until hot and serve."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(background);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(20, 20, 20, 20);

        scrollView.addView(layout);
        setContentView(scrollView);

        Button backButton = new Button(this);
        backButton.setText("← Back to Home");
        backButton.setAllCaps(false);
        backButton.setTextColor(Color.WHITE);
        backButton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(darkGreen)
        );

        layout.addView(backButton);
        backButton.setOnClickListener(v -> finish());

        TextView heading = new TextView(this);
        heading.setText("Recipes");
        heading.setTextSize(30);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setTextColor(darkGreen);
        heading.setPadding(0, 20, 0, 8);

        layout.addView(heading);

        TextView subtitle = new TextView(this);
        subtitle.setText("Discover simple meals for your kitchen.");
        subtitle.setTextSize(16);
        subtitle.setTextColor(textColor);
        subtitle.setPadding(0, 0, 0, 20);

        layout.addView(subtitle);

        for (int i = 0; i < recipeNames.length; i++) {

            final int index = i;

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(16, 12, 16, 12);
            card.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(cardColor)
            );

            LinearLayout.LayoutParams cardParams =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            cardParams.bottomMargin = 12;
            layout.addView(card, cardParams);

            TextView name = new TextView(this);
            name.setText(recipeNames[i]);
            name.setTextSize(19);
            name.setTypeface(null, Typeface.BOLD);
            name.setTextColor(darkGreen);

            card.addView(name);

            TextView ingredients = new TextView(this);
            ingredients.setText("Ingredients: " + recipeIngredients[i]);
            ingredients.setTextSize(14);
            ingredients.setTextColor(textColor);
            ingredients.setPadding(0, 8, 0, 8);

            card.addView(ingredients);

            Button detailsButton = new Button(this);
            detailsButton.setText("View Recipe");
            detailsButton.setAllCaps(false);
            detailsButton.setTextColor(Color.WHITE);
            detailsButton.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(green)
            );

            card.addView(detailsButton);

            detailsButton.setOnClickListener(v -> {

                Intent intent = new Intent(
                        RecipesActivity.this,
                        RecipeDetailActivity.class
                );

                intent.putExtra("recipeName", recipeNames[index]);
                intent.putExtra(
                        "recipeIngredients",
                        recipeIngredients[index]
                );
                intent.putExtra(
                        "recipeInstructions",
                        recipeInstructions[index]
                );

                startActivity(intent);
            });
        }
    }
}