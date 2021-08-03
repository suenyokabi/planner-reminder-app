package com.example.alarmreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.alarmreminder.data.DatabaseHelper;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {
RecyclerView recyclerView;
    FloatingActionButton fab3;
    NoteAdapter adapter;
    List<NoteModel> notesList;
    DatabaseHelper sqLiteManager;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        // initialize and assign variable
        BottomNavigationView bottomNavigationView= findViewById(R.id.bottom_navigation);

        // set Home selected
        bottomNavigationView.setSelectedItemId(R.id.notes);
        //perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.notes:

                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,MainActivity.class));
                        overridePendingTransition(0,0);

                        return true;
                    case R.id.task:
                        startActivity(new Intent(getApplicationContext()
                                ,TaskActivity.class));
                        overridePendingTransition(0,0);
                        return true;


                }
                return false;
            }
        });

      recyclerView=findViewById(R.id.recycler_view);
      fab3=findViewById(R.id.fab3);
      coordinatorLayout=findViewById(R.id.layout_main);

      fab3.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent= new Intent(NotesActivity.this, AddNotesActivity.class);
              startActivity(intent);

          }
      });



      notesList=new ArrayList<>();
      sqLiteManager=new DatabaseHelper(this);
      fetchAllNotesFromDatabase();
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      adapter=new NoteAdapter(this, NotesActivity.this,notesList);
      recyclerView.setAdapter(adapter);

ItemTouchHelper helper= new ItemTouchHelper(callback);
helper.attachToRecyclerView(recyclerView);







}

    private void  fetchAllNotesFromDatabase() {
      Cursor cursor = sqLiteManager.readAllData();

       if (cursor.getCount() == 0) {
           Toast.makeText(this, "No Data to show", Toast.LENGTH_SHORT).show();
     } else {
            while (cursor.moveToNext()) {
               notesList.add(new NoteModel(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
           }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.searchbar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search Notes Here");

        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //adapter.getFilter().filter(newText);
                return true;
            }
        };

        searchView.setOnQueryTextListener(listener);


        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.delete_all_notes) {
            deleteAllNotes();
        }


        return super.onOptionsItemSelected(item);
    }
    private void deleteAllNotes(){
        DatabaseHelper sqLiteManager= new DatabaseHelper(NotesActivity.this);
        sqLiteManager.deleteAllNotes();
        recreate();
    }
    ItemTouchHelper.SimpleCallback callback= new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull  RecyclerView.ViewHolder viewHolder,  RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull  RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            NoteModel item = adapter.getList().get(position);

            adapter.removeItem(viewHolder.getAdapterPosition());

            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Item Deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapter.restoreItem(item, position);
                            recyclerView.scrollToPosition(position);
                        }
                    }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);

                            if (!(event == DISMISS_EVENT_ACTION)) {
                                DatabaseHelper sqLiteManger = new DatabaseHelper(NotesActivity.this);
                                sqLiteManger.deleteSingleItem(item.getId());
                            }


                        }
                    });

            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    };


}