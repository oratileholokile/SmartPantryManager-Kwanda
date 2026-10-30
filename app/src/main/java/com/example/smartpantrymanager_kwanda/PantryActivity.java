package com.example.smartpantrymanager_kwanda;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PantryActivity extends Activity {

    private final int background = Color.rgb(241, 248, 241);
    private final int cardColor = Color.rgb(220, 239, 217);
    private final int green = Color.rgb(56, 142, 60);
    private final int darkGreen = Color.rgb(27, 94, 32);
    private final int textColor = Color.rgb(36, 59, 42);
    private final int white = Color.WHITE;

    private DBHelper dbHelper;
    private LinearLayout ingredientContainer;
    private TextView emptyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(this);

        buildScreen();
        loadIngredients();
    }

    private void buildScreen() {

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(20, 20, 20, 12);
        root.setBackgroundColor(background);

        setContentView(root);

        Button backButton = new Button(this);
        backButton.setText("← Back to Home");
        backButton.setTextColor(white);
        backButton.setAllCaps(false);
        backButton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(darkGreen)
        );

        root.addView(backButton);

        backButton.setOnClickListener(v -> finish());

        TextView heading = new TextView(this);
        heading.setText("My Pantry");
        heading.setTextSize(30);
        heading.setTypeface(null, Typeface.BOLD);
        heading.setTextColor(darkGreen);
        heading.setPadding(0, 18, 0, 4);

        root.addView(heading);

        TextView subtitle = new TextView(this);
        subtitle.setText("Keep track of the ingredients in your kitchen.");
        subtitle.setTextSize(15);
        subtitle.setTextColor(textColor);
        subtitle.setPadding(0, 0, 0, 16);

        root.addView(subtitle);

        Button addButton = new Button(this);
        addButton.setText("+ Add Ingredient");
        addButton.setTextSize(16);
        addButton.setAllCaps(false);
        addButton.setTextColor(white);
        addButton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(green)
        );

        root.addView(addButton);

        addButton.setOnClickListener(v -> showIngredientDialog(null));

        ScrollView scrollView = new ScrollView(this);

        LinearLayout.LayoutParams scrollParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1
                );

        scrollParams.topMargin = 12;

        root.addView(scrollView, scrollParams);

        ingredientContainer = new LinearLayout(this);
        ingredientContainer.setOrientation(LinearLayout.VERTICAL);

        scrollView.addView(ingredientContainer);

        emptyMessage = new TextView(this);
        emptyMessage.setText("Your pantry is empty.\nAdd your first ingredient!");
        emptyMessage.setTextColor(textColor);
        emptyMessage.setTextSize(16);
        emptyMessage.setGravity(Gravity.CENTER);
        emptyMessage.setPadding(12, 35, 12, 35);

        ingredientContainer.addView(emptyMessage);
    }

    private void loadIngredients() {

        ingredientContainer.removeAllViews();

        ArrayList<Ingredient> ingredients = dbHelper.getIngredients();

        if (ingredients.isEmpty()) {
            emptyMessage = new TextView(this);
            emptyMessage.setText(
                    "Your pantry is empty.\nAdd your first ingredient!"
            );
            emptyMessage.setTextColor(textColor);
            emptyMessage.setTextSize(16);
            emptyMessage.setGravity(Gravity.CENTER);
            emptyMessage.setPadding(12, 35, 12, 35);

            ingredientContainer.addView(emptyMessage);
            return;
        }

        for (Ingredient ingredient : ingredients) {
            addIngredientCard(ingredient);
        }
    }

    private void addIngredientCard(Ingredient ingredient) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(16, 12, 16, 12);

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

        ingredientContainer.addView(card, cardParams);

        TextView name = new TextView(this);
        name.setText(ingredient.getName());
        name.setTextSize(20);
        name.setTypeface(null, Typeface.BOLD);
        name.setTextColor(darkGreen);

        card.addView(name);

        TextView details = new TextView(this);

        details.setText(
                "Quantity: " + ingredient.getQuantity() +
                        "\nCategory: " + ingredient.getCategory() +
                        "\nExpiry date: " + ingredient.getExpiry()
        );

        details.setTextSize(15);
        details.setTextColor(textColor);
        details.setPadding(0, 8, 0, 8);

        card.addView(details);

        TextView expiryStatus = new TextView(this);
        expiryStatus.setText(getExpiryStatus(ingredient.getExpiry()));
        expiryStatus.setTextSize(14);
        expiryStatus.setTypeface(null, Typeface.BOLD);
        expiryStatus.setTextColor(getExpiryColor(ingredient.getExpiry()));

        card.addView(expiryStatus);

        LinearLayout buttons = new LinearLayout(this);
        buttons.setOrientation(LinearLayout.HORIZONTAL);

        card.addView(buttons);

        Button editButton = new Button(this);
        editButton.setText("Edit");
        editButton.setAllCaps(false);
        editButton.setTextColor(white);
        editButton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(green)
        );

        buttons.addView(
                editButton,
                new LinearLayout.LayoutParams(0, 48, 1)
        );

        Button deleteButton = new Button(this);
        deleteButton.setText("Delete");
        deleteButton.setAllCaps(false);
        deleteButton.setTextColor(white);
        deleteButton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(darkGreen)
        );

        LinearLayout.LayoutParams deleteParams =
                new LinearLayout.LayoutParams(0, 48, 1);

        deleteParams.leftMargin = 8;

        buttons.addView(deleteButton, deleteParams);

        editButton.setOnClickListener(
                v -> showIngredientDialog(ingredient)
        );

        deleteButton.setOnClickListener(v -> confirmDelete(ingredient));
    }

    private void showIngredientDialog(Ingredient ingredient) {

        boolean editing = ingredient != null;

        LinearLayout form = new LinearLayout(this);
        form.setOrientation(LinearLayout.VERTICAL);
        form.setPadding(24, 8, 24, 0);

        EditText nameInput = createInput("Ingredient name");
        form.addView(nameInput);

        EditText quantityInput = createInput("Quantity (e.g. 2 kg or 5 items)");
        form.addView(quantityInput);

        TextView categoryLabel = new TextView(this);
        categoryLabel.setText("Category");
        categoryLabel.setTextColor(darkGreen);
        categoryLabel.setTextSize(15);
        categoryLabel.setPadding(0, 8, 0, 4);

        form.addView(categoryLabel);

        Spinner categorySpinner = new Spinner(this);

        String[] categories = {
                "Vegetables",
                "Fruits",
                "Dairy",
                "Meat",
                "Grains",
                "Canned Food",
                "Beverages",
                "Other"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        categorySpinner.setAdapter(adapter);
        form.addView(categorySpinner);

        EditText expiryInput = createInput("Expiry date (dd/MM/yyyy)");
        form.addView(expiryInput);

        if (editing) {

            nameInput.setText(ingredient.getName());
            quantityInput.setText(ingredient.getQuantity());
            expiryInput.setText(ingredient.getExpiry());

            int position = adapter.getPosition(ingredient.getCategory());

            if (position >= 0) {
                categorySpinner.setSelection(position);
            }
        }

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(editing ? "Edit Ingredient" : "Add Ingredient")
                .setView(form)
                .setPositiveButton(editing ? "Update" : "Save", null)
                .setNegativeButton("Cancel", (d, which) -> d.dismiss())
                .create();

        dialog.setOnShowListener(d -> dialog.getButton(
                AlertDialog.BUTTON_POSITIVE
        ).setOnClickListener(v -> {

            String name = nameInput.getText().toString().trim();
            String quantity = quantityInput.getText().toString().trim();
            String category = categorySpinner.getSelectedItem().toString();
            String expiry = expiryInput.getText().toString().trim();

            if (name.isEmpty() || quantity.isEmpty() || expiry.isEmpty()) {
                Toast.makeText(
                        this,
                        "Please complete all fields.",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            if (!isValidDate(expiry)) {
                expiryInput.setError("Use dd/MM/yyyy");
                Toast.makeText(
                        this,
                        "Enter a valid expiry date, e.g. 25/12/2026.",
                        Toast.LENGTH_LONG
                ).show();
                return;
            }

            boolean success;

            if (editing) {
                success = dbHelper.updateIngredient(
                        ingredient.getId(),
                        name,
                        quantity,
                        category,
                        expiry
                );
            } else {
                success = dbHelper.addIngredient(
                        name,
                        quantity,
                        category,
                        expiry
                );
            }

            if (success) {
                Toast.makeText(
                        this,
                        editing ? "Ingredient updated." : "Ingredient saved.",
                        Toast.LENGTH_SHORT
                ).show();

                loadIngredients();
                dialog.dismiss();
            } else {
                Toast.makeText(
                        this,
                        "Could not save the ingredient. Please try again.",
                        Toast.LENGTH_LONG
                ).show();
            }
        }));

        dialog.show();
    }

    private EditText createInput(String hint) {

        EditText input = new EditText(this);
        input.setHint(hint);
        input.setTextColor(textColor);
        input.setHintTextColor(Color.GRAY);
        input.setTextSize(15);
        input.setSingleLine(true);

        return input;
    }

    private boolean isValidDate(String date) {

        SimpleDateFormat format = new SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
        );

        format.setLenient(false);

        try {
            Date parsedDate = format.parse(date);
            return parsedDate != null;
        } catch (ParseException e) {
            return false;
        }
    }

    private String getExpiryStatus(String expiry) {

        SimpleDateFormat format = new SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
        );

        format.setLenient(false);

        try {
            Date expiryDate = format.parse(expiry);

            if (expiryDate == null) {
                return "Expiry date unavailable";
            }

            SimpleDateFormat dayFormat = new SimpleDateFormat(
                    "yyyyMMdd",
                    Locale.getDefault()
            );

            String todayString = dayFormat.format(new Date());
            String expiryString = dayFormat.format(expiryDate);

            int comparison = expiryString.compareTo(todayString);

            if (comparison < 0) {
                return "Expired";
            } else if (comparison == 0) {
                return "Expires today";
            } else {
                return "Not expired";
            }

        } catch (ParseException e) {
            return "Expiry date unavailable";
        }
    }

    private int getExpiryColor(String expiry) {

        String status = getExpiryStatus(expiry);

        if (status.equals("Expired")) {
            return Color.RED;
        } else if (status.equals("Expires today")) {
            return Color.rgb(230, 126, 34);
        }

        return darkGreen;
    }

    private void confirmDelete(Ingredient ingredient) {

        new AlertDialog.Builder(this)
                .setTitle("Delete Ingredient")
                .setMessage(
                        "Are you sure you want to delete " +
                                ingredient.getName() + "?"
                )
                .setPositiveButton("Delete", (dialog, which) -> {

                    boolean success =
                            dbHelper.deleteIngredient(ingredient.getId());

                    if (success) {
                        Toast.makeText(
                                this,
                                "Ingredient deleted.",
                                Toast.LENGTH_SHORT
                        ).show();

                        loadIngredients();
                    } else {
                        Toast.makeText(
                                this,
                                "Could not delete ingredient.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}