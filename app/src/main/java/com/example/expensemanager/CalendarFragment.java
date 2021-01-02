package com.example.expensemanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class CalendarFragment extends Fragment {

    private Button dateButton, timeButton;
    private TextView dateTextView, timeTextView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dateButton = view.findViewById(R.id.dateButton);
        timeButton = view.findViewById(R.id.timeButton);
        dateTextView = view.findViewById(R.id.dateTextView);
        timeTextView = view.findViewById(R.id.timeTextView);

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

        boolean is24HourFormat= DateFormat.is24HourFormat(getContext());

        TimePickerDialog timePickerDialog= new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
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

        DatePickerDialog datePickerDialog= new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
