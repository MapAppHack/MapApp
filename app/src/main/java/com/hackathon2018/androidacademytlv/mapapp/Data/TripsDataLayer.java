package com.hackathon2018.androidacademytlv.mapapp.Data;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackathon2018.androidacademytlv.mapapp.Models.Person;
import com.hackathon2018.androidacademytlv.mapapp.Models.Trip;
import com.hackathon2018.androidacademytlv.mapapp.Models.TripEvent;

import java.util.List;

/**
 * Created by petero on 3/15/2018.
 */

public class TripsDataLayer {

    public static final String EVENTS = "events";
    public static final String TRIPS = "trips";
    public static final String TRIP = "trip";
    public static final String AUTHOR = "author";
    private static final String PERSONS = "persons";
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
        ChildEventListener listener = mDatabase.child(TRIPS).orderByChild(AUTHOR).equalTo(user)
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

    public void getTripDetails(String trip, final IItemAddedCallback<Trip> tripAddedCallback) {
        mDatabase.child(TRIPS).child(trip).addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void createTrip(Trip trip)
    {
        DatabaseReference ref = mDatabase.child(TRIPS).push();
        trip.id=ref.getKey();
        ref.setValue(trip);
    }

    public void saveTrip(Trip trip)
    {
        DatabaseReference ref = mDatabase.child(TRIPS).child(trip.id);
        ref.setValue(trip);
    }

    public void deleteTrip(Trip trip)
    {
        DatabaseReference ref = mDatabase.child(TRIPS).child(trip.id);
        ref.setValue(null);
    }


    public void createEvent(TripEvent tripEvent)
    {
        DatabaseReference ref = mDatabase.child(EVENTS).push();
        tripEvent.id=ref.getKey();
        ref.setValue(tripEvent);
    }

    public void saveEvent(TripEvent tripEvent)
    {
        DatabaseReference ref = mDatabase.child(EVENTS).child(tripEvent.id);
        ref.setValue(tripEvent);
    }

    public void deleteEvent(TripEvent tripEvent)
    {
        DatabaseReference ref = mDatabase.child(EVENTS).child(tripEvent.id);
        ref.setValue(null);
    }
    public void createPerson(Person person)
    {
        DatabaseReference ref = mDatabase.child(PERSONS).push();
        person.id=ref.getKey();
        ref.setValue(person);
    }

    public void savePerson(Person person)
    {
        DatabaseReference ref = mDatabase.child(PERSONS).child(person.id);
        ref.setValue(person);
    }

    public void deletePerson(Person person)
    {
        DatabaseReference ref = mDatabase.child(PERSONS).child(person.id);
        ref.setValue(null);
    }

    public void getEventsByTrips(String trip, final IItemAddedCallback<TripEvent> tripAddedCallback)
    {
        ChildEventListener listener = mDatabase.child(EVENTS).orderByChild(TRIP).equalTo(trip)
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
