package com.example.matteo.mini_projet_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView monTexte = null;
    private Button button1, button2, button3;
    final String url_emploi_temps = "https://edt.univ-tlse3.fr/FSI/FSImentionM/Info/g31090.html";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monTexte = (TextView)findViewById(R.id.textView1);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        button1.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        //création d'un nouveau Thread pour libérer l'UI Thread le plus tôt possible ;)
                        new Thread(){
                            public void run(){
                                //on lance l'activité qui gère la map
                                Intent intent1 = new Intent(MainActivity.this, GoogleMap.class);
                                startActivity(intent1);
                            }
                        }.start();

                    }
                }
        );

        button2.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        //création d'un nouveau Thread pour libérer l'UI Thread le plus tôt possible ;)
                        new Thread(){
                            public void run(){
                                //on lance l'activité qui gère la map
                                Intent intent2 = new Intent(MainActivity.this, PageInternet.class);
                                intent2.putExtra("url", url_emploi_temps);
                                startActivity(intent2);
                            }
                        }.start();

                    }
                }
        );


        button3.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        //création d'un nouveau Thread pour libérer l'UI Thread le plus tôt possible ;)
                        new Thread(){
                            public void run(){
                                //on lance l'activité qui gère la map
                                Intent intent3 = new Intent(MainActivity.this, QrCode.class);
                                startActivity(intent3);
                            }
                        }.start();

                    }
                }
        );


    }
}

