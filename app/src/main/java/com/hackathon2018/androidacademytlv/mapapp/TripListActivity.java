package com.hackathon2018.androidacademytlv.mapapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hackathon2018.androidacademytlv.mapapp.Data.IItemAddedCallback;
import com.hackathon2018.androidacademytlv.mapapp.Data.TripsDataLayer;
import com.hackathon2018.androidacademytlv.mapapp.Models.Trip;

public class TripListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        final Intent intent = new Intent(this, MapActivity.class);
        TripsDataLayer.getInstance().getTripsByUser("user1", new IItemAddedCallback<Trip>() {
            @Override
            public void onItemAdded(Trip item) {
                intent.putExtra(Trip.EXTRA_KEY, item);
                startActivity(intent);
            }
        });
    }
}
