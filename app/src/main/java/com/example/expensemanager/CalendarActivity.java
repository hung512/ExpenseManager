package com.example.expensemanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity{

    Button dateButton, timeButton;
    TextView dateTextView, timeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        dateButton = findViewById(R.id.dateButton);
        timeButton = findViewById(R.id.timeButton);
        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateButton();
            }
        });
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeButton();
            }
        });
    }

    private void handleTimeButton() {
        Calendar calendar= Calendar.getInstance();
        int HOUR= calendar.get(Calendar.HOUR);
        int MINUTE= calendar.get(Calendar.MINUTE);

        boolean is24HourFormat= DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog= new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String timeString= " " + hour + ":" + minute;
                timeTextView.setText(timeString);

//                Calendar calendar= Calendar.getInstance();
//                calendar.set(Calendar.HOUR, hour);
//                calendar.set(Calendar.MINUTE, minute);
//
//                CharSequence charSequence = DateFormat.format("hh:mm a", calendar);
//                timeTextView.setText(charSequence);
            }
        }, HOUR, MINUTE, is24HourFormat);
        timePickerDialog.show();
    }

    private void handleDateButton() {

        Calendar cal= Calendar.getInstance();
        int YEAR = cal.get(Calendar.YEAR);
        int MONTH= cal.get(Calendar.MONTH);
        int DATE= cal.get(Calendar.DATE);

        DatePickerDialog datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                String dateString = year + " " + month + " "+ date;
                dateTextView.setText(dateString);

                Calendar calendar= Calendar.getInstance();
                int YEAR = calendar.get(Calendar.YEAR);
                int MONTH= calendar.get(Calendar.MONTH);
                int DATE= calendar.get(Calendar.DATE);

                CharSequence dateCharSequence= DateFormat.format("EEE, dd MMM yyyy", calendar);
                dateTextView.setText(dateCharSequence);
            }
        }, YEAR, MONTH, DATE);
        datePickerDialog.show();
    }
}