package com.hackathon2018.androidacademytlv.mapapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hackathon2018.androidacademytlv.mapapp.Models.Trip;

import java.util.ArrayList;

public class TripListActivity extends AppCompatActivity {
    private static final int NUM_LIST_ITEMS = 50;
    private RecyclerView tripListRecyclerView;
    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        tripListRecyclerView = (RecyclerView) findViewById(R.id.trip_list_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        tripListRecyclerView.setLayoutManager(layoutManager);

        tripListRecyclerView.setHasFixedSize(true);

            mAdapter = new TripListAdapter(generateTripList());
        tripListRecyclerView.setAdapter(mAdapter);



    }

    public static ArrayList<Trip> generateTripList() {
        ArrayList<Trip> trips = new ArrayList<>();
//        trips.add(new Trip("one", "dgw", 5.4, "usd", "yt", 43454));
//        trips.add(new Trip("two", "dgw", 5.4, "usd", "yt", 43454));
//        trips.add(new Trip("three", "dgw", 5.4, "usd", "yt", 43454));
//        TripneTrip("One", "dgw", 5.4, "usd", "yt", 43454);
//        trips[1] = new Trip("Two", "dgw", 5.4, "usd", "yt", 43454);
//        trips[2] = new Trip("Three", "dgw", 5.4, "usd", "yt", 43454);
//        trips[3] = new Trip("Four", "dgw", 5.4, "usd", "yt", 43454);

        return trips;
    }

}
