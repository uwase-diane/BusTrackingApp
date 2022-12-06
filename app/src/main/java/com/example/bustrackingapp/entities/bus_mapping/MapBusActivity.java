package com.example.bustrackingapp.entities.bus_mapping;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bustrackingapp.R;

import java.util.Locale;

public class MapBusActivity extends AppCompatActivity
{
    private final int LOCATION_PERMISSION_REQUEST_CODE = 13;
    private Location location;
    private LocationManager locationManager;

    private PendingIntent deliveredPendingIntent;
    private TextView txtLatLong;

    private Button btnMapMe;
    private Locale currentLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_main);

        txtLatLong = (TextView) findViewById(R.id.txt_lat_long);

        btnMapMe = (Button) findViewById(R.id.btn_map_me);

        currentLocale = getResources().getConfiguration().locale;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        location = this.getCurrentLocation();
        displayLocation();
        registerForLocationUpdates();

        btnMapMe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openDialog();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                location = getCurrentLocation();
                displayLocation();
                registerForLocationUpdates();
            } else {
                Toast.makeText(this, getString(R.string.no_permission_warning), Toast.LENGTH_LONG).show();

                finishAffinity();
                System.exit(0);
            }
        }
//        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length <= 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Not permitted to send an SMS", Toast.LENGTH_LONG).show();
//            }
//        }
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    public boolean checkForLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
                return false;
            }
        }
        return true;
    }

    private void registerForLocationUpdates() {
        if (checkForLocationPermission())
            locationManager.requestLocationUpdates("gps", 30000L, 10.0f, new LocationUpdatesListener());
    }

    public Location getCurrentLocation() {
        if (checkForLocationPermission())
            return locationManager.getLastKnownLocation("gps");
        else
            return null;
    }
    public void displayLocation() {
        if (location != null)
            txtLatLong.setText(String.format(
                    currentLocale, "(%.2f,%.2f)", location.getLatitude(), location.getLongitude()
            ));
        else
            txtLatLong.setText(getString(R.string.null_location_message));
    }

    private void openDialog() {
        if (location != null)
            MapBusFragment.newInstance(location).show(getSupportFragmentManager(), null);
        else
            Toast.makeText(this, "Location not available", Toast.LENGTH_LONG).show();
    }
    private class LocationUpdatesListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            location = loc;
            displayLocation();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }


}