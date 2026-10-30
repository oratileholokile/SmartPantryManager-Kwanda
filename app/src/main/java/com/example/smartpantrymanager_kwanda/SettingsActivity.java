package com.example.smartpantrymanager_kwanda;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingsActivity extends Activity {

    private final int background = Color.rgb(241, 248, 241);
    private final int darkGreen = Color.rgb(27, 94, 32);
    private final int textColor = Color.rgb(36, 59, 42);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(24, 24, 24, 24);
        layout.setBackgroundColor(background);

        setContentView(layout);

        Button backButton = new Button(this);
        backButton.setText("← Back to Home");
        backButton.setAllCaps(false);
        backButton.setTextColor(Color.WHITE);
        backButton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(darkGreen)
        );

        layout.addView(backButton);
        backButton.setOnClickListener(v -> finish());

        TextView title = new TextView(this);
        title.setText("Settings");
        title.setTextSize(30);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(darkGreen);
        title.setPadding(0, 24, 0, 12);

        layout.addView(title);

        TextView appName = new TextView(this);
        appName.setText("Smart Pantry Manager");
        appName.setTextSize(20);
        appName.setTypeface(null, Typeface.BOLD);
        appName.setTextColor(textColor);
        appName.setPadding(0, 12, 0, 8);

        layout.addView(appName);

        TextView description = new TextView(this);
        description.setText(
                "Manage your pantry ingredients and explore recipes " +
                        "with Smart Pantry Manager."
        );
        description.setTextSize(16);
        description.setTextColor(textColor);
        description.setPadding(0, 0, 0, 20);

        layout.addView(description);

        TextView themeLabel = new TextView(this);
        themeLabel.setText("App Theme");
        themeLabel.setTextSize(18);
        themeLabel.setTypeface(null, Typeface.BOLD);
        themeLabel.setTextColor(darkGreen);
        themeLabel.setPadding(0, 12, 0, 8);

        layout.addView(themeLabel);

        TextView themeValue = new TextView(this);
        themeValue.setText("Green and White");
        themeValue.setTextSize(16);
        themeValue.setTextColor(textColor);

        layout.addView(themeValue);

        TextView version = new TextView(this);
        version.setText("Smart Pantry Manager Kwanda");
        version.setTextSize(14);
        version.setTextColor(textColor);
        version.setGravity(Gravity.CENTER);
        version.setPadding(0, 40, 0, 0);

        layout.addView(version);
    }
}