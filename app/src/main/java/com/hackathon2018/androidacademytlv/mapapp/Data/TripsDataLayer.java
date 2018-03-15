package com.hackathon2018.androidacademytlv.mapapp.Data;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackathon2018.androidacademytlv.mapapp.Models.Trip;
import com.hackathon2018.androidacademytlv.mapapp.Models.TripEvent;

import java.util.List;

/**
 * Created by petero on 3/15/2018.
 */

public class TripsDataLayer {

    private static TripsDataLayer mInstance;
    private DatabaseReference mDatabase;

    public static TripsDataLayer getInstance()
    {
        if (mInstance == null) {
            mInstance = new TripsDataLayer();
        }
        return mInstance;
    }

    public  TripsDataLayer()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    public void getTripsByUser(String user, final IItemAddedCallback<Trip> tripAddedCallback)
    {
        ChildEventListener listener = mDatabase.child("trips").orderByChild("author").equalTo(user)
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Trip trip = dataSnapshot.getValue(Trip.class);
                tripAddedCallback.onItemAdded(trip);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getTripDetails(String trip, final IItemAddedCallback<Trip> tripAddedCallback)
    {
        mDatabase.child("trips").child(trip).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Trip trip = dataSnapshot.getValue(Trip.class);
                tripAddedCallback.onItemAdded(trip);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
	
    public void getEventsByTrips(String trip, final IItemAddedCallback<TripEvent> tripAddedCallback)
    {
        ChildEventListener listener = mDatabase.child("events").orderByChild("trip").equalTo(trip)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        TripEvent trip = dataSnapshot.getValue(TripEvent.class);
                        tripAddedCallback.onItemAdded(trip);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


}
