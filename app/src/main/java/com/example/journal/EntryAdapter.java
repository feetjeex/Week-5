package com.example.journal;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class EntryAdapter extends ResourceCursorAdapter {

    // Constructor of the class
    public EntryAdapter(Context context, Cursor cursor) {
        super(context, R.layout.entry_row, cursor);
    }

    public void bindView(View view, Context context, Cursor cursor) {

        // Assigning the UI elements
        TextView entryTitle = view.findViewById(R.id.entryRowTitle);
        TextView entryDate = view.findViewById(R.id.entryRowDate);
        ImageView entryMood = view.findViewById(R.id.imageMood);

        // Sets the UI elements using data from the cursor
        entryTitle.setText(cursor.getString(1));
        entryDate.setText(cursor.getString(4));

        // Switch statement used to find the mood that the user put in
        int mood = cursor.getInt(3);
        switch (mood) {
            case 1:
                entryMood.setImageResource(R.drawable.one);
            break;
            case 2:
                entryMood.setImageResource(R.drawable.two);
            break;
            case 3:
                entryMood.setImageResource(R.drawable.three);
            break;
            case 4:
                entryMood.setImageResource(R.drawable.four);
            break;
        }
    }
}

