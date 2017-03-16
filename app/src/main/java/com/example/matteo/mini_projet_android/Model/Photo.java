package com.example.matteo.mini_projet_android.Model;

/**
 * Created by Matteo on 16/03/2017.
 */

public class Photo {

    public Float orientation;
    public String criticite, imageUrl;
    public Point coordonne;



    public Photo() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Photo(Float orientation, String criticite, Point coordonne, String imageUrl ) {
        this.orientation = orientation;
        this.criticite = criticite;
        this.coordonne = coordonne;
        this.imageUrl = imageUrl;
    }
}
