package com.example.smartpantrymanager_kwanda;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class RecipeDetailActivity extends Activity {

    private final int background = Color.rgb(241, 248, 241);
    private final int darkGreen = Color.rgb(27, 94, 32);
    private final int textColor = Color.rgb(36, 59, 42);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(background);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(24, 24, 24, 24);

        scrollView.addView(layout);
        setContentView(scrollView);

        Button backButton = new Button(this);
        backButton.setText("← Back to Recipes");
        backButton.setAllCaps(false);
        backButton.setTextColor(Color.WHITE);
        backButton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(darkGreen)
        );

        layout.addView(backButton);
        backButton.setOnClickListener(v -> finish());

        String recipeName = getIntent().getStringExtra("recipeName");
        String ingredients = getIntent().getStringExtra("recipeIngredients");
        String instructions = getIntent().getStringExtra("recipeInstructions");

        TextView title = new TextView(this);
        title.setText(recipeName != null ? recipeName : "Recipe Details");
        title.setTextSize(28);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(darkGreen);
        title.setPadding(0, 20, 0, 20);

        layout.addView(title);

        TextView ingredientsTitle = new TextView(this);
        ingredientsTitle.setText("Ingredients");
        ingredientsTitle.setTextSize(21);
        ingredientsTitle.setTypeface(null, Typeface.BOLD);
        ingredientsTitle.setTextColor(darkGreen);

        layout.addView(ingredientsTitle);

        TextView ingredientsText = new TextView(this);
        ingredientsText.setText(
                ingredients != null ? "• " + ingredients.replace(", ", "\n• ")
                        : "No ingredients available."
        );
        ingredientsText.setTextSize(16);
        ingredientsText.setTextColor(textColor);
        ingredientsText.setPadding(0, 10, 0, 24);

        layout.addView(ingredientsText);

        TextView instructionsTitle = new TextView(this);
        instructionsTitle.setText("Cooking Instructions");
        instructionsTitle.setTextSize(21);
        instructionsTitle.setTypeface(null, Typeface.BOLD);
        instructionsTitle.setTextColor(darkGreen);

        layout.addView(instructionsTitle);

        TextView instructionsText = new TextView(this);
        instructionsText.setText(
                instructions != null ? instructions : "No instructions available."
        );
        instructionsText.setTextSize(16);
        instructionsText.setTextColor(textColor);
        instructionsText.setPadding(0, 10, 0, 20);

        layout.addView(instructionsText);
    }
}