package com.example.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Map;

public class EntryDatabase extends SQLiteOpenHelper {

    private static final String TAG = "MainActivity";
    Cursor cursor;

    private EntryDatabase(Context context) {
        super(context, "entries", null, 1);
    }

    private static EntryDatabase instance;

    public static EntryDatabase getInstance(Context context) {
        if (instance != null) {
            return instance;
        }

        else {
            instance = new EntryDatabase(context);
            return instance;
        }
    }

    public long insert(JournalEntry journalEntry) {

        getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", journalEntry.getTitle());
        contentValues.put("Content", journalEntry.getContent());
        contentValues.put("Mood", journalEntry.getMood());
        contentValues.put("Timestamp", journalEntry.getTimestamp());

        return getWritableDatabase().insert("entries", null, contentValues);
    }

    public Cursor selectAll() {
        SQLiteDatabase db;
        db = getWritableDatabase();
        cursor = db.rawQuery("SELECT * FROM entries", null);
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Creates the table entries
        db.execSQL("create table entries (_id INTEGER PRIMARY KEY AUTOINCREMENT, Title TEXT, Content TEXT, Mood INTEGER, Timestamp TEXT);");

        db.execSQL("insert into entries (_id, Title, Content, Mood, Timestamp) VALUES (1, 'First Entry', 'This is the first entry', 1, '12:42 - 4/3/2019');");

        db.execSQL("insert into entries (_id, Title, Content, Mood, Timestamp) VALUES (2, 'Second Entry', 'This is the second entry', 3, '12:42 - 9/3/2019');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "entries");
        onCreate(db);
    }


    public Integer deleter(long _id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String id =String.valueOf(_id);
        return db.delete("entries", "_id = ?", new String[]{id});
    }
}
