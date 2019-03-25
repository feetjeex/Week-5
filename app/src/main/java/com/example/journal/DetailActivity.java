package com.example.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Extracts the data of the selected JournalEntry object from a bundle
        // Sets all fields of the JournalEntry object using this data
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String entryTitle = extras.getString("intent_title");
        String entryContent = extras.getString("intent_content");
        int entryMood = extras.getInt("intent_mood");
        String entryTimeStamp = extras.getString("intent_timestamp");

        // Assigning the UI elements
        TextView title = findViewById(R.id.detailTitle);
        ImageView imageMood = findViewById(R.id.imageMood);
        TextView text = findViewById(R.id.detailText);
        TextView time = findViewById(R.id.entryRowDate);

        // Check which Mood was selected by the user upon submitting the JournalEntry
        switch (entryMood) {
            case 1:
                imageMood.setImageResource(R.drawable.one);
                break;
            case 2:
                imageMood.setImageResource(R.drawable.two);
                break;
            case 3:
                imageMood.setImageResource(R.drawable.three);
                break;
            case 4:
                imageMood.setImageResource(R.drawable.four);
                break;
        }

        // Sets the text of the UI elements
        title.setText(entryTitle);
        text.setText(entryContent);
        time.setText(entryTimeStamp);
    }
}
