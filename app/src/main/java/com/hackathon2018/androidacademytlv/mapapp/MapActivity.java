package com.hackathon2018.androidacademytlv.mapapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;
import com.hackathon2018.androidacademytlv.mapapp.Data.IItemAddedCallback;
import com.hackathon2018.androidacademytlv.mapapp.Data.TripsDataLayer;
import com.hackathon2018.androidacademytlv.mapapp.Models.Trip;
import com.hackathon2018.androidacademytlv.mapapp.Models.TripEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Trip mTrip;
    private Map<String, TripEvent> mTripEvents;
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
        setInfoWindowAdapter(mMap);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra(Trip.EXTRA_KEY)) {
            mTrip =  (Trip) intentThatStartedThisActivity.getSerializableExtra(Trip.EXTRA_KEY);
            getTripsEvents(mTrip);
        }
    }

    private void setInfoWindowAdapter(GoogleMap mMap) {

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                TripEvent tripEvent = mTripEvents.get(marker.getSnippet());
                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.event_custom_view, null);

                TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);

                TextView tvStart = (TextView) v.findViewById(R.id.tv_start);

                TextView tvEnd = (TextView) v.findViewById(R.id.tv_end);

                TextView tvInfo = (TextView) v.findViewById(R.id.tv_info);

                TextView tvContact = (TextView) v.findViewById(R.id.tv_contact);

                tvTitle.setText("Event Name  :" + tripEvent.title);

                tvStart.setText("Start Time  :"+ tripEvent.start);

                tvEnd.setText("End Time  :"+ tripEvent.end);

                tvInfo.setText("Info  :"+ tripEvent.info);

                tvContact.setText("Contact  :"+ tripEvent.contact);

                // Returning the view containing InfoWindow contents
                return v;
            }
        });
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
                .snippet(event.id)
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

    private void getTripsEvents(Trip trip) {

        mTripEvents = new HashMap<>();
        final TripsDataLayer dataLayer = TripsDataLayer.getInstance();
        String tripId = trip.id;
        dataLayer.getEventsByTrips(tripId, new IItemAddedCallback<TripEvent>() {
            @Override
            public void onItemAdded(TripEvent item) {
                mTripEvents.put(item.id, item);
                drawEventsOnMap(item);
            }
        });
    }
}
