package com.hackathon2018.androidacademytlv.mapapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hackathon2018.androidacademytlv.mapapp.Data.IItemAddedCallback;
import com.hackathon2018.androidacademytlv.mapapp.Data.TripsDataLayer;
import com.hackathon2018.androidacademytlv.mapapp.Models.Trip;

import java.util.ArrayList;

public class TripListActivity extends AppCompatActivity {
    private static final int NUM_LIST_ITEMS = 50;
    private RecyclerView tripListRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Trip> mDataset = new ArrayList<>();

    //    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        tripListRecyclerView = (RecyclerView) findViewById(R.id.trip_list_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        tripListRecyclerView.setLayoutManager(layoutManager);

        tripListRecyclerView.setHasFixedSize(true);

            mAdapter = new TripListAdapter();
        tripListRecyclerView.setAdapter(mAdapter);

        generateTripList();
    }

    public void generateTripList() {

        final TripListAdapter listAdapter = (TripListAdapter) tripListRecyclerView.getAdapter();

        TripsDataLayer.getInstance().getTripsByUser("user1", new IItemAddedCallback<Trip>() {
            @Override
            public void onItemAdded(Trip item) {
                mDataset.add(item);
                listAdapter.setData(mDataset, TripListActivity.this);
                listAdapter.notifyDataSetChanged();
            }
        });
    }

//    @Override
//    public void onListItemClick(final String tripId) {
//        TripsDataLayer.getInstance().getTripDetails(tripId, new IItemAddedCallback<Trip>() {
//            @Override
//            public void onItemAdded(Trip item) {
//                Intent intent = new Intent(TripListActivity.this,MapActivity.class);
//                intent.putExtra(Trip.EXTRA_KEY,item);
//                startActivity(intent);
//
//            }
//        });
//    }
}
