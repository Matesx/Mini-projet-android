package com.example.matteo.mini_projet_android.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.matteo.mini_projet_android.Configuration;
import com.example.matteo.mini_projet_android.GoogleMap;
import com.example.matteo.mini_projet_android.InformationActivity;
import com.example.matteo.mini_projet_android.PageInternet;
import com.example.matteo.mini_projet_android.PhotoActivity;
import com.example.matteo.mini_projet_android.QrCode;
import com.example.matteo.mini_projet_android.R;
import com.example.matteo.mini_projet_android.RUActivity;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final String url_emploi_temps = "https://edt.univ-tlse3.fr/FSI/FSImentionM/Info/g31090.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_geo:
                Intent intent1 = new Intent(MenuActivity.this, GoogleMap.class);
                startActivity(intent1);
                break;

            case R.id.nav_resto:
                Intent intent7 = new Intent(MenuActivity.this, RUActivity.class);
                startActivity(intent7);
                break;
            case R.id.nav_anomalies:
                Intent intent4 = new Intent(MenuActivity.this, PhotoActivity.class);
                startActivity(intent4);
                break;
            case R.id.nav_edt:
                Intent intent2 = new Intent(MenuActivity.this, PageInternet.class);
                intent2.putExtra("url", url_emploi_temps);
                startActivity(intent2);
                break;
            case R.id.nav_infos_cours:
                Intent intent3 = new Intent(MenuActivity.this, QrCode.class);
                startActivity(intent3);
                break;
            case R.id.nav_config:
                Intent intent6 = new Intent(MenuActivity.this, Configuration.class);
                startActivity(intent6);
                break;
            case R.id.nav_infos_config:
                Intent intent5 = new Intent(MenuActivity.this, InformationActivity.class);
                startActivity(intent5);
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
