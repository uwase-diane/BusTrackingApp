package com.example.bustrackingapp.entities.bus_mapping;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import com.example.bustrackingapp.R;

public class Distance_Map extends AppCompatActivity {
    TextView textView;
    TextView textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_map);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);


        Location currentLocation = new Location("locationA");
        currentLocation.setLatitude(17.372102);
        currentLocation.setLongitude(78.484196);
        Location destination = new Location("locationB");

        destination.setLatitude(17.375775);
        destination.setLongitude(78.469218);
        double distance = currentLocation.distanceTo(destination);
        textView.setText("Distance between two Geographic Locations: " + distance + " KMS");

        double time = 60 * distance / 60000;
        textView2.setText("time taken  is"+time);
    }
}