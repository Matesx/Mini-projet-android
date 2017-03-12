package com.example.matteo.mini_projet_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView monTexte = null;
    private Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monTexte = (TextView)findViewById(R.id.textView1);
        button1 = (Button) findViewById(R.id.button1);

        button1.setOnClickListener(

                new View.OnClickListener() {
                    public void onClick(View v) {
                        //on lance l'activité qui gère la map

                        //Toast.makeText(MainActivity.this,  "Bonjour ! :)", Toast.LENGTH_LONG).show();
                        Intent intent1 = new Intent(MainActivity.this, GoogleMap.class);
                        startActivity(intent1);

                    }
                }
        );


    }
}
