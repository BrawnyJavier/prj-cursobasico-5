package com.altice.calculadoradetiempos;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AppMainActivity extends AppCompatActivity {


    private TextClock clock;

    // Fecha de inicio en el rango
    private DatePickerDialog InitDatePickerDialog; // Popup who hosts the DatePicker
    private TimePickerDialog InitTimePickerDialog; // Popup who hosts the timePicker
    private TextInputEditText InitDateInput; // Text imput that holds the selected Date
    private TextInputEditText InitTimeInput; // Text imput that holds the selected time
    private int initHour; // Selected hour
    private int initMinute; // Selected Minute

    // Fecha de fin en el rango
    private DatePickerDialog FinDatePickerDialog; // Popup who hosts the DatePicker
    private TimePickerDialog FinTimePickerDialog; // Popup who hosts the timePicker
    private TextInputEditText FinDateInput; // Text imput that holds the selected Date
    private TextInputEditText EndTimeInput; // Text imput that holds the selected time
    private int EndHour;  // Selected hour
    private int EndMinute; // Selected Minute

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        clock = (TextClock) findViewById(R.id.Clock);

        //Avoids keyboard being open on activity start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // Current Time
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        // Initializes variables for the initial date of the range
        InitDateInput = (TextInputEditText) findViewById(R.id.DateInitTextInput);
        InitTimeInput = (TextInputEditText) findViewById(R.id.TimeInitTextInput);
        InitDatePickerDialog = new DatePickerDialog(this);
        InitDatePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                InitDateInput.setText(dayOfMonth + "/" + (month+1) + "/" + year);
            }
        });
        InitTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                InitTimeInput.setText(hourOfDay + ":" + minute);
                initHour = hourOfDay;
                initMinute = minute;
            }
        }, hour, minute, true);

        // initializing variables for the end date of the range
        EndTimeInput = (TextInputEditText) findViewById(R.id.EndTimeTextInput);
        FinDateInput = (TextInputEditText) findViewById(R.id.DateFinInputText);
        FinDatePickerDialog = new DatePickerDialog(this);
        FinDatePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                FinDateInput.setText(dayOfMonth + "/" + (month+1) + "/" + year);
            }
        });

        FinTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                EndTimeInput.setText(hourOfDay + ":" + minute);
                EndHour = hourOfDay;
                EndMinute = minute;
            }
        }, hour, minute, true);

        // Sets the texts inputs to now as default value
        FinDateInput.setText(R.string.ahora);
        InitTimeInput.setText(R.string.ahora);

    }

    public void compute(View view) {
        Date initDate = getDateFromDatePicker(InitDatePickerDialog.getDatePicker());
        initDate.setHours(initHour);
        initDate.setMinutes(initMinute);
        Date finDate = getDateFromDatePicker(FinDatePickerDialog.getDatePicker());
        finDate.setHours(EndHour);
        finDate.setMinutes(EndMinute);
        Resources R = getResources();
        // Creates an instance of the class that holds the app's logic
        // and gets the string computed by it.
        String TimeSince_ = new TimeSince
                                .TimeSinceBuilder()
                                .setInit(initDate)
                                .setFin(finDate)
                                .setResources(R)
                                .BuildTimeSince()
                                .getTimeSince();
        // Shows the computed string in the clock label.
        clock.setText(TimeSince_);
        Toast.makeText(this, TimeSince_, Toast.LENGTH_SHORT).show();

    }


    // function to avoid repeating the code for getting the date from the datepicker.
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }


    // On click events
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showDateDialog(View view) {

        if (view.getId() == R.id.DateInitTextInput) {

            InitDatePickerDialog.show();

        } else if (view.getId() == R.id.DateFinInputText) {

            FinDatePickerDialog.show();
        }

    }

    public void showTimeDialog(View view) {
        if (view.getId() == R.id.TimeInitTextInput) {

            InitTimePickerDialog.show();

        } else if (view.getId() == R.id.EndTimeTextInput) {

            FinTimePickerDialog.show();
        }
    }
}
