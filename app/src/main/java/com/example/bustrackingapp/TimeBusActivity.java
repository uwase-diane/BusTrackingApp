package com.example.bustrackingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bustrackingapp.bus_mapping.MapBusActivity;
import com.example.bustrackingapp.bus_mapping.StudentBusActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import RetrieveFacadePattern.RetrieveFacade;
import RetrieveFactoryPattern.Retrieve;
import RetrieveFactoryPattern.RetrieveFactory;
import busActivitites.BusStopActivity;

public class TimeBusActivity extends AppCompatActivity   {



    Spinner pickUpTimeSpinner;
    Spinner notifyTimeSpinner;
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayList<String> time;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> notifyTime;
    ArrayAdapter<String> arrayAdapterNotifyTime;
    Button mapBus;
    String chose_bus_stop;
    String time_selected;
    Query longitude_query;
    Query latitude_query;
    double chose_longitude, chose_latitude;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_time);

        // button

        mapBus = findViewById(R.id.mapBus);

        //fetching location from firebase basing on the bus_stop chosen

        Intent s_intent = getIntent();
        chose_bus_stop = s_intent.getStringExtra("selected stop");


//         ############################

        longitude_query = FirebaseDatabase.getInstance().getReference().child("Stops_location")
                .child("R1")
                .child(chose_bus_stop)
                .orderByKey().equalTo(getString(R.string.longitude));;
        latitude_query = FirebaseDatabase.getInstance().getReference().child("Stops_location")
                .child("R1")
                .child(chose_bus_stop)
                .orderByKey().equalTo(getString(R.string.latitude));

        longitude_query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {

//                    System.out.println("location longitude: "+dataSnapshot.getValue());

                    chose_longitude = Double.parseDouble(dataSnapshot.getValue().toString());
//                    googleMapFragment.mapBusOnMap(location);
//                    Log.d(TAG, "onDataChange: (location retrieval from db)"+location.toString());
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

                    chose_latitude = Double.parseDouble(dataSnapshot.getValue().toString());
//

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        ############################3
        if(s_intent.getStringExtra("selected route").equals("Route1"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference("Route1");

        }
        if(s_intent.getStringExtra("selected route").equals("Route2"))
        {
            databaseReference = FirebaseDatabase.getInstance().getReference("Route2");

        }
        /////////////////////////////////////////////
        //preferred time

        pickUpTimeSpinner = findViewById(R.id.backup_time);
        databaseReference = FirebaseDatabase.getInstance().getReference("PreferredTime");
        time = new ArrayList<>();
        time.add(getString(R.string.select_your_preferred_time));
       arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, time);
        pickUpTimeSpinner.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        RetrieveFacade retrieveFacade = new RetrieveFacade(arrayAdapter,time);

        retrieveFacade.retrieveDataPreferredTime();
//        retrieveDataPreferredTime();


        // notify time

        notifyTimeSpinner = findViewById(R.id.notify_time);
        databaseReference = FirebaseDatabase.getInstance().getReference("NotifyTime");
        notifyTime = new ArrayList<>();
        notifyTime.add(getString(R.string.when_to_notify_you));


        RetrieveFactory shapeFactory = new RetrieveFactory();
        Retrieve retrieve1 = shapeFactory.getData("timebus");

        notifyTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                time_selected = notifyTime.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
                mapBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StudentBusActivity.class);
                intent.putExtra("stop_longitude",chose_longitude);
                intent.putExtra("stop_latitude",chose_latitude);
                intent.putExtra("time_selected",time_selected);
                startActivity(intent);
//                startActivity(new Intent(getApplicationContext(), StudentBusActivity.class));
            }
        });


        arrayAdapterNotifyTime = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, notifyTime);
        notifyTimeSpinner.setAdapter(arrayAdapterNotifyTime);
        retrieve1.retrieveDataFeedback(arrayAdapterNotifyTime,notifyTime);
        arrayAdapterNotifyTime.notifyDataSetChanged();



    }
}
