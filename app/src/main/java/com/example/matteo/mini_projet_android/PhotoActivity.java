package com.example.matteo.mini_projet_android;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matteo.mini_projet_android.Model.Photo;
import com.example.matteo.mini_projet_android.Model.Point;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.io.ByteArrayOutputStream;
import java.io.File;


/**
 * Created by Matteo on 16/03/2017.
 */

public class PhotoActivity extends AppCompatActivity implements SensorEventListener {
    Button ButtonEnvoyer;
    TextView TextViewValid;
    Spinner SpinnerCriticite;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference();
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private File output=null;

    ImageView imageView;


    SensorManager sensorManager;
    Sensor magnetic;
    Sensor accelerometer;

    // Attribut de la classe pour calculer  l'orientation
    float[] acceleromterVector=new float[3];
    float[] magneticVector=new float[3];
    float[] resultMatrix=new float[9];
    float[] values=new float[3];
    Double latitude = 0.0;
    Double longitude = 0.0;
    static float orientation;
    static String criticite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        imageView = (ImageView) findViewById(R.id.imageView2) ;

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

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));

                        startActivityForResult(cameraIntent, 1);
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
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), image);

            Point photoPoint = new Point("Photo1", latitude,  longitude);


            // store image in firebase
            ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
            image.recycle();
            byte[] byteArray = bYtE.toByteArray();
            String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

            Photo photo1 = new Photo(orientation, criticite, photoPoint, encodedImage);
            myRef.child("photos").child(photoPoint.name).setValue(photo1);



            TextViewValid.setText("Votre photo : ");


            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);

            String infos = "Infos enregistrées \n";
            infos += "criticite : "+criticite+"\n";
            infos += "position : "+longitude+" , "+latitude +"\n";
            infos += "orientation : "+orientation+"\n";

            Toast.makeText(this, infos, Toast.LENGTH_LONG).show();


        }
        else{
            Toast.makeText(this, "Aucune photo n'a été prise", Toast.LENGTH_SHORT).show();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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
