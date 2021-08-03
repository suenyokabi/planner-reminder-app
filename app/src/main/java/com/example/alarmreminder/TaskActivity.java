package com.example.alarmreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.alarmreminder.Adapter.ToDoAdapter;
import com.example.alarmreminder.Model.ToDoModel;
import com.example.alarmreminder.data.DatabaseHelper;
import com.example.alarmreminder.data.DatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskActivity extends AppCompatActivity implements OnDialogCloseListner{
    private RecyclerView mRecyclerview;
    private FloatingActionButton fab;
    private DatabaseHelper sqLiteManager;
    private List<ToDoModel> mList;
    private ToDoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        mRecyclerview=findViewById(R.id.recyclerview);
        fab=findViewById(R.id.fab2);
        sqLiteManager=new DatabaseHelper(TaskActivity.this);
        mList = new ArrayList<>();
        adapter = new ToDoAdapter(sqLiteManager , TaskActivity.this);

        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(adapter);


        mList = sqLiteManager.getAllTasks();
        Collections.reverse(mList);
        adapter.setTasks(mList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
           //ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
            //itemTouchHelper.attachToRecyclerView(mRecyclerview);


        });

        // initialize and assign variable
        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_navigation);

        // set Home selected
        bottomNavigationView.setSelectedItemId(R.id.task);
        //perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.notes:
                        startActivity(new Intent(getApplicationContext()
                                ,NotesActivity.class));
                        overridePendingTransition(0,0);

                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,MainActivity.class));
                        overridePendingTransition(0,0);

                        return true;
                    case R.id.task:

                        return true;


                }
                return false;
            }
        });









}

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList = sqLiteManager.getAllTasks();
        Collections.reverse(mList);
        adapter.setTasks(mList);
        adapter.notifyDataSetChanged();
    }
}