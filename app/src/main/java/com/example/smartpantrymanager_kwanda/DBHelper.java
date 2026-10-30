package com.example.smartpantrymanager_kwanda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartPantryKwanda.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_INGREDIENTS = "ingredients";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TABLE_INGREDIENTS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "quantity TEXT NOT NULL, " +
                "category TEXT NOT NULL, " +
                "expiry TEXT NOT NULL)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        onCreate(db);
    }

    public boolean addIngredient(String name, String quantity,
                                 String category, String expiry) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("quantity", quantity);
        values.put("category", category);
        values.put("expiry", expiry);

        long result = db.insert(TABLE_INGREDIENTS, null, values);

        db.close();

        return result != -1;
    }

    public ArrayList<Ingredient> getIngredients() {

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_INGREDIENTS + " ORDER BY name ASC",
                null
        );

        try {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow("name"));
                String quantity = cursor.getString(
                        cursor.getColumnIndexOrThrow("quantity"));
                String category = cursor.getString(
                        cursor.getColumnIndexOrThrow("category"));
                String expiry = cursor.getString(
                        cursor.getColumnIndexOrThrow("expiry"));

                ingredients.add(new Ingredient(
                        id, name, quantity, category, expiry
                ));
            }
        } finally {
            cursor.close();
            db.close();
        }

        return ingredients;
    }

    public boolean updateIngredient(int id, String name, String quantity,
                                    String category, String expiry) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("quantity", quantity);
        values.put("category", category);
        values.put("expiry", expiry);

        int result = db.update(
                TABLE_INGREDIENTS,
                values,
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return result > 0;
    }

    public boolean deleteIngredient(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                TABLE_INGREDIENTS,
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return result > 0;
    }
}