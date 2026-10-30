
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
    private final int green = Color.rgb(56, 142, 60);
    private final int darkGreen = Color.rgb(27, 94, 32);
    private final int textColor = Color.rgb(36, 59, 42);
    private final int white = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildScreen();
    }

    private void buildScreen() {

        String recipeName = getIntent().getStringExtra("recipeName");
        String ingredients = getIntent().getStringExtra("recipeIngredients");
        String instructions = getIntent().getStringExtra("recipeInstructions");

        if (recipeName == null) {
            recipeName = "Recipe";
        }

        if (ingredients == null) {
            ingredients = "No ingredients available.";
        }

        if (instructions == null) {
            instructions = "No instructions available.";
        }

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(20, 20, 20, 12);
        root.setBackgroundColor(background);

        setContentView(root);

        TextView heading = new TextView(this);
        heading.setText(recipeName);
        heading.setTextSize(27);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setTextColor(darkGreen);
        heading.setPadding(0, 18, 0, 16);

        root.addView(heading);

        ScrollView scrollView = new ScrollView(this);

        LinearLayout.LayoutParams scrollParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1
                );

        root.addView(scrollView, scrollParams);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);

        scrollView.addView(content);

        TextView ingredientsHeading = new TextView(this);
        ingredientsHeading.setText("Ingredients");
        ingredientsHeading.setTextSize(21);
        ingredientsHeading.setTypeface(null, Typeface.BOLD);
        ingredientsHeading.setTextColor(green);
        ingredientsHeading.setPadding(0, 8, 0, 8);

        content.addView(ingredientsHeading);

        TextView ingredientsText = new TextView(this);
        ingredientsText.setText("• " + ingredients.replace(", ", "\n• "));
        ingredientsText.setTextSize(16);
        ingredientsText.setTextColor(textColor);
        ingredientsText.setPadding(8, 4, 8, 24);

        content.addView(ingredientsText);

        TextView instructionsHeading = new TextView(this);
        instructionsHeading.setText("Cooking Instructions");
        instructionsHeading.setTextSize(21);
        instructionsHeading.setTypeface(null, Typeface.BOLD);
        instructionsHeading.setTextColor(green);
        instructionsHeading.setPadding(0, 8, 0, 8);

        content.addView(instructionsHeading);

        TextView instructionsText = new TextView(this);
        instructionsText.setText(instructions);
        instructionsText.setTextSize(16);
        instructionsText.setTextColor(textColor);
        instructionsText.setPadding(8, 4, 8, 24);

        content.addView(instructionsText);

        // Back button at the bottom
        Button backButton = new Button(this);
        backButton.setText("← Back to Recipes");
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
