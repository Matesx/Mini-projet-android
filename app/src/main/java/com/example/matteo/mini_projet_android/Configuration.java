package com.example.matteo.mini_projet_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.matteo.mini_projet_android.menu.MenuActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Configuration extends AppCompatActivity {

    TextView login, mdp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        login = (TextView) findViewById(R.id.textViewLogin);
        mdp = (TextView) findViewById(R.id.textViewMdp);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            login.setText("Votre login : " + user.getEmail());
        }

    }
}
