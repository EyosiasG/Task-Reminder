package com.example.taskreminder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabCreate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabCreate extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TabCreate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabCreate.
     */
    // TODO: Rename and change types and number of parameters
    public static TabCreate newInstance(String param1, String param2) {
        TabCreate fragment = new TabCreate();
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
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Button startTimeButton;
    private DBHelper db;
    private   String name, date,category, startTime;
    int hour, minute;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_tab_create, container, false);
        initDatePicker();

        db = new DBHelper(getContext());


        dateButton = root.findViewById(R.id.datePickerDialog);


        startTimeButton = root.findViewById(R.id.startTimeBtn);

        Spinner spinner = root.findViewById(R.id.spinner);
        Spinner am_pm_Button = root.findViewById(R.id.am_pm_spinner);

        String[] categoryList = {"Select Category","School", "Work", "Sports", "Family"};
        String[] am_pm_List = {"AM", "PM"};

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(
                getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,categoryList
        );
        ArrayAdapter<String> am_pm_Adapter = new ArrayAdapter<String>(
                getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,am_pm_List
        );


        spinner.setAdapter(monthAdapter);
        am_pm_Button.setAdapter(am_pm_Adapter);

        long millis = System.currentTimeMillis();
        java.sql.Date mydate = new java.sql.Date(millis);
        String dateInString = mydate.toString();
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH);
        try {
            mydate = (java.sql.Date) formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(mydate.toString());


        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        java.sql.Date finalMydate = mydate;
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                        startTimeButton.setText(String.format(Locale.getDefault(),"%02d:%02d",hour, minute));

                    }
                };
                int style = AlertDialog.THEME_HOLO_DARK;
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), style, onTimeSetListener, hour, minute, true);

                timePickerDialog.setTitle(finalMydate.toString());
                timePickerDialog.show();
            }
        });


        EditText editTextName = root.findViewById(R.id.nameEditText);
        Button buttonCreate = root.findViewById(R.id.createTaskBtn);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = editTextName.getText().toString();
                category = spinner.getSelectedItem().toString();
                try {
                    date = todaysDate(dateButton.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                startTime = startTimeButton.getText().toString() +  " " + am_pm_Button.getSelectedItem().toString();


                if(name.isEmpty() || date.isEmpty() || startTime.isEmpty())
                    Toast.makeText(getContext(), "Cannot Submit Empty Fields!", Toast.LENGTH_SHORT).show();
                else{
                    try{
                        boolean checkUserData = db.insertUserData(name, category,date, startTime);
                        if(checkUserData == true){
                            Toast.makeText(getContext(), "New Entry Inserted", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getContext(), "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                        }

                        editTextName.getText().clear();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        return root;
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month,year);


    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }

    private String makeDateString(int day, int month, int year) {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "January";
        if(month == 2)
            return "February";
        if(month == 3)
            return "March";
        if(month == 4)
            return "April";
        if(month == 5)
            return "May";
        if(month == 6)
            return "Jun";
        if(month == 7)
            return "July";
        if(month == 8)
            return "August";
        if(month == 9)
            return "September";
        if(month == 10)
            return "October";
        if(month == 11)
            return "November";
        if(month == 12)
            return "December";

        return "January";

    }
    public String todaysDate(String dateInString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH);

        Date date = formatter.parse(dateInString);
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String _date = simpleDateFormat.format(date);

        return _date;
    }

}