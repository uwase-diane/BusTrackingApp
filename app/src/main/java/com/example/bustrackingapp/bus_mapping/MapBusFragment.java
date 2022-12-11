package com.example.bustrackingapp.bus_mapping;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.bustrackingapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapBusFragment extends Fragment implements OnMapReadyCallback
{
    private Location location;
    SupportMapFragment supportMapFragment;

    public MapBusFragment(Location location, SupportMapFragment supportMapFragment)
    {
        super();
        this.location = location;
        this.supportMapFragment = supportMapFragment;
    }

    // zero argument constructor
    public MapBusFragment()
    {
        super();
    }

    public static MapBusFragment newInstance(Location location, SupportMapFragment supportMapFragment)
    {
        return new MapBusFragment(location, supportMapFragment);
    }


    public static MapBusFragment newInstance()
    {
        return new MapBusFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_google_map, container, false);

        //get handle of SupportMapFragment
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (supportMapFragment != null)
        {
            supportMapFragment.getMapAsync(this);
        }

        return rootView;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }

    @Override
    public void onMapReady(GoogleMap _googleMap)
    {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        }

        _googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        _googleMap.setMyLocationEnabled(true);
        _googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        _googleMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng latLng = new LatLng(-34, 151);
        if (location != null)
            latLng = new LatLng(location.getLatitude(), location.getLongitude());

        _googleMap.addMarker(new MarkerOptions().position(latLng));
        _googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                latLng, 17.0f));
    }
}