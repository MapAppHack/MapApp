package com.hackathon2018.androidacademytlv.mapapp;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.hackathon2018.androidacademytlv.mapapp.Models.TripEvent;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private List<TripEvent> mTripEvents;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        mTripEvents = new ArrayList<>();
        long timeStamp = System.currentTimeMillis();
        mTripEvents.add(new TripEvent("Ozen Bar", timeStamp,timeStamp+1, 32.074208,34.7758732));
        mTripEvents.add(new TripEvent("Isrotel Royal Garden", timeStamp+2,timeStamp+3, 29.550476,34.9631261));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        PolylineOptions polylineOptions = new PolylineOptions().width(5).color(Color.RED);
//        new LatLngBounds();
        // Add a marker in Sydney, Australia, and move the camera.
        for (TripEvent event:
             mTripEvents) {
            LatLng markerPoint = new LatLng(event.startLatitude, event.startLongitude);
            polylineOptions.add(markerPoint);
            mMap.addMarker(new MarkerOptions().position(markerPoint).title(event.title));
        }

        double startLatitudeZoom = mTripEvents.get(0).startLatitude;
        double endLongitudeZoom = mTripEvents.get(mTripEvents.size()-1).startLongitude;
        LatLng latLngZoom = new LatLng(startLatitudeZoom, endLongitudeZoom);
        mMap.addPolyline(polylineOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngZoom, 7));
    }
}
