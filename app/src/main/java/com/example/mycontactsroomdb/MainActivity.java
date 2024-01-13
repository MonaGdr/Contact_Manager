package com.example.mycontactsroomdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mycontactsroomdb.adapter.CustomAdapter;
import com.example.mycontactsroomdb.db.ContactDatabase;
import com.example.mycontactsroomdb.db.entity.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    CustomAdapter adapter;

    List<Contact> contacts = new ArrayList<>();

    private ContactDatabase contactDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //make an instance of database
        contactDatabase = Room.databaseBuilder(
                getApplicationContext(),
                ContactDatabase.class,
                "db.contact"
                ).allowMainThreadQueries().build();

        //get the data from db
        //contacts = contactDatabase.getContactDAO().getAllContacts(); replaced by multi threading approach
        loadDataFromDb();

        //Call backs
        RoomDatabase.Callback callback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);

            }
        };


        //showing the list of contacts using recycler view
        adapter = new CustomAdapter(MainActivity.this, contacts, getSupportFragmentManager());
        rv.setAdapter(adapter);
        rv.setLayoutManager( new LinearLayoutManager(MainActivity.this));

    }

    private void loadDataFromDb() {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {

                //load data
                contacts.addAll( contactDatabase.getContactDAO().getAllContacts());

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        adapter.notifyDataSetChanged();

                    }
                });
            }
        });
    }

    private void initView() {
        rv = findViewById(R.id.recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //show the menu in which the user can add new contact
        getMenuInflater().inflate(R.menu.menu_layout , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // create alert dialog to add new contact
        ContactDialog contactDialog = new ContactDialog(MainActivity.this , false, adapter);
        contactDialog.show(getSupportFragmentManager(), "add contact");

        return super.onOptionsItemSelected(item);
    }


}