package com.example.journal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import static com.example.journal.EntryDatabase.getInstance;

public class MainActivity extends AppCompatActivity {

    Cursor cursor;
    EntryAdapter adapter;
    EntryDatabase db;

    // Implements the onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Implementing the floating action button and its listener
        FloatingActionButton fab = findViewById(R.id.fab);
        View.OnClickListener fabListener = new fabMainActivityListener();
        fab.setOnClickListener(fabListener);

        // Implementing the ListView module and listeners
        ListView listView = findViewById(R.id.listView);
        AdapterView.OnItemClickListener listViewListener = new ClickViewListener();
        listView.setOnItemClickListener(listViewListener);
        AdapterView.OnItemLongClickListener lv = new LongClickViewListener();
        listView.setOnItemLongClickListener(lv);

        // Declaration and initialization of a db object
        db = EntryDatabase.getInstance(this);
        cursor = db.selectAll();

        // Declaration, initialization and assignment of a new EntryAdapter object
        adapter = new EntryAdapter(this, cursor);
        listView.setAdapter(adapter);
    }

    // Method used to update the data
    private void updateData () {
        adapter.swapCursor(db.selectAll());
    }

    // Implements the onClickListener for the floating action button
    private class fabMainActivityListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // OnClick, transfers the user to the InputActivity
            Intent intent = new Intent(MainActivity.this, InputActivity.class);
            startActivity(intent);
        }
    }

    // Implements the OnItemClickListener for the ListView containing the JournalEntries
    private class ClickViewListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // Declaring, initializing and assigning the fields of a JournalEntry from the cursor object
            Cursor clickedJournalEntry = (Cursor) parent.getItemAtPosition(position);
            String title = clickedJournalEntry.getString(1);
            String content = clickedJournalEntry.getString(2);
            int mood = clickedJournalEntry.getInt(3);
            String timestamp = clickedJournalEntry.getString(4);

            // Transferring all the fields of a JournalEntry using a Bundle, and transferring the user to the DetailActivity
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            Bundle extras = new Bundle();
            extras.putString("intent_title", title);
            extras.putString("intent_content",content);
            extras.putInt("intent_mood", mood);
            extras.putString("intent_timestamp", timestamp);
            intent.putExtras(extras);
            startActivity(intent);
        }
    }

    // Implementing the OnItemLongClickListener
    private class LongClickViewListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            // Implementing the cursorDeleter which contains the data on the selected JournalEntry (to be deleted)
            Cursor cursorDeleter = (Cursor) parent.getItemAtPosition(position);
            EntryDatabase entryDatabase = getInstance(getApplicationContext());
            int columnNumber = cursorDeleter.getColumnIndex("_id");
            int idNumber = cursorDeleter.getInt(columnNumber);
            int result = entryDatabase.deleter(idNumber);
            updateData();
            return true;
        }
    }
}
