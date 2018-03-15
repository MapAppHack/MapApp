package com.hackathon2018.androidacademytlv.mapapp;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Cap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;
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
        mTripEvents.add(new TripEvent("Lifta", timeStamp+4,timeStamp+5, 31.7953769,35.1962508));
        mTripEvents.add(new TripEvent("Jericho", timeStamp+6,timeStamp+7, 31.8612161,35.461637));
        mTripEvents.add(new TripEvent("HaBanim Garden", timeStamp+8,timeStamp+9, 32.6104967,35.2901403));
        mTripEvents.add(new TripEvent("Spa Hotel Mizpe Hayamim", timeStamp+10,timeStamp+11, 32.9583682,35.5327069));
        mTripEvents.add(new TripEvent("University Nahariya", timeStamp+12,timeStamp+13, 33.0222844,35.1078906));
        mTripEvents.add(new TripEvent("Zar'it", timeStamp+14,timeStamp+15, 33.0920219,35.2663289));
        mTripEvents.add(new TripEvent("1","Nahal Betzet", "", 33.0856492,35.2511153, 33.0823414,35.2275365, timeStamp+16,timeStamp+17, "", 0, "", "National Park", ""));
        mTripEvents.add(new TripEvent("Bet She'arim National Park", timeStamp+18,timeStamp+19, 32.7037597,35.129801));
        mTripEvents.add(new TripEvent("Residence Beach", timeStamp+20,timeStamp+21, 32.3279683,34.8492472));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        PolylineOptions polylineOptions = new PolylineOptions().width(5).color(Color.RED);

        //TODO: add bounderies to map
        //TODO: sort events by time stamp
//        new LatLngBounds();

        for (int i=0; i < mTripEvents.size()-1; i++){
            TripEvent event = mTripEvents.get(i);
            LatLng markerStartPoint = new LatLng(event.startLatitude, event.startLongitude);
            LatLng markerEndPoint = new LatLng(event.endLatitude, event.endLongitude);
            polylineOptions.add(markerStartPoint);

            IconGenerator icg = new IconGenerator(this);
            Bitmap icon = icg.makeIcon(String.valueOf(i+1));
            mMap.addMarker(
                    new MarkerOptions()
                            .position(markerStartPoint)
                            .title(event.title)
                            .icon(BitmapDescriptorFactory.fromBitmap(icon)));

            if(markerEndPoint.latitude != 0 || markerEndPoint.longitude != 0){
                icon = icg.makeIcon(String.valueOf(i+1));
                polylineOptions.add(markerEndPoint);
                mMap.addMarker(
                        new MarkerOptions()
                                .position(markerEndPoint)
                                .title(event.title)
                                .icon(BitmapDescriptorFactory.fromBitmap(icon)));
            }
        }

        double startLatitudeZoom = mTripEvents.get(0).startLatitude;
        double endLongitudeZoom = mTripEvents.get(mTripEvents.size()-1).startLongitude;
        LatLng latLngZoom = new LatLng(startLatitudeZoom, endLongitudeZoom);
        mMap.addPolyline(polylineOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngZoom, 7));
    }
}
