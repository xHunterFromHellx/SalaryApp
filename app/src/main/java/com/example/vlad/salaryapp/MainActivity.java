package com.example.vlad.salaryapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    TimePicker time;
    Button startButton;
    Button endButton;
    Button nextButton;
    public DatePicker date;
    public static int startHour;
    public static int startMin;
    public static int endHour;
    public static int endMin;
    public static int currentYear;
    public static int currentMonth;
    public static int currentDay;
    public static int checkEndButton = 0;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = (TimePicker)findViewById(R.id.timePicker);
        startButton = (Button)findViewById(R.id.startButton);
        endButton = (Button)findViewById(R.id.endButton);
        nextButton = (Button)findViewById(R.id.buttonNext);
        date = (DatePicker)findViewById(R.id.datePicker);


        time.setIs24HourView(true);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHour = time.getHour();
               startMin = time.getMinute();
                File hour = new File(getApplicationContext().getFilesDir() + File.separator + "h.txt");
                try
                {
                    PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(hour)));
                    printWriter.println(startHour);
                    printWriter.flush();
                    printWriter.close();
                }
                catch (IOException io)
                {
                    io.printStackTrace();
                }

                File minute = new File(getApplicationContext().getFilesDir() + File.separator + "m.txt");

                try
                {
                    PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(minute)));
                    printWriter.println(startMin);
                    printWriter.flush();
                    printWriter.close();
                }
                catch (IOException io)
                {
                    io.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "You started on " + startHour + ":" + startMin, Toast.LENGTH_LONG).show();
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endHour = time.getHour();
                endMin = time.getMinute();
                checkEndButton = 1;
                Toast.makeText(getApplicationContext(), "You finished on " + endHour + ":" + endMin, Toast.LENGTH_LONG).show();



                try {
                    startHour = Integer.parseInt(new Scanner(new File(getApplicationContext().getFilesDir() + File.separator + "h.txt")).nextLine());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                try {
                    startMin = Integer.parseInt(new Scanner(new File(getApplicationContext().getFilesDir() + File.separator + "m.txt")).nextLine());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentYear = date.getYear();
                currentMonth = date.getMonth();
                currentDay = date.getDayOfMonth();
                startActivity(new Intent(MainActivity.this, SummaryActivity.class));
            }
        });
    }
}
