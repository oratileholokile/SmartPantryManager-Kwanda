package com.example.smartpantrymanager_kwanda;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends Activity {

    private DBHelper dbHelper;

    private final int background = Color.rgb(241, 248, 241);
    private final int cardColor = Color.rgb(220, 239, 217);
    private final int green = Color.rgb(56, 142, 60);
    private final int darkGreen = Color.rgb(27, 94, 32);
    private final int textColor = Color.rgb(36, 59, 42);

    private LinearLayout recipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(this);

        buildScreen();
        loadSuggestedRecipes();
    }

    private void buildScreen() {

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(20, 20, 20, 12);
        root.setBackgroundColor(background);

        setContentView(root);

        TextView heading = new TextView(this);
        heading.setText("Suggested Recipes");
        heading.setTextSize(28);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setTextColor(darkGreen);
        heading.setPadding(0, 18, 0, 4);

        root.addView(heading);

        TextView subtitle = new TextView(this);
        subtitle.setText("Recipes you can make with your current pantry ingredients.");
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

        recipeContainer = new LinearLayout(this);
        recipeContainer.setOrientation(LinearLayout.VERTICAL);

        scrollView.addView(recipeContainer);

        Button backButton = new Button(this);
        backButton.setText("← Back to Home");
        backButton.setTextSize(16);
        backButton.setAllCaps(false);
        backButton.setTextColor(Color.WHITE);
        backButton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(darkGreen)
        );

        root.addView(backButton);

        backButton.setOnClickListener(v -> finish());
    }

    private void loadSuggestedRecipes() {

        recipeContainer.removeAllViews();

        ArrayList<Recipe> recipes = dbHelper.getSuggestedRecipes();

        if (recipes.isEmpty()) {

            TextView emptyMessage = new TextView(this);
            emptyMessage.setText(
                    "No recipes available yet.\n\n" +
                            "Add more ingredients to your pantry to discover recipes you can make!"
            );

            emptyMessage.setTextSize(17);
            emptyMessage.setTextColor(textColor);
            emptyMessage.setGravity(Gravity.CENTER);
            emptyMessage.setPadding(20, 50, 20, 50);

            recipeContainer.addView(emptyMessage);
            return;
        }

        for (Recipe recipe : recipes) {
            addRecipeCard(recipe);
        }
    }

    private void addRecipeCard(Recipe recipe) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(18, 16, 18, 16);

        GradientDrawable shape = new GradientDrawable();
        shape.setColor(cardColor);
        shape.setCornerRadius(20);
        card.setBackground(shape);

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        cardParams.bottomMargin = 14;

        recipeContainer.addView(card, cardParams);

        TextView name = new TextView(this);
        name.setText(recipe.getName());
        name.setTextSize(20);
        name.setTypeface(null, Typeface.BOLD);
        name.setTextColor(darkGreen);

        card.addView(name);

        TextView ingredients = new TextView(this);

        StringBuilder ingredientText = new StringBuilder("Ingredients:\n");

        for (String ingredient : recipe.getIngredients()) {
            ingredientText.append("• ")
                    .append(ingredient)
                    .append("\n");
        }

        ingredients.setText(ingredientText.toString());
        ingredients.setTextSize(14);
        ingredients.setTextColor(textColor);
        ingredients.setPadding(0, 8, 0, 8);

        card.addView(ingredients);

        Button viewButton = new Button(this);
        viewButton.setText("View Recipe");
        viewButton.setAllCaps(false);
        viewButton.setTextColor(Color.WHITE);
        viewButton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(green)
        );

        card.addView(viewButton);

        viewButton.setOnClickListener(v -> {

            Intent intent = new Intent(
                    SuggestedRecipesActivity.this,
                    RecipeDetailActivity.class
            );

            intent.putExtra("recipeName", recipe.getName());

            StringBuilder ingredientList = new StringBuilder();

            for (String ingredient : recipe.getIngredients()) {
                ingredientList.append("• ")
                        .append(ingredient)
                        .append("\n");
            }

            intent.putExtra(
                    "recipeIngredients",
                    ingredientList.toString()
            );

            intent.putExtra(
                    "recipeInstructions",
                    recipe.getInstructions()
            );

            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}