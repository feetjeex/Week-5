package com.example.journal;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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

        Button submitEntry = findViewById(R.id.submitEntry);
        View.OnClickListener listener = new MyClickListener();
        submitEntry.setOnClickListener(listener);

        inputTitle = findViewById(R.id.inputTitle);
        inputText = findViewById(R.id.inputText);
        moodOne = findViewById(R.id.imageButton1);
        moodTwo = findViewById(R.id.imageButton2);
        moodThree = findViewById(R.id.imageButton3);
        moodFour = findViewById(R.id.imageButton4);
    }

    public void button1(View view) {
        moodTwo.setBackgroundColor(Color.parseColor("#ffffff"));
        moodThree.setBackgroundColor(Color.parseColor("#ffffff"));
        moodFour.setBackgroundColor(Color.parseColor("#ffffff"));
        moodOne.setBackgroundColor(Color.parseColor("#808080"));
        mood = 1;
    }

    public void button2(View view) {
        moodOne.setBackgroundColor(Color.parseColor("#ffffff"));
        moodThree.setBackgroundColor(Color.parseColor("#ffffff"));
        moodFour.setBackgroundColor(Color.parseColor("#ffffff"));
        moodTwo.setBackgroundColor(Color.parseColor("#808080"));
        mood = 2;
    }

    public void button3(View view) {
        moodTwo.setBackgroundColor(Color.parseColor("#ffffff"));
        moodThree.setBackgroundColor(Color.parseColor("#808080"));
        moodFour.setBackgroundColor(Color.parseColor("#ffffff"));
        moodOne.setBackgroundColor(Color.parseColor("#ffffff"));
        mood = 3;
    }

    public void button4(View view) {
        moodTwo.setBackgroundColor(Color.parseColor("#ffffff"));
        moodThree.setBackgroundColor(Color.parseColor("#ffffff"));
        moodFour.setBackgroundColor(Color.parseColor("#808080"));
        moodOne.setBackgroundColor(Color.parseColor("#ffffff"));
        mood = 4;
    }

    private class MyClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // Add code to add journal to database
            String Title = inputTitle.getText().toString();
            String Text = inputText.getText().toString();

            Long tsLong = System.currentTimeMillis()/1000;
            String ts = tsLong.toString();

            // Storing all the data in a journalEntry object
            journalEntry.setTitle(Title);
            journalEntry.setContent(Text);
            journalEntry.setMood(mood);
            journalEntry.setTimestamp(ts);

            addEntry();
            Intent intent = new Intent(InputActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void addEntry () {
        EntryDatabase entryDatabase = EntryDatabase.getInstance(this);
        entryDatabase.insert(journalEntry);
    }
}
