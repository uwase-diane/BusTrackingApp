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

import RetrieveFactoryPattern.Retrieve;
import RetrieveFactoryPattern.RetrieveFactory;

public class StudentFeedbackActivity extends AppCompatActivity  {



    Spinner feedback;
    DatabaseReference databaseReference;
    ValueEventListener listener;
    ArrayList<String> student_feedback;
    ArrayAdapter<String> arrayAdapter;
    Button save;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        save = findViewById(R.id.btnFeedback);

        feedback = findViewById(R.id.feedback);
        databaseReference = FirebaseDatabase.getInstance().getReference("Feedback");
        student_feedback = new ArrayList<>();
        student_feedback.add("Choose your answer");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, student_feedback);
        feedback.setAdapter(arrayAdapter);

        RetrieveFactory shapeFactory = new RetrieveFactory();
        Retrieve retrieve1 = shapeFactory.getData("studentfeedback");
        retrieve1.retrieveDataFeedback(arrayAdapter,student_feedback);
        arrayAdapter.notifyDataSetChanged();




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Confirmation.class));
            }
        });

    }

//    @Override
//    public void retrieveDataFeedback() {
//
//        listener = databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot item:dataSnapshot.getChildren()){
//                    student_feedback.add(item.getValue().toString());
//                }
//
//                arrayAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }

}
