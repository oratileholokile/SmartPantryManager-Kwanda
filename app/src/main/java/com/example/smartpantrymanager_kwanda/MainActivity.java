
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
        Button btnSettings = findViewById(R.id.btnSettings);

        btnPantry.setOnClickListener(v ->
                startActivity(new Intent(
                        MainActivity.this,
                        PantryActivity.class
                ))
        );

        btnRecipes.setOnClickListener(v ->
                startActivity(new Intent(
                        MainActivity.this,
                        RecipesActivity.class
                ))
        );

        btnSettings.setOnClickListener(v ->
                startActivity(new Intent(
                        MainActivity.this,
                        SettingsActivity.class
                ))
        );
    }
}
