package com.example.matteo.mini_projet_android;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.Formatter;

public class InformationActivity extends AppCompatActivity implements SensorEventListener {

    // Valeur courante de la lumière
    float l;

    // Le sensor manager
    SensorManager sensorManager;

    // Le capteur de lumière
    Sensor light;

    TextView brightness, microphone;

    MediaRecorder micro;

    private static double mEMA;
    private double EMA_FILTER;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        brightness = (TextView) findViewById(R.id.textView7);
        microphone = (TextView) findViewById(R.id.textView8);

        new Ecouter().execute();

        // Instancier le SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Instancier le capteur de lumière
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        // Mettre à jour uniquement dans le cas de notre capteur
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            // La valeur de la lumière
            l = event.values[0];
            brightness.setText("Luminosite : " + l + " lux");

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        // désenregistrer notre écoute du capteur
        sensorManager.unregisterListener(this, light);
        super.onPause();
    }

    @Override
    protected void onResume() {
        // enregistrer notre écoute du capteur
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }

    public void start() {
        if (micro == null) {
            micro = new MediaRecorder();
            micro.setAudioSource(MediaRecorder.AudioSource.MIC);
            micro.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            micro.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            micro.setOutputFile("/dev/null");

            try {
                micro.prepare();
            } catch (IllegalStateException e) {
                Log.e("error", "IllegalStateException");
            } catch (IOException e) {
                Log.e("error", "IOException");
                ;
            }
            micro.start();
        }
    }

    public void stop() {
        if (micro != null) {
            micro.stop();
            micro.release();
            micro = null;
        }
    }

    public double getAmplitude() {
        if (micro != null)
            return (micro.getMaxAmplitude());
        else
            return 0;
    }

    public class Ecouter extends AsyncTask<Void, Double, Void> {

        protected void onPreExecute() {
            start();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            while(true) {
                SystemClock.sleep(300);
                mEMA = 0.0;
                EMA_FILTER = 0.6;
                double amp =  getAmplitude();
                mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
                double soundb = 20 * Math.log10(mEMA / 50);
                publishProgress( soundb);
            }


        }

        @Override
        protected void onProgressUpdate(Double... values) {
            Double value = values[0];

            if (value < 0) {
                value = new Double(0);
            }

            String db = new Formatter().format("%03.1f",value).toString();
            microphone.setText("Niveau sonore : "  + db + "db");
        }



        @Override
        protected void onPostExecute(Void result) {
            stop();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            stop();
        }
    }
}
