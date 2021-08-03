package com.example.alarmreminder;

import androidx.appcompat.app.AppCompatActivity;
import com.example.alarmreminder.data.DatabaseHelper;
import com.example.alarmreminder.data.DatabaseHelper;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class AddNotesActivity extends AppCompatActivity {
    EditText note_title, note_description;
    Button addNotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        //hooks to the widgets
        note_title=findViewById(R.id.note_title);
        note_description=findViewById(R.id.note_description);
        addNotes=findViewById(R.id.addNote);

                addNotes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(note_title.getText().toString()) && !TextUtils.isEmpty(note_description.getText().toString()))
                        {
//                            DatabaseHelper db= new DatabaseHelper(
                            DatabaseHelper.addNotes(note_title.getText().toString(),
                                         note_description.getText().toString(),AddNotesActivity.this);

                            Intent intent = new Intent(AddNotesActivity.this,NotesActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
else {
    Toast.makeText(AddNotesActivity.this, "Both Fields Required", Toast.LENGTH_SHORT).show();}
                    }
                });

    }
}