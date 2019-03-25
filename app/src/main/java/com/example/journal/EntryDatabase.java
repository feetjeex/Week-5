package com.example.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Map;

public class EntryDatabase extends SQLiteOpenHelper {

    Cursor cursor;
    private static EntryDatabase instance;

    // Constructor of the class
    private EntryDatabase(Context context) {
        super(context, "entries", null, 1);
    }

    // Checks if an instance already exists, if not: Creates one
    public static EntryDatabase getInstance(Context context) {
        if (instance != null) {
            return instance;
        }

        else {
            instance = new EntryDatabase(context);
            return instance;
        }
    }

    // Implements the insert method
    public long insert(JournalEntry journalEntry) {

        // Establishes a connection with the database
        // Assigns values to all the fields of the table in the database using data from the JournalEntry object
        getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", journalEntry.getTitle());
        contentValues.put("Content", journalEntry.getContent());
        contentValues.put("Mood", journalEntry.getMood());
        contentValues.put("Timestamp", journalEntry.getTimestamp());

        // Returns the database upon success
        return getWritableDatabase().insert("entries", null, contentValues);
    }

    // Method to select all rows from the database
    public Cursor selectAll() {
        SQLiteDatabase db;
        db = getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM entries", null);

        // Returns the cursor containing all the rows from the database
        return cursor;
    }

    // Method to create the table entries in the database
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Creates the table entries
        db.execSQL("create table entries (_id INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Content TEXT, Mood INTEGER, Timestamp TEXT);");
    }

    // Method used to delete the old table entries and create a new one
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "entries");
        onCreate(db);
    }

    // Method used to delete a certain row in the table entries
    public Integer deleter(long _id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String id =String.valueOf(_id);
        return db.delete("entries", "_id = ?", new String[]{id});
    }
}
