package com.example.smartpantrymanager_kwanda;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPantry = findViewById(R.id.btnPantry);
        Button btnRecipes = findViewById(R.id.btnRecipes);
        Button btnSuggestedRecipes = findViewById(R.id.btnSuggestedRecipes);
        Button btnSettings = findViewById(R.id.btnSettings);

        btnPantry.setOnClickListener(v ->
                startActivity(new Intent(this, PantryActivity.class)));

        btnRecipes.setOnClickListener(v ->
                startActivity(new Intent(this, RecipesActivity.class)));

        btnSuggestedRecipes.setOnClickListener(v ->
                startActivity(new Intent(this, SuggestedRecipesActivity.class)));

        btnSettings.setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity.class)));
    }
}