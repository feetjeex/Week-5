package com.example.journal;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InputActivity extends AppCompatActivity {

    int mood;
    EditText inputTitle;
    EditText inputText;
    ImageButton moodOne;
    ImageButton moodTwo;
    ImageButton moodThree;
    ImageButton moodFour;
    JournalEntry journalEntry = new JournalEntry();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // Assigns the OnClickListener
        Button submitEntry = findViewById(R.id.submitEntry);
        View.OnClickListener listener = new MyClickListener();
        submitEntry.setOnClickListener(listener);

        // Assigns the UI elements
        inputTitle = findViewById(R.id.inputTitle);
        inputText = findViewById(R.id.inputText);
        moodOne = findViewById(R.id.imageButton1);
        moodTwo = findViewById(R.id.imageButton2);
        moodThree = findViewById(R.id.imageButton3);
        moodFour = findViewById(R.id.imageButton4);
    }

    // Sets the background for the buttons after the user has pressed one of the buttons
    public void button1(View view) {
        moodTwo.setBackgroundColor(Color.parseColor("#ffffff"));
        moodThree.setBackgroundColor(Color.parseColor("#ffffff"));
        moodFour.setBackgroundColor(Color.parseColor("#ffffff"));
        moodOne.setBackgroundColor(Color.parseColor("#808080"));
        mood = 1;
    }

    // Sets the background for the buttons after the user has pressed one of the buttons
    public void button2(View view) {
        moodOne.setBackgroundColor(Color.parseColor("#ffffff"));
        moodThree.setBackgroundColor(Color.parseColor("#ffffff"));
        moodFour.setBackgroundColor(Color.parseColor("#ffffff"));
        moodTwo.setBackgroundColor(Color.parseColor("#808080"));
        mood = 2;
    }

    // Sets the background for the buttons after the user has pressed one of the buttons
    public void button3(View view) {
        moodTwo.setBackgroundColor(Color.parseColor("#ffffff"));
        moodThree.setBackgroundColor(Color.parseColor("#808080"));
        moodFour.setBackgroundColor(Color.parseColor("#ffffff"));
        moodOne.setBackgroundColor(Color.parseColor("#ffffff"));
        mood = 3;
    }

    // Sets the background for the buttons after the user has pressed one of the buttons
    public void button4(View view) {
        moodTwo.setBackgroundColor(Color.parseColor("#ffffff"));
        moodThree.setBackgroundColor(Color.parseColor("#ffffff"));
        moodFour.setBackgroundColor(Color.parseColor("#808080"));
        moodOne.setBackgroundColor(Color.parseColor("#ffffff"));
        mood = 4;
    }

    // Implements the OnClickListener
    private class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // Assigns values to the strings used in the JournalEntry
            String Title = inputTitle.getText().toString();
            String Text = inputText.getText().toString();

            // Code to get the Timestamp field
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm - dd/MM/yyyy");
            String format = simpleDateFormat.format(new Date());

            // Storing all the data in a journalEntry object
            journalEntry.setTitle(Title);
            journalEntry.setContent(Text);
            journalEntry.setMood(mood);
            journalEntry.setTimestamp(format);

            // Calls the 'addEntry' method
            addEntry();
            Intent intent = new Intent(InputActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    // Method which inserts the selected JournalEntry in the database
    public void addEntry () {
        EntryDatabase entryDatabase = EntryDatabase.getInstance(this);
        entryDatabase.insert(journalEntry);
    }
}
