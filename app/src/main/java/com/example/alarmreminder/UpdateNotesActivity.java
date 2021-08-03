package com.example.alarmreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alarmreminder.data.DatabaseHelper;

public class UpdateNotesActivity extends AppCompatActivity {
    EditText note_title,note_description;
    Button updateNotes;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);

        note_title= findViewById(R.id.note_title);
        note_description= findViewById(R.id.note_description);
        updateNotes=findViewById(R.id.updateNote);

        Intent i =getIntent();
        note_title.setText(i.getStringExtra("note_title"));
        note_description.setText(i.getStringExtra("note_description"));
        id=i.getStringExtra("id");
        updateNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(note_title.getText().toString()) && !TextUtils.isEmpty(note_description.getText().toString()))
                {

                    DatabaseHelper sqLiteManager = new DatabaseHelper(UpdateNotesActivity.this);
                    sqLiteManager.updateNotes(note_title.getText().toString(),note_description.getText().toString(),id);

                    Intent i=new Intent(UpdateNotesActivity.this,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();


                }
                else
                {
                    Toast.makeText(UpdateNotesActivity.this, "Both Fields Required", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}