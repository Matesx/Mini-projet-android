package com.example.matteo.mini_projet_android;

/**
 * Created by Matteo on 12/03/2017.
 */

public class Point {

    public String name;
    public Double LatLng1;
    public Double LatLng2;

    public Point() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Point(String name, Double LatLng1, Double LatLng2) {
        this.name = name;
        this.LatLng1 = LatLng1;
        this.LatLng2 = LatLng2;
    }
/*  firebase ne prends pas en compte les non beans
    public String getName(){
        return this.name;
    }

    public Double getLatLng1(){
        return this.LatLng1;
    }

    public Double getLatLng2(){
        return this.LatLng2;
    }
    */
}
