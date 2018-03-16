package com.hackathon2018.androidacademytlv.mapapp;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;
import com.hackathon2018.androidacademytlv.mapapp.Data.IItemAddedCallback;
import com.hackathon2018.androidacademytlv.mapapp.Data.TripsDataLayer;
import com.hackathon2018.androidacademytlv.mapapp.Models.Trip;
import com.hackathon2018.androidacademytlv.mapapp.Models.TripEvent;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private int mCounter = 1;
    private PolylineOptions mPolylineOptions = new PolylineOptions().width(5).color(Color.RED);
    LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getTripsEvents();
    }

    private void drawEventsOnMap(TripEvent event) {




        LatLng markerStartPoint = new LatLng(event.startLatitude, event.startLongitude);
        LatLng markerEndPoint = new LatLng(event.endLatitude, event.endLongitude);
        mPolylineOptions.add(markerStartPoint);

        IconGenerator icg = new IconGenerator(this);
        Bitmap icon = icg.makeIcon(String.valueOf(mCounter));

        MarkerOptions startMarker = new MarkerOptions()
                .position(markerStartPoint)
                .title(event.title)
                .icon(BitmapDescriptorFactory.fromBitmap(icon));

        mMap.addMarker(startMarker);

        boundsBuilder.include(startMarker.getPosition());

        if(markerEndPoint.latitude != 0 || markerEndPoint.longitude != 0){
            icon = icg.makeIcon(String.valueOf(mCounter));
            mPolylineOptions.add(markerEndPoint);
            MarkerOptions endMarker = new MarkerOptions()
                    .position(markerEndPoint)
                    .title(event.title)
                    .icon(BitmapDescriptorFactory.fromBitmap(icon));
            mMap.addMarker(endMarker);
            boundsBuilder.include(endMarker.getPosition());
        }


        mMap.addPolyline(mPolylineOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),50));

        mCounter++;
    }


    //TODO: add bounderies to map
        //TODO: sort events by time stamp

//        new LatLngBounds();


    private void getTripsEvents() {
        final List<TripEvent> mTripEvents = new ArrayList<>();

        final TripsDataLayer dataLayer = TripsDataLayer.getInstance();
        dataLayer.getTripsByUser("user1", new IItemAddedCallback<Trip>() {
            @Override
            public void onItemAdded(Trip item) {
                String tripId = item.id;
                dataLayer.getEventsByTrips(tripId, new IItemAddedCallback<TripEvent>() {
                    @Override
                    public void onItemAdded(TripEvent item) {
                        drawEventsOnMap(item);
                    }
                });
                return;
            }
        });
    }
}
