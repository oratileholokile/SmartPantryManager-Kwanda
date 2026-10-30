
package com.example.smartpantrymanager_kwanda;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class SettingsActivity extends Activity {

    private final int background = Color.rgb(241, 248, 241);
    private final int cardColor = Color.rgb(220, 239, 217);
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

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(20, 20, 20, 12);
        root.setBackgroundColor(background);

        setContentView(root);

        TextView heading = new TextView(this);
        heading.setText("Settings");
        heading.setTextSize(30);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setTextColor(darkGreen);
        heading.setPadding(0, 18, 0, 4);

        root.addView(heading);

        TextView subtitle = new TextView(this);
        subtitle.setText("Manage your app information.");
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

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);

        scrollView.addView(content);

        // App information card
        LinearLayout appCard = createCard();

        TextView appTitle = new TextView(this);
        appTitle.setText("Smart Pantry Manager");
        appTitle.setTextSize(20);
        appTitle.setTypeface(null, Typeface.BOLD);
        appTitle.setTextColor(darkGreen);

        appCard.addView(appTitle);

        TextView appDescription = new TextView(this);
        appDescription.setText(
                "Smart Pantry Manager helps you organise " +
                        "your kitchen ingredients and keep track of " +
                        "their expiry dates."
        );
        appDescription.setTextSize(15);
        appDescription.setTextColor(textColor);
        appDescription.setPadding(0, 10, 0, 0);

        appCard.addView(appDescription);
        content.addView(appCard);

        // Theme information card
        LinearLayout themeCard = createCard();

        TextView themeTitle = new TextView(this);
        themeTitle.setText("App Theme");
        themeTitle.setTextSize(20);
        themeTitle.setTypeface(null, Typeface.BOLD);
        themeTitle.setTextColor(darkGreen);

        themeCard.addView(themeTitle);

        TextView themeDescription = new TextView(this);
        themeDescription.setText("Green and White");
        themeDescription.setTextSize(16);
        themeDescription.setTextColor(textColor);
        themeDescription.setPadding(0, 10, 0, 0);

        themeCard.addView(themeDescription);
        content.addView(themeCard);

        // Version information card
        LinearLayout versionCard = createCard();

        TextView versionTitle = new TextView(this);
        versionTitle.setText("Application Information");
        versionTitle.setTextSize(20);
        versionTitle.setTypeface(null, Typeface.BOLD);
        versionTitle.setTextColor(darkGreen);

        versionCard.addView(versionTitle);

        TextView versionDescription = new TextView(this);
        versionDescription.setText(
                "Application: Smart Pantry Manager\n" +
                        "Platform: Android\n" +
                        "Version: 1.0"
        );
        versionDescription.setTextSize(15);
        versionDescription.setTextColor(textColor);
        versionDescription.setPadding(0, 10, 0, 0);

        versionCard.addView(versionDescription);
        content.addView(versionCard);

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

    private LinearLayout createCard() {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(18, 18, 18, 18);

        GradientDrawable shape = new GradientDrawable();
        shape.setColor(cardColor);
        shape.setCornerRadius(20);

        card.setBackground(shape);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.bottomMargin = 14;

        card.setLayoutParams(params);

        return card;
    }
}
