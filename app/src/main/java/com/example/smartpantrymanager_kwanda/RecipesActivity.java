
package com.example.smartpantrymanager_kwanda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class RecipesActivity extends Activity {

    private final int background = Color.rgb(241, 248, 241);
    private final int cardColor = Color.rgb(220, 239, 217);
    private final int green = Color.rgb(56, 142, 60);
    private final int darkGreen = Color.rgb(27, 94, 32);
    private final int textColor = Color.rgb(36, 59, 42);
    private final int white = Color.WHITE;

    private String[] recipeNames = {
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

    private String[] recipeIngredients = {
            "Mixed vegetables, oil, garlic, soy sauce",
            "Pasta, tomatoes, onion, garlic, cheese",
            "Eggs, cheese, milk, salt, pepper",
            "Chicken, rice, onion, spices",
            "Apples, bananas, oranges, grapes",
            "Potatoes, onion, stock, milk",
            "Bread, avocado, salt, pepper",
            "Bread, lettuce, tomato, cucumber, cheese",
            "Bananas, flour, eggs, milk",
            "Beans, tomatoes, onion, curry powder"
    };

    private String[] recipeInstructions = {
            "1. Wash and chop the vegetables.\n\n" +
                    "2. Heat oil in a pan.\n\n" +
                    "3. Add garlic and vegetables.\n\n" +
                    "4. Add soy sauce and stir-fry until cooked.",

            "1. Cook the pasta according to the packet instructions.\n\n" +
                    "2. Chop the tomatoes and onion.\n\n" +
                    "3. Fry the onion and garlic.\n\n" +
                    "4. Add tomatoes and cook into a sauce.\n\n" +
                    "5. Mix with pasta and add cheese.",

            "1. Beat the eggs with milk.\n\n" +
                    "2. Add salt and pepper.\n\n" +
                    "3. Heat a pan and pour in the eggs.\n\n" +
                    "4. Add cheese.\n\n" +
                    "5. Cook until ready.",

            "1. Wash the rice.\n\n" +
                    "2. Cook the chicken with onion and spices.\n\n" +
                    "3. Add water and cook until tender.\n\n" +
                    "4. Cook the rice separately.\n\n" +
                    "5. Serve the chicken with rice.",

            "1. Wash all the fruits.\n\n" +
                    "2. Peel and chop the fruits.\n\n" +
                    "3. Place them in a bowl.\n\n" +
                    "4. Mix gently and serve.",

            "1. Peel and chop the potatoes.\n\n" +
                    "2. Fry the onion in a pot.\n\n" +
                    "3. Add potatoes and stock.\n\n" +
                    "4. Simmer until soft.\n\n" +
                    "5. Add milk and blend until smooth.",

            "1. Toast the bread.\n\n" +
                    "2. Mash the avocado.\n\n" +
                    "3. Add salt and pepper.\n\n" +
                    "4. Spread the avocado on the toast.",

            "1. Wash and slice the vegetables.\n\n" +
                    "2. Place lettuce on the bread.\n\n" +
                    "3. Add tomato, cucumber and cheese.\n\n" +
                    "4. Cover with another slice of bread.",

            "1. Mash the bananas.\n\n" +
                    "2. Add flour, eggs and milk.\n\n" +
                    "3. Mix until smooth.\n\n" +
                    "4. Heat a pan.\n\n" +
                    "5. Cook small pancakes on both sides.",

            "1. Chop the onion.\n\n" +
                    "2. Fry the onion with curry powder.\n\n" +
                    "3. Add tomatoes and beans.\n\n" +
                    "4. Simmer until the sauce thickens.\n\n" +
                    "5. Serve with rice."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildScreen();
    }

    private void buildScreen() {

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(20, 20, 20, 12);
        root.setBackgroundColor(background);

        setContentView(root);

        TextView heading = new TextView(this);
        heading.setText("Recipes");
        heading.setTextSize(30);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setTextColor(darkGreen);
        heading.setPadding(0, 18, 0, 4);

        root.addView(heading);

        TextView subtitle = new TextView(this);
        subtitle.setText("Discover meals you can prepare.");
        subtitle.setTextSize(15);
        subtitle.setTextColor(textColor);
        subtitle.setPadding(0, 0, 0, 16);

        root.addView(subtitle);

        ScrollView scrollView = new ScrollView(this);

        LinearLayout.LayoutParams scrollParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1
                );

        root.addView(scrollView, scrollParams);

        LinearLayout recipeContainer = new LinearLayout(this);
        recipeContainer.setOrientation(LinearLayout.VERTICAL);

        scrollView.addView(recipeContainer);

        for (int i = 0; i < recipeNames.length; i++) {

            final int position = i;

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(16, 14, 16, 14);

            GradientDrawable shape = new GradientDrawable();
            shape.setColor(cardColor);
            shape.setCornerRadius(20);
            card.setBackground(shape);

            LinearLayout.LayoutParams cardParams =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            cardParams.bottomMargin = 12;

            recipeContainer.addView(card, cardParams);

            TextView name = new TextView(this);
            name.setText(recipeNames[i]);
            name.setTextSize(20);
            name.setTypeface(null, Typeface.BOLD);
            name.setTextColor(darkGreen);

            card.addView(name);

            TextView ingredients = new TextView(this);
            ingredients.setText(
                    "Ingredients: " + recipeIngredients[i]
            );
            ingredients.setTextSize(14);
            ingredients.setTextColor(textColor);
            ingredients.setPadding(0, 8, 0, 8);

            card.addView(ingredients);

            Button viewButton = new Button(this);
            viewButton.setText("View Recipe");
            viewButton.setAllCaps(false);
            viewButton.setTextColor(white);
            viewButton.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(green)
            );

            card.addView(viewButton);

            viewButton.setOnClickListener(v -> {

                Intent intent = new Intent(
                        RecipesActivity.this,
                        RecipeDetailActivity.class
                );

                intent.putExtra("recipeName", recipeNames[position]);
                intent.putExtra("recipeIngredients", recipeIngredients[position]);
                intent.putExtra("recipeInstructions", recipeInstructions[position]);

                startActivity(intent);
            });
        }

        // Back button at the bottom
        Button backButton = new Button(this);
        backButton.setText("← Back to Home");
        backButton.setTextSize(16);
        backButton.setAllCaps(false);
        backButton.setTextColor(white);
        backButton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(darkGreen)
        );

        LinearLayout.LayoutParams backParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        backParams.topMargin = 8;

        root.addView(backButton, backParams);

        backButton.setOnClickListener(v -> finish());
    }
}
