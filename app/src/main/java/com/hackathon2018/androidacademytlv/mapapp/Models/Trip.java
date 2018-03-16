package com.hackathon2018.androidacademytlv.mapapp.Models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;



public class Trip {
    public  String id;
    public String title;
    public double budget;
    public String currency;
    public String author;
    public long start;

    public  Trip()
    {

    }

    public  Trip(String id)
    {
        this.id=id;
    }
    public Trip(String id, String title, double budget, String currency, String author,long start)
    {
        this.id = id;
        this.title=title;
        this.budget=budget;
        this.currency=currency;
        this.author = author;
        this.start = start;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("title", title);
        result.put("budget", budget);
        result.put("currency", currency);
        result.put("author", author);
        result.put("start", start);
        return result;
    }
}
