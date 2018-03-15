package com.hackathon2018.androidacademytlv.mapapp.Models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Person {

    public String id;
    public String name;
    public String email;
    public String phone;
    public String contactType;
    public String trip;

    public Person(){
    }

    public Person(String id, String name, String email, String phone, String contactType, String trip)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.contactType = contactType;
        this.trip = trip;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("email", email);
        result.put("phone", phone);
        result.put("contactType", contactType);
        result.put("trip", trip);
        return result;
    }
}
