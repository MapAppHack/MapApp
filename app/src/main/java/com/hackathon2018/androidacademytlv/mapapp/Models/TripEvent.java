package com.hackathon2018.androidacademytlv.mapapp.Models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class TripEvent implements Serializable {
    public static final String EXTRA_KEY = "extraKey";
    public String id;
    public String title;
    public String trip;
    public double startLatitude;
    public double startLongitude;
    public double endLatitude;
    public double endLongitude;
    public long start;
    public long end;
    public String currency;
    public double cost;
    public String contact;
    public String info;
    public String infoLink;

    public TripEvent() {
    }

    public TripEvent(String title, long startTime, long endTime, double startLatitude, double startLongitude){

        this.title = title;
        start = startTime;
        end = endTime;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
    }

    public TripEvent(String id, String title, String trip, double startLatitude, double startLongitude, double endLatitude, double endLongitude,
                     long start, long end, String currency, double cost, String contact, String info, String infoLink){
        this.id = id;
        this.title = title;
        this.trip = trip;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.start = start;
        this.end = end;
        this.currency = currency;
        this.cost = cost;
        this.contact = contact;
        this.info = info;
        this.infoLink = infoLink;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("title", title);
        result.put("start", start);
        result.put("end", end);
        result.put("currency", currency);
        result.put("cost", cost);
        result.put("contact", contact);
        result.put("info", info);
        result.put("infoLink", infoLink);
        result.put("trip", trip);
        result.put("startLatitude", startLatitude);
        result.put("startLongitude", startLongitude);
        result.put("endLatitude", endLatitude);
        result.put("endLongitude", endLongitude);
        return result;
    }
}
