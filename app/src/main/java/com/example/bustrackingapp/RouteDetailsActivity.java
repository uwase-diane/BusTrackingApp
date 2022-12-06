package com.example.bustrackingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bustrackingapp.entities.RouteStop;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RouteDetailsActivity extends AppCompatActivity {

    DatabaseReference database;
    RecyclerView recyclerview;
    MainAdapter mainAdapter;
    ArrayList<RouteStop> list;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);

        recyclerview =(RecyclerView)findViewById(R.id.routelist);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<RouteStop> options =
                new FirebaseRecyclerOptions.Builder<RouteStop>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Route"), RouteStop.class)
                        .build();
        mainAdapter = new MainAdapter(options);
        recyclerview.setAdapter(mainAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
}
