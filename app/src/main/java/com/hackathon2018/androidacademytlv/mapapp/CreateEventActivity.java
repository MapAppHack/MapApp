package com.hackathon2018.androidacademytlv.mapapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.hackathon2018.androidacademytlv.mapapp.Data.TripsDataLayer;
import com.hackathon2018.androidacademytlv.mapapp.Models.TripEvent;

import android.text.format.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

public class CreateEventActivity extends AppCompatActivity {
    private static final double MAP_OFFSET = 0.01;
    private static final int PLACE_PICKER_REQUEST_START = 1678;
    private static final int PLACE_PICKER_REQUEST_END = 1679;
    private EditText mPlaceTextStart;
    private EditText mPlaceTextEnd;
    private TripEvent mTripEvent;
    private View mDataPickerSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        mTripEvent = new TripEvent();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mTripEvent.trip = bundle.getString("tripId", "");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
        for(Currency currency: Currency.getAvailableCurrencies())
        {
            adapter.add(currency.getDisplayName());
        }
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.create_event_currency);

        autoCompleteTextView.setAdapter(adapter);

        final Calendar myCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        final TimePickerDialog.OnTimeSetListener timeSet = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.HOUR, hour);
                myCalendar.set(Calendar.MINUTE, minute);

                EditText edited = (EditText) mDataPickerSource;
                Date time = myCalendar.getTime();
                edited.setTag(time);
                edited.setText(dateFormat.format(time));
//                updateLabel();            }
        }};

        final DatePickerDialog.OnDateSetListener dateSet = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                selectTime(timeSet, myCalendar);
//                edittext.setText(dateFormat.format(myCalendar.getTime()));
//                updateLabel();
            }

        };

        EditText editText = findViewById(R.id.create_event_start);
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(true);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    selectDate(dateSet, myCalendar, view);
                }
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                selectDate(dateSet, myCalendar, v);
            }
        });

        editText = findViewById(R.id.create_event_end);
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(true);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    selectDate(dateSet, myCalendar, view);
                }
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                selectDate(dateSet, myCalendar, v);
            }
        });


        mPlaceTextStart = findViewById(R.id.create_event_start_point);
        mPlaceTextStart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    selectPlace(PLACE_PICKER_REQUEST_START, mPlaceTextStart);
                }
            }
        });

        mPlaceTextEnd = findViewById(R.id.create_event_end_point);
        mPlaceTextEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    selectPlace(PLACE_PICKER_REQUEST_END, mPlaceTextEnd);
                }
            }
        });

    }

    private void selectTime(TimePickerDialog.OnTimeSetListener date, Calendar myCalendar) {
        new TimePickerDialog(this, date, myCalendar
                .get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(this)).show();}

    private void selectDate(DatePickerDialog.OnDateSetListener date, Calendar myCalendar, View view) {
        mDataPickerSource = view;
        new DatePickerDialog(CreateEventActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void selectPlace(int intentCode, View view) {


        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try
        {
            LatLng place = (LatLng) view.getTag();
            if (place != null)
            {
                LatLngBounds ll =  new LatLngBounds(new LatLng(place.latitude - MAP_OFFSET, place.longitude- MAP_OFFSET),
                        new LatLng(place.latitude + MAP_OFFSET, place.longitude + MAP_OFFSET));
                builder.setLatLngBounds(ll);
            }
            startActivityForResult(builder.build(this), intentCode);
        }
        catch (GooglePlayServicesRepairableException ex) {
        }
        catch (GooglePlayServicesNotAvailableException ex)
        {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case PLACE_PICKER_REQUEST_START:
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                mTripEvent.startText = place.getName().toString();
                mPlaceTextStart.setText(mTripEvent.startText);
                LatLng latLng = place.getLatLng();
                mTripEvent.startLatitude = latLng.latitude;
                mTripEvent.startLongitude = latLng.longitude;
                mPlaceTextStart.setTag(latLng);
            }
            break;
            case PLACE_PICKER_REQUEST_END:
                if (resultCode == RESULT_OK) {
                    Place place = PlacePicker.getPlace(this, data);
                    mTripEvent.endText = place.getName().toString();
                    mPlaceTextEnd.setText(mTripEvent.endText);
                    LatLng latLng = place.getLatLng();
                    mTripEvent.endLatitude = latLng.latitude;
                    mTripEvent.endLongitude = latLng.longitude;
                    mPlaceTextEnd.setTag(latLng);
                }
                break;
        }
    }

    public void onSaveClick(View view) {
        processData();
    }

    private void processData() {
        EditText text = findViewById(R.id.create_event_title);
        mTripEvent.title = text.getText().toString();
        Object dataObject;

        text = findViewById(R.id.create_event_start);
        dataObject = text.getTag();
        if (dataObject != null)
        {
            Date date = (Date) dataObject;
            mTripEvent.start = date.getTime();
        }

        text = findViewById(R.id.create_event_end);
        dataObject = text.getTag();
        if (dataObject != null)
        {
            Date date = (Date) dataObject;
            mTripEvent.end = date.getTime();
        }

        text = findViewById(R.id.create_event_cost);
        mTripEvent.cost = Double.parseDouble(text.getText().toString());
        text = findViewById(R.id.create_event_currency);
        mTripEvent.currency = text.getText().toString();

        TripsDataLayer.getInstance().createEvent(mTripEvent);
    }
}
