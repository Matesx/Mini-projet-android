package com.example.matteo.mini_projet_android;

/**
 * Created by Matteo on 12/03/2017.
 */

public class Point {

    public Double LatLng1;
    public Double LatLng2;

    public Point() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Point(Double useLatLng1rname, Double LatLng2) {
        this.LatLng1 = LatLng1;
        this.LatLng2 = LatLng2;
    }
}
