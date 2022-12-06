package com.example.bustrackingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    RadioGroup radioGroup;
    RadioButton radioButton;
    RecyclerView recyclerview;
    MainAdapter mainAdapter;
    Button continueDetails;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);

        recyclerview =(RecyclerView)findViewById(R.id.routelist);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));



        radioGroup = findViewById(R.id.radioGroup);

        FirebaseRecyclerOptions<RouteStop> options =
                new FirebaseRecyclerOptions.Builder<RouteStop>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Route"), RouteStop.class)
                        .build();
        mainAdapter = new MainAdapter(options);
        recyclerview.setAdapter(mainAdapter);

        // Routes


        // Preferred time and notify time

        continueDetails = findViewById(R.id.continueDetails);

        continueDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TimeBusActivty.class));
            }
        });

        recyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
            }
        });
    }


    public void onRadioButtonClicked(View v)
    {
        if(radioGroup == null)
        {
            System.out.println("is null " + radioGroup);
        }
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        Toast.makeText(this, "Select: " + radioButton.getText(), Toast.LENGTH_SHORT).show();

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
