package com.example.bustrackingapp.bus_mapping;
import android.Manifest;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bustrackingapp.R;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;


public class StudentBusActivity extends AppCompatActivity
{
    private Location location;
    private LocationManager locationManager;
    private TextView longitudeTextView;
    private TextView latudeTextView;

    private SupportMapFragment supportMapFragment;
    private MapBusFragment googleMapFragment;
    private boolean firstTimePermissionEnabled = true;
    private static final int SMS_PERMISSION_REQUEST_CODE = 12;
    // TAG for log
    private final String TAG = "MapBusActivity";

    // toast notification
    private Toast toastObj;


    // called when mainActivity is first created.
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if(checkForSmsPermission())
            firstTimePermissionEnabled = false;

        // initialize toast object
        toastObj = Toast.makeText(getApplicationContext(),
                "Default toast message", Toast.LENGTH_SHORT);
        toastObj.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        super.onCreate(savedInstanceState);
        // zero arg constructor
        googleMapFragment = MapBusFragment.newInstance();

        checkLocationServices(savedInstanceState);
        setContentView(R.layout.student_activity_map_main);


        // find the TextViews for longitude and latitude
        longitudeTextView = findViewById(R.id.longitude_value);
        latudeTextView = findViewById(R.id.latitude_value);

        // finding views for buttons
        Button btnMapMe = findViewById(R.id.map_me_button);


        // get handle for SupportMapFragment
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_location);

        // adding listener to button to pop up map
        btnMapMe.setOnClickListener(v ->
        {
            // location permission request
            if (!checkForLocationPermission())
            {
                Log.d(TAG, "Start LocationPermissionRequest as there is no permission to get location");
                Intent startIntent = new Intent(this, LocationPermissionRequest.class);
                startActivityForResult(startIntent, 0);
            }

            // google map fragment on screen.
            loadGoogleMapFragment();
        });

    }
    public void onClickApprovePermissionRequest(View view)
    {
        Log.d(TAG, "onClickApprovePermissionRequest()");

//        ActivityCompat.requestPermissions(
//                this,
//                new String[]{Manifest.permission.SEND_SMS},
//                SMS_PERMISSION_REQUEST_CODE);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onStart()
    {
        super.onStart();
        // location permission request
        if (!checkForLocationPermission())
        {
            Log.d(TAG, "Start LocationPermissionRequest as there is no permission to get location");
            Intent startIntent = new Intent(this, LocationPermissionRequest.class);
            startActivityForResult(startIntent, 0);
        }
        //get handle for LocationManager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //get handle for Locale
        Locale locale = getResources().getConfiguration().locale;

        //get handle for current location including latitude and longitude
        location = this.getCurrentLocation();
//        location.setLatitude(-1.9443);
//        location.setLongitude(30.09497);
        //getting location from database
        Query longitude_query = FirebaseDatabase.getInstance().getReference().child(getString(R.string.bus_location))
                .child(getString(R.string.route_1))
                .child(getString(R.string.location))
                .orderByKey().equalTo(getString(R.string.longitude));;
        Query latitude_query = FirebaseDatabase.getInstance().getReference().child(getString(R.string.bus_location))
                .child(getString(R.string.route_1))
                .child(getString(R.string.location))
                .orderByKey().equalTo(getString(R.string.latitude));
//        System.out.println("location latitude: "+latitude_query);
        longitude_query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
//                    location = (Location)dataSnapshot.getValue();
                    System.out.println("location longitude: "+dataSnapshot.getValue());

                    location.setLongitude(Double.parseDouble(dataSnapshot.getValue().toString()));
                    Log.d(TAG, "onDataChange: (location retrieval from db)"+location.toString());
                    /////////////////////you need to finish this data reading form firebase
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        latitude_query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
//                    location = (Location)dataSnapshot.getValue();
                    System.out.println("location latitude: "+dataSnapshot.getValue());

                    location.setLatitude(Double.parseDouble(dataSnapshot.getValue().toString()));
                    Log.d(TAG, "onDataChange: (location retrieval from db)"+location.toString());
                    /////////////////////you need to finish this data reading form firebase
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //track the location as device moves
        registerForLocationUpdates();

        // display my current coordinates
        viewMyLocation();

        Log.d(TAG, "Start and register SMS send and delivered receiver");

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d(TAG, "onRestart");

        // checking for permissions
        if (checkForSmsPermission())
        {
            if(firstTimePermissionEnabled)
            {

                firstTimePermissionEnabled = false;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (!checkForLocationPermission())
            {
                Log.d(TAG, "No permission for location onRestart, app ending");
                this.finishAffinity();
            } else
            {
                Log.d(TAG, "Permission for location onRestart, app continuing");
            }
        }

    }

    // check for permission to use location on the device.
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkForLocationPermission()
    {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Log.d(TAG, "No permission for location");
            return false;
        }
        Log.d(TAG, "Permission for location");
        return true;
    }

    // check if permission to send SMS is set
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkForSmsPermission()
    {
        Log.d(TAG, "checkForSmsPermission");

        // checking if permission has been granted
        if (checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            Log.d(TAG, "Permission to send SMS denied previously!");

            return false;
        }
        Log.d(TAG, "Permission to send SMS accepted previously!");

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(TAG, "onResume");
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public Location getCurrentLocation()
    {
        if (checkForLocationPermission())
            return locationManager.getLastKnownLocation("gps");
        else
            return null;
    }


    public void viewMyLocation()
    {
        if (location != null)
        {
            // update views
            longitudeTextView.setText(String.valueOf(location.getLongitude()));
            latudeTextView.setText(String.valueOf(location.getLatitude()));
        } else
        {
            // no coordinates set
            showToastMsg("No Coordinates to Display!");
            longitudeTextView.setText(R.string.no_coordinates);
            latudeTextView.setText(R.string.no_coordinates);
            showToastMsg("Kindly check your location settings!");
        }

    }

    private void loadGoogleMapFragment()
    {
        googleMapFragment = MapBusFragment.newInstance(location, supportMapFragment);

        if (location != null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.map_location, googleMapFragment).commit();
            showToastMsg("See your current location on Google Map!");
        } else
        {
            showToastMsg("Location not available!, please check location and internet status!");
        }

    }
    private class LocationUpdatesListener implements LocationListener
    {
        @Override
        public void onLocationChanged(@NonNull Location _location)
        {

            Log.d(TAG, "onLocationChanged");

            if (_location != null)
                location = _location;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                viewMyLocation();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            Log.d(TAG, "onStatusChanged");
            showToastMsg("Location disabled!, please enable location");
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onProviderEnabled(@NonNull String provider)
        {
            Log.d(TAG, "onProviderEnabled");
            if (checkForLocationPermission())
                locationManager.requestLocationUpdates("gps", 1000L, 1.0f, this);
        }

        @Override
        public void onProviderDisabled(@NonNull String provider)
        {
            Log.d(TAG, "onProviderDisabled");

            showToastMsg("Location disabled!, please enable location");
        }

    }

    public void showToastMsg(String toastMsg)
    {
        // update toast message and show
        toastObj.setText(toastMsg);
        toastObj.show();
    }

    // check if location service is active
    public void checkLocationServices(Bundle savedInstanceState)
    {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;

        try
        {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex)
        {
        }


        if (!gps_enabled)
        {
            // notify user
            new AlertDialog.Builder(this)
                    .setMessage(R.string.location_not_enabled)
                    .setPositiveButton(R.string.continue_text, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt)
                        {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton(R.string.no_thanks_text, null)
                    .show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void registerForLocationUpdates()
    {
        if (checkForLocationPermission())
            locationManager.requestLocationUpdates("gps", 500L, 1.0f, new LocationUpdatesListener());
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        try
        {
            // checking if googleMapFragment has been previously set
            if (googleMapFragment != null)
                outState.putParcelable("obj", (Parcelable) googleMapFragment);
        } catch (Exception e)
        {
            Log.d(TAG, "googleMapFragment not yet set");
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        googleMapFragment = savedInstanceState.getParcelable("obj");
    }

}