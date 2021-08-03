package com.example.alarmreminder;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;




public class SpalshActivity extends AppCompatActivity {

    static int TIMEOUT_MILLIS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i= new Intent(SpalshActivity.this, MainActivity.class);
                startActivity(i);
//close this activity
                finish();

            }
        }, TIMEOUT_MILLIS);
}
}