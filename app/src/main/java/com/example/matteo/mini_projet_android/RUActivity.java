package com.example.matteo.mini_projet_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class RUActivity extends AppCompatActivity {
    LineChart lineChart;
    Button button11h30;
    Button button11h45;
    Button button12h00;
    Button button12h15;
    Button button12h30;
    Button button12h45;
    Button buttonReinitialiser;

    ArrayList<Entry> entries;
    ArrayList<String> labels;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ru);

        lineChart = (LineChart) findViewById(R.id.linegraph);
        button11h30 = (Button) findViewById(R.id.button11h30);
        button11h45 = (Button) findViewById(R.id.button11h45);
        button12h00 = (Button) findViewById(R.id.button12H00);
        button12h15 = (Button) findViewById(R.id.button12H15);
        button12h30 = (Button) findViewById(R.id.button12H30);
        button12h45 = (Button) findViewById(R.id.button12H45);
        buttonReinitialiser = (Button) findViewById(R.id.buttonReinitialiser);





        entries = new ArrayList<>();
        entries.add(new Entry(400f, 0));
        entries.add(new Entry(750f, 1));
        entries.add(new Entry(800f, 2));
        entries.add(new Entry(850f, 3));
        entries.add(new Entry(600f, 4));
        entries.add(new Entry(400f, 5));

        LineDataSet dataset = new LineDataSet(entries, "Affluence Restaurant Universitaire");
        labels = new ArrayList<String>();
        labels.add("11H30");
        labels.add("11H45");
        labels.add("12H00");
        labels.add("12H15");
        labels.add("12H30");
        labels.add("12H45");

        LineData data = new LineData(labels, dataset);
        lineChart.setData(data); // set the data and list of lables into chart


        button11h30.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        updateChart(0);
                    }
                }
        );

        button11h45.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        updateChart(1);
                    }
                }
        );

        button12h00.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        updateChart(2);
                    }
                }
        );

        button12h15.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        updateChart(3);
                    }
                }
        );

        button12h30.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        updateChart(4);
                    }
                }
        );

        button12h45.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        updateChart(5);
                    }
                }
        );

        buttonReinitialiser.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {
                        reinitialiserChart();
                    }
                }
        );
    }

    public void updateChart(int key){
        float nb = entries.get(key).getVal();
        nb+=10;
        entries.set(key, new Entry(nb, key));

        LineDataSet dataset = new LineDataSet(entries, "Affluence Restaurant Universitaire");
        LineData data = new LineData(labels, dataset);

        lineChart.setData(data);
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }

    public void reinitialiserChart(){
        entries.set(0, new Entry(0f, 0));
        entries.set(1, new Entry(0f, 1));
        entries.set(2, new Entry(0f, 2));
        entries.set(3, new Entry(0f, 3));
        entries.set(4, new Entry(0f, 4));
        entries.set(5, new Entry(0f, 5));


        LineDataSet dataset = new LineDataSet(entries, "Affluence Restaurant Universitaire");
        LineData data = new LineData(labels, dataset);

        lineChart.setData(data);
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }
}
