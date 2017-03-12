package com.example.matteo.mini_projet_android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

/**
 * Created by Matteo on 12/03/2017.
 */

public class GoogleMap extends FragmentActivity implements OnMapReadyCallback  {



    public GoogleMap googleMap = null;
    LatLng cameraBase;
    Double LatLng1, LatLng2;
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

        myRef.child("cameraBase").child("Latlng1").setValue(43.55695559999999);
        myRef.child("cameraBase").child("Latlng2").setValue(1.466449799999964);
        myRef.child("markers").child("RU 1").child("Latlng1").setValue(43.561985);
        myRef.child("markers").child("RU 1").child("Latlng2").setValue(1.463430);
        myRef.child("markers").child("U4").child("Latlng1").setValue(43.561985);
        myRef.child("markers").child("U4").child("Latlng2").setValue(1.469198);
        myRef.child("markers").child("IRIT").child("Latlng1").setValue(43.561985);
        myRef.child("markers").child("IRIT").child("Latlng2").setValue(1.467881);

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
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if(child.getKey().equals("cameraBase")){
                        for (DataSnapshot childd : child.getChildren()) {
                            //Log.d("ATTENTION",childd.getKey() );
                            if(childd.getKey().equals("Latlng1")){
                                LatLng1 = (Double)childd.getValue();


                            }
                            if(childd.getKey().equals("Latlng2")){
                                LatLng2 = (Double)childd.getValue();
                            }
                        }

                    }

                    if(child.getKey().equals("markers")){
                        for (DataSnapshot childd : child.getChildren()) {
                            String titre = childd.getKey();
                            Double LatLng11 = 0.0;
                            Double LatLng22 = 0.0;
                            for (DataSnapshot childdd : childd.getChildren()) {
                                if(childdd.getKey().equals("Latlng1")){
                                    LatLng11 = (Double)childdd.getValue();


                                }
                                if(childdd.getKey().equals("Latlng2")){
                                    LatLng22 = (Double)childdd.getValue();
                                }
                            }

                            googleMap.addMarker(new MarkerOptions().position(new LatLng(LatLng11, LatLng22)).title(titre));
                        }
                    }


                }

                cameraBase = new LatLng(43.55695559999999,  1.466449799999964);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraBase, 15));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ALIRE", "Failed to read value.", error.toException());
            }
        });




    }
}