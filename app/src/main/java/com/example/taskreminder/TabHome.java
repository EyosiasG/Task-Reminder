package com.example.taskreminder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabHome extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TabHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabHome.
     */
    // TODO: Rename and change types and number of parameters
    public static TabHome newInstance(String param1, String param2) {
        TabHome fragment = new TabHome();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }
    RecyclerView recyclerView;
    ArrayList<String> name, category, email, age;
    DBHelper DB;
    MyAdapter adapter;
    List<Item> tasks;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_tab_home, container, false);

        DB = new DBHelper(getContext());
        name = new ArrayList<>();
        category = new ArrayList<>();
        email = new ArrayList<>();
        age = new ArrayList<>();

        recyclerView = root.findViewById(R.id.recyclerView);
        TextView pendingTasks = root.findViewById(R.id.pendingTasks);

        CardView schoolCat = root.findViewById(R.id.schoolCat);
        CardView sportCat = root.findViewById(R.id.sportCat);
        CardView workCat = root.findViewById(R.id.workCat);
        CardView familyCat = root.findViewById(R.id.familyCat);

        schoolCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Category.class);
                startActivity(intent);
            }
        });

        sportCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Sport.class);
                startActivity(intent);
            }
        });

        workCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Work.class);
                startActivity(intent);
            }
        });

        familyCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Family.class);
                startActivity(intent);
            }
        });
        adapter = new MyAdapter(getContext(), name, category, email, age);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        displayData();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String date = todaysDate();
        }
        pendingTasks.setText("You have " + name.size() + " pending tasks");
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String todaysDate(){
        String string = "January 2 2010";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(string, formatter);
        return date.toString();
    }


    public void onClick(){
        Cursor cursor = DB.getData();
        if(cursor.getCount()==0){
            Toast.makeText(getContext(), "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            while(cursor.moveToNext()){
                name.add(cursor.getString(0));
                category.add(cursor.getString(1));
                email.add(cursor.getString(2));
                age.add(cursor.getString(3));
            }
        }
    }

    private void displayData() {
        Cursor cursor = DB.getTodaysData();
        if(cursor.getCount()==0){
            Toast.makeText(getContext(), "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            while(cursor.moveToNext()){
                name.add(cursor.getString(0));
                category.add(cursor.getString(1));
                email.add(cursor.getString(2));
                age.add(cursor.getString(3));
            }
        }
    }
}