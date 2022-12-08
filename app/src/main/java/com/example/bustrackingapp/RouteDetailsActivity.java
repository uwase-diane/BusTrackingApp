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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RouteDetailsActivity extends AppCompatActivity {

    Button btnContinue;
    Spinner spinnerRouteOne;
    Spinner spinnerRouteTwo;
    Spinner spinnerRouteThree;
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayList<String> bustStop1;
    ArrayList<String> bustStop2;
    ArrayList<String> bustStop3;
    ArrayAdapter<String> arrayAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);

        btnContinue = findViewById(R.id.continueDetails);
        spinnerRouteOne = findViewById(R.id.route1);
        spinnerRouteTwo = findViewById(R.id.route2);
        spinnerRouteThree = findViewById(R.id.route3);

        //route 1

        databaseReference = FirebaseDatabase.getInstance().getReference("Route1");
        bustStop1 = new ArrayList<>();
        bustStop1.add("Select from route one ");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bustStop1);
        spinnerRouteOne.setAdapter(arrayAdapter);
        retrieveDataRouteOne();

        //route 2

        databaseReference = FirebaseDatabase.getInstance().getReference("Route2");
        bustStop2 = new ArrayList<>();
        bustStop2.add("Select from route two ");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bustStop2);
        spinnerRouteTwo.setAdapter(arrayAdapter);
        retrieveDataRouteTwo();

        // route 3


        databaseReference = FirebaseDatabase.getInstance().getReference("Route3");
        bustStop3 = new ArrayList<>();
        bustStop3.add("Select from route three ");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bustStop3);
        spinnerRouteThree.setAdapter(arrayAdapter);
        retrieveDataRouteThree();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TimeBusActivity.class));
            }
        });

    }

    public void retrieveDataRouteOne() {

        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    bustStop1.add(item.getValue().toString());
                }

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void retrieveDataRouteTwo() {

        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    bustStop2.add(item.getValue().toString());
                }

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void retrieveDataRouteThree() {

        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    bustStop3.add(item.getValue().toString());
                }

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}
