package com.example.bustrackingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bustrackingapp.entities.RouteStop;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TimeBusActivty extends AppCompatActivity {

    Spinner pickUpTimeSpinner;
    Spinner notifyTimeSpinner;
    MainAdapter mainAdapter;
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayList<String> time;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> notifyTime;
    ArrayAdapter<String> arrayAdapterNotifyTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_time);

        //preferred time

        pickUpTimeSpinner = findViewById(R.id.backup_time);
        databaseReference = FirebaseDatabase.getInstance().getReference("PreferredTime");
        time = new ArrayList<>();
        time.add("Select your preferred time ");
       arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, time);
        pickUpTimeSpinner.setAdapter(arrayAdapter);
        retrieveDataPreferredTime();


        // notify time

        notifyTimeSpinner = findViewById(R.id.notify_time);
        databaseReference = FirebaseDatabase.getInstance().getReference("NotifyTime");
        notifyTime = new ArrayList<>();
        notifyTime.add("When to notify you ");

        arrayAdapterNotifyTime = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, notifyTime);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notifyTimeSpinner.setAdapter(arrayAdapterNotifyTime);
        retrieveDataNotifyTime();




    }

    public void retrieveDataPreferredTime() {

            listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    time.add(item.getValue().toString());
                }

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void retrieveDataNotifyTime() {

        listener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item:dataSnapshot.getChildren()){
                    notifyTime.add(item.getValue().toString());
                }

                arrayAdapterNotifyTime.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
