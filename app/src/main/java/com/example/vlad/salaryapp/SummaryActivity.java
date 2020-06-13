package com.example.vlad.salaryapp;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class SummaryActivity extends AppCompatActivity {

    Button dayButton;
    Button monthButton;
    Button yearButton;
    EditText salaryPerHour;
    TextView reportSalary;
    int daySummaryHours;
    int daySummaryMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        dayButton = (Button)findViewById(R.id.dayButton);
        monthButton = (Button)findViewById(R.id.monthButton);
        yearButton = (Button)findViewById(R.id.yearButton);
        salaryPerHour = (EditText)findViewById(R.id.salaryPerHour);
        reportSalary = (TextView)findViewById(R.id.reportSalary);
        final String year = String.valueOf(MainActivity.currentYear);
        final String month = String.valueOf(MainActivity.currentMonth);
        final String day = String.valueOf(MainActivity.currentDay);

        if (MainActivity.checkEndButton != 0){
        dayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    daySummaryHours = MainActivity.endHour - MainActivity.startHour;
                    daySummaryMinutes = MainActivity.endMin - MainActivity.startMin;
                    if (daySummaryMinutes < 0)
                    {
                        daySummaryHours -= 1;
                        daySummaryMinutes += 60;
                    }

                    if (daySummaryHours < 0 )
                    {
                        daySummaryHours = 24 - MainActivity.startHour + MainActivity.endHour;
                    }

                    double salaryPerCurrentDay = 0;
                    final String sSalaryPerHour = salaryPerHour.getText().toString();
                    int iSalaryPerHour = Integer.parseInt(sSalaryPerHour);

                    if (daySummaryHours < 8)
                    {
                        salaryPerCurrentDay = daySummaryHours * iSalaryPerHour + ((daySummaryMinutes/60) * iSalaryPerHour);
                    }
                    else if (daySummaryHours > 8 && daySummaryHours < 10)
                    {
                        salaryPerCurrentDay = (daySummaryHours * iSalaryPerHour + daySummaryMinutes * iSalaryPerHour / 60) + ((daySummaryHours - 8) * iSalaryPerHour + daySummaryMinutes * iSalaryPerHour / 60)*0.25;
                    }
                    else
                    {
                        salaryPerCurrentDay = (daySummaryHours * iSalaryPerHour + daySummaryMinutes * iSalaryPerHour / 60) + (2 * iSalaryPerHour + daySummaryMinutes * iSalaryPerHour / 60)*0.25 + ((daySummaryHours - 10) * iSalaryPerHour + daySummaryMinutes * iSalaryPerHour / 60)*0.5;
                    }

                    reportSalary.setText("you are worked today " + daySummaryHours + " hours " + daySummaryMinutes + " minutes\n" + "You will get " + (float)salaryPerCurrentDay + " ILS");

                    File logFolder = new File(getApplicationContext().getFilesDir() + File.separator + "Logs");
                    if (!logFolder.exists())
                    {
                        logFolder.mkdir();
                    }
                    File yearFolder = new File(getApplicationContext().getFilesDir() + File.separator + "Logs" + File.separator + year);
                    if (!yearFolder.exists())
                    {
                        yearFolder.mkdir();
                    }

                    File monthFolder = new File(getApplicationContext().getFilesDir() + File.separator + "Logs" + File.separator + year + File.separator + month);
                    if (!monthFolder.exists())
                    {
                        monthFolder.mkdir();
                    }

                    File dateLogFile = new File(getApplicationContext().getFilesDir() + File.separator + "Logs" + File.separator + year + File.separator + month + File.separator + day + ".txt");

                    try
                    {
                        PrintWriter daySumToFile = new PrintWriter(new BufferedWriter(new FileWriter(dateLogFile)));
                        daySumToFile.println((float)salaryPerCurrentDay);
                        daySumToFile.flush();
                        daySumToFile.close();
                    }
                    catch (IOException io)
                    {
                        io.printStackTrace();
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        });}
        else dayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Select your end time", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SummaryActivity.this, MainActivity.class));
            }
        });


        monthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    double summaryMonthMoney = 0;
                    double everyDayMoney = 0;

                    for (int i = 1; i < 31; i++)
                    {
                        try{
                            File file = new File(getApplicationContext().getFilesDir() + File.separator + "Logs" + File.separator + year + File.separator + month + File.separator + i + ".txt");

                            Scanner scanner = new Scanner(file);
                            everyDayMoney = Double.parseDouble(scanner.nextLine());
                            summaryMonthMoney = summaryMonthMoney + everyDayMoney;
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }

                    reportSalary.setText("You are get " + (float)summaryMonthMoney + " ILS on selected month");
                }
                catch (Exception f)
                {
                    f.printStackTrace();
                }
            }
        });

        yearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double summaryMonthMoney = 0;
                double everyDayMoney = 0;

                for (int i = 1; i < 12; i++)
                {
                    for (int j = 1; j < 31; j++)
                    {
                        try
                        {
                            File file = new File(getApplicationContext().getFilesDir() + File.separator + "Logs" + File.separator + year + File.separator + i + File.separator + j + ".txt");

                            Scanner scanner = new Scanner(file);
                            everyDayMoney = Double.parseDouble(scanner.nextLine());
                            summaryMonthMoney = summaryMonthMoney + everyDayMoney;
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                reportSalary.setText("You are get " + (float)summaryMonthMoney + " ILS on selected year");
            }
        });



    }
}
