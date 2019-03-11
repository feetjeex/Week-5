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
    private static final String TAG = "MainActivity";

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

        db = EntryDatabase.getInstance(this);

        cursor = db.selectAll();
        adapter = new EntryAdapter(this, cursor);
        listView.setAdapter(adapter);
    }

    private void updateData () {
        adapter.swapCursor(db.selectAll());
    }

    private class fabMainActivityListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, InputActivity.class);
            startActivity(intent);
        }
    }

    //Title, Content, Mood, Timestamp

    private class ClickViewListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor clickedJournalEntry = (Cursor) parent.getItemAtPosition(position);
            String title = clickedJournalEntry.getString(1);
            String content = clickedJournalEntry.getString(2);
            int mood = clickedJournalEntry.getInt(3);
            String timestamp = clickedJournalEntry.getString(4);


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

    private class LongClickViewListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            Cursor cursorDeleter = (Cursor) parent.getItemAtPosition(position);
            EntryDatabase entryDatabase = getInstance(getApplicationContext());
            int columnNumber = cursorDeleter.getColumnIndex("_id");
            Log.d(TAG, "columnNumber: " + columnNumber);
            int idNumber = cursorDeleter.getInt(columnNumber);
            Log.d(TAG, "idNumber: " + idNumber);
            int result = entryDatabase.deleter(idNumber);
            Log.d(TAG, "result: " + result);
            updateData();
            return true;
        }
    }
}
