package com.example.taskreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class Sport extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> name, category, email, age;
    DBHelper DB;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        setContentView(R.layout.activity_category);
        DB = new DBHelper(getApplicationContext());
        name = new ArrayList<>();
        category = new ArrayList<>();
        email = new ArrayList<>();
        age = new ArrayList<>();

        displayData();

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MyAdapter(this, name, category, email, age);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

    }
    private void displayData() {
        Cursor cursor = DB.getSportData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        } else {
            while (cursor.moveToNext()) {
                name.add(cursor.getString(0));
                category.add(cursor.getString(1));
                email.add(cursor.getString(2));
                age.add(cursor.getString(3));
            }
        }
    }
}