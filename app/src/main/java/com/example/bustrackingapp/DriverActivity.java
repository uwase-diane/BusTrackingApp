package com.example.bustrackingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bustrackingapp.entities.bus_mapping.DriverMapMeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DriverActivity extends AppCompatActivity {

    Spinner selectRoute;
    Spinner selectTime;
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayList<String> time;
    ArrayAdapter<String> arrayAdapterTime;
    ArrayList<String> route;
    ArrayAdapter<String> arrayAdapteRoute;
    Button btnSave;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        selectRoute = findViewById(R.id.routes);
        selectTime = findViewById(R.id.times);
        btnSave = findViewById(R.id.btnSave);

        // route
        databaseReference = FirebaseDatabase.getInstance().getReference("Routes");
        route = new ArrayList<>();
        route.add("Select your route ");
        arrayAdapteRoute = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, route);
        selectRoute.setAdapter(arrayAdapteRoute);
        retrieveDataRoutes();

        // time


        databaseReference = FirebaseDatabase.getInstance().getReference("PreferredTime");
        time = new ArrayList<>();
        time.add("Select your departure time ");
        arrayAdapterTime = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, time);
        selectTime.setAdapter(arrayAdapterTime);
        retrieveDataPreferredTime();

    }


    public void retrieveDataRoutes() {

        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    route.add(item.getValue().toString());
                }

                arrayAdapteRoute.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void retrieveDataPreferredTime() {

        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    time.add(item.getValue().toString());
                }

                arrayAdapterTime.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DriverMapMeActivity.class);
                intent.putExtra("user","bustudent");
                startActivity(intent);
//                startActivity(new Intent(getApplicationContext(), DriverMapMeActivity.class));
            }
        });
    }
}
