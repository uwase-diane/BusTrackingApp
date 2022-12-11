package com.example.bustrackingapp.bus_mapping;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.bustrackingapp.R;


public class LocationPermissionRequest extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback
{
    // attributes
    private final int LOCATION_PERMISSION_REQUEST_CODE = 13;
    private final String TAG = "LocationPermRequest";
    
    // create screen to request sms permission
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(TAG, getString(R.string.onCreate_LocationPermissionRequest));
        
        // If permissions granted, we start the main activity (shut this activity down).
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            Log.d(TAG, getString(R.string.permission_had_previously_been_granted_for_location_access));
            finish();
        }
        
        Log.d(TAG, "screen created for location permission");
        setContentView(R.layout.location_permission);

        // bind accept / deny buttons
        findViewById(R.id.location_deny_button).setOnClickListener(v ->
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                onClickDenyPermissionRequest(v);
            }
        });

        findViewById(R.id.location_accept_button).setOnClickListener(v ->
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                onClickApprovePermissionRequest(v);
            }
        });
    }
    
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClickApprovePermissionRequest(View view)
    {
        Log.d(TAG, "onClickApprovePermissionRequest()");
        
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE);
    }
    
    public void onClickDenyPermissionRequest(View view)
    {
        Log.d(TAG, "onClickDenyPermissionRequest()");
        
        // quit activity
        finish();
    }
    
    
    // requesting permission
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE)
        {
            Log.d(TAG, "Permission Activity Ended");
            finish();
        }
    }
    
}
