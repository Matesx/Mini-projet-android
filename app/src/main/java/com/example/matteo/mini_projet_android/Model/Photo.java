package com.example.matteo.mini_projet_android.Model;

/**
 * Created by Matteo on 16/03/2017.
 */

public class Photo {

    public Double orientation;
    public String criticite;
    public Point coordonne;


    public Photo() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Photo(Double orientation, String criticite, Point coordonne ) {
        this.orientation = orientation;
        this.criticite = criticite;
        this.coordonne = coordonne;
    }
}
