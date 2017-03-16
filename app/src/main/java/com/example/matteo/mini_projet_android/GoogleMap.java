package com.example.matteo.mini_projet_android;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.matteo.mini_projet_android.Model.Point;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Matteo on 12/03/2017.
 */

public class GoogleMap extends FragmentActivity implements OnMapReadyCallback  {

    public GoogleMap googleMap = null;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.google_map);

        try {
            initialiseMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initialiseMap() {

        /* batiment paul sabatier */
        Point ru1 = new Point("RU 1", 43.561985,  1.463430);
        Point u4 = new Point("U4", 43.562578,  1.469191);
        Point irit = new Point("IRIT", 43.561647,  1.467901);

        myRef.child("markers").child(ru1.name).setValue(ru1);
        myRef.child("markers").child(u4.name).setValue(u4);
        myRef.child("markers").child(irit.name).setValue(irit);

        if(googleMap == null){
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

    }


    @Override
    public void onMapReady(final com.google.android.gms.maps.GoogleMap googleMap) {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot enfant : dataSnapshot.getChildren()) {

                    if(enfant.getKey().equals("markers")){

                        Point point = null;

                        for (DataSnapshot enfant2 : enfant.getChildren()) {

                            point = enfant2.getValue(Point.class);
                            googleMap.addMarker(new MarkerOptions().position(new LatLng(point.LatLng1, point.LatLng2)).title(point.name));

                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ALIRE", "Failed to read value.", error.toException());
            }
        });

    }
}