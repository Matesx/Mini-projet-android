package com.example.matteo.mini_projet_android;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

/**
 * Created by Matteo on 16/03/2017.
 */

public class Photo extends AppCompatActivity implements SensorEventListener {
    Button ButtonEnvoyer;
    TextView TextViewValid;
    Spinner SpinnerCriticite;


    SensorManager sensorManager;
    Sensor magnetic;
    Sensor accelerometer;

    // Attribut de la classe pour calculer  l'orientation
    float[] acceleromterVector=new float[3];
    float[] magneticVector=new float[3];
    float[] resultMatrix=new float[9];
    float[] values=new float[3];
    double latitude = 0;
    double longitude = 0;
    static float orientation;
    static String criticite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ButtonEnvoyer = (Button) findViewById(R.id.buttonEnvoyer);
        TextViewValid = (TextView) findViewById(R.id.txtInfoSend);
        SpinnerCriticite = (Spinner) findViewById(R.id.spinner);


        // Instancier le SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Instantiate the magnetic sensor and its max range
        magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        // Instantiate the accelerometer
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        String[] items = new String[]{"Confort", "Problème", "Danger"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        SpinnerCriticite.setAdapter(adapter);

        //Appel du "buttonEnvoyer"
        ButtonEnvoyer.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        Intent mediaChooser = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(mediaChooser, 1);
                        criticite = SpinnerCriticite.getSelectedItem().toString();
                    }
                }
        );
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Utilisateur a pris une photo
        if (resultCode== Activity.RESULT_OK) {
            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

            int permissionCoarseCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            int permissionFineCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

            if (permissionCoarseCheck == PackageManager.PERMISSION_GRANTED && permissionFineCheck == PackageManager.PERMISSION_GRANTED
                    && locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        //Nothing TO DO

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        //Nothing TO DO
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        //Nothing TO DO
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        //Nothing TO DO
                    }
                });
                Location location = locationManager.getLastKnownLocation(locationManager.PASSIVE_PROVIDER);

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }

            Bitmap image = (Bitmap) data.getExtras().get("data");

            String infos = "Infos enregistrées \n";
            infos += "criticite : "+criticite+"\n";
            infos += "position : "+longitude+" , "+latitude +"\n";
            infos += "orientation : "+orientation+"\n";
            infos += "image : "+image;
            Toast.makeText(this, infos, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Aucune photo n'a été prise", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        // Mettre à jour la valeur de l'accéléromètre et du champ magnétique
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            acceleromterVector=event.values;
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magneticVector=event.values;
        }

        // Demander au sensorManager la matric de Rotation (resultMatric)
        SensorManager.getRotationMatrix(resultMatrix, null, acceleromterVector, magneticVector);

        // Demander au SensorManager le vecteur d'orientation associé (values)
        SensorManager.getOrientation(resultMatrix, values);

        orientation =(float) Math.toDegrees(values[0]);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this,magnetic, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this,accelerometer);
        sensorManager.unregisterListener(this,magnetic);
    }
}
