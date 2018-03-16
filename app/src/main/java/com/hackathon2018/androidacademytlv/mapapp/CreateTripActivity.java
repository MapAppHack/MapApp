package com.hackathon2018.androidacademytlv.mapapp;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.hackathon2018.androidacademytlv.mapapp.Data.TripsDataLayer;
import com.hackathon2018.androidacademytlv.mapapp.Models.Trip;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        //final Calendar myCalendar = Calendar.getInstance();

        //EditText edittext= (EditText) findViewById(R.id.create_trip_start);

        final Calendar myCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final EditText edittext = findViewById(R.id.create_trip_start);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                edittext.setText(dateFormat.format(myCalendar.getTime()));
//                updateLabel();
            }

        };
        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    SelectDate(date, myCalendar);
                }
            }
        });

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SelectDate(date, myCalendar);
            }
        });

    }

    private void SelectDate(DatePickerDialog.OnDateSetListener date, Calendar myCalendar) {
        new DatePickerDialog(CreateTripActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void processData() {
        Trip trip = new Trip();
        EditText text = findViewById(R.id.create_trip_title);
        trip.title = text.getText().toString();
        text = findViewById(R.id.create_trip_budget);
        trip.budget =  Double.parseDouble(text.getText().toString());

        text = findViewById(R.id.create_trip_currency);
        trip.currency =  text.getText().toString();

        text = findViewById(R.id.create_trip_start);
        String value = text.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        try {
            date = dateFormat.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        trip.start =  date.getTime();

        TripsDataLayer.getInstance().createTrip(trip);

    }

    private boolean validateData() {
        return  true;
    }
    private void saveData() {
    }

    public void onSaveClick(View view) {
        processData();
    }
}
