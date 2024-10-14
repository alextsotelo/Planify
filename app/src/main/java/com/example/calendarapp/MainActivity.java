package com.example.calendarapp;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Comment out the next line to disable the Toast
            // String date = dayOfMonth + "/" + (month + 1) + "/" + year; // Month is 0-indexed
            // Toast.makeText(MainActivity.this, "Selected date: " + date, Toast.LENGTH_SHORT).show();
        });


    }
    public void launchToDoList (View v) {
        //launch a new activity

        Intent i = new Intent(this, List.class);
        startActivity(i);
    }
}
